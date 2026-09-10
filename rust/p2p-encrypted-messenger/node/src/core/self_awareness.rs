//! LAN peer discovery and encrypted peer-to-peer transport.
//!
//! Every node announces itself over UDP broadcast; peers that go silent are
//! pruned. Messages between peers travel over TCP, encrypted end to end.

use crate::adapters::server_get_routes::fetch_pub_keys;
use crate::config::{
    APPLICATION_TCP_PORT, APPLICATION_UDP_PORT, SELF_AWARENESS_BROADCAST_INTERVAL,
    SELF_AWARENESS_CLEANUP_INTERVAL, SELF_AWARENESS_DISPLAY_INTERVAL,
    SELF_AWARENESS_DISPLAY_PEERLIST, SELF_AWARENESS_PEER_TIMEOUT,
    SELF_AWARENESS_TCP_BUFFER_SIZE, SELF_AWARENESS_UDP_BUFFER_SIZE, SERVER_URL,
};
use crate::core::error::ApplicationError;
use crate::core::interfaces::ApplicationMessageTrait;
use crate::core::interpreter;
use crate::core::message::ApplicationMessage;
use crate::core::payload::ApplicationPayload;
use crate::errors::lan::{
    BROADCAST_PRESENCE_ERROR, TCP_LISTENER_ACCEPT_ERROR, TCP_SEND_MESSAGE_ERROR,
    TCP_STREAM_READ_ERROR, UDP_SOCKET_CLONE_ERROR, UDP_SOCKET_CREATE_ERROR,
};
use crate::{INSTANCE_NAME, PRIV_KEY, PUB_KEYS};

use if_addrs::get_if_addrs;
use socket2::{Domain, Protocol, Socket, Type};
use std::{
    io::{self, Read, Write},
    net::{Ipv4Addr, SocketAddr, TcpListener, TcpStream, UdpSocket},
    sync::{Arc, Mutex},
    thread,
    time::{Duration, Instant},
};

// --- Peers -------------------------------------------------------------------

#[derive(Debug)]
pub struct Peer {
    pub name: String,
    pub udp_addr: SocketAddr,
    pub tcp_addr: String,
    pub last_seen: Instant,
}

pub type PeerList = Arc<Mutex<Vec<Peer>>>;

// --- Address discovery -------------------------------------------------------

/// Find this machine's LAN IPv4 address, or the subnet broadcast address for it.
/// Falls back to `255.255.255.255` when no suitable interface exists.
fn discover_ipv4(exact_lan_ip: bool) -> Ipv4Addr {
    let Ok(ifaces) = get_if_addrs() else {
        return Ipv4Addr::BROADCAST;
    };

    for iface in ifaces {
        let if_addrs::IfAddr::V4(ref ipv4) = iface.addr else {
            continue;
        };
        if iface.is_loopback() || ipv4.ip.is_loopback() {
            continue;
        }

        if exact_lan_ip {
            return ipv4.ip;
        }

        // The subnet's broadcast address: ip | !netmask.
        return Ipv4Addr::from(u32::from(ipv4.ip) | !u32::from(ipv4.netmask));
    }

    Ipv4Addr::BROADCAST
}

// --- Background routines -----------------------------------------------------

/// Announce this node's presence on the LAN at a fixed interval.
fn broadcast_presence(socket: UdpSocket, addr: SocketAddr, instance_name: String, tcp_ip: String) {
    let msg = format!("#BROADCAST|INSTANCE:{instance_name}|TCP_ADDR:{tcp_ip}");

    loop {
        if let Err(e) = socket.send_to(msg.as_bytes(), addr) {
            eprintln!("{}", BROADCAST_PRESENCE_ERROR.with_details(e.to_string()));
        }
        thread::sleep(SELF_AWARENESS_BROADCAST_INTERVAL);
    }
}

/// Drop peers that have stopped announcing themselves.
fn cleanup_peers(peer_list: PeerList) {
    loop {
        thread::sleep(SELF_AWARENESS_CLEANUP_INTERVAL);

        let mut peers = peer_list.lock().unwrap();
        peers.retain(|p| p.last_seen.elapsed() < SELF_AWARENESS_PEER_TIMEOUT);
    }
}

/// Periodically print the current peer list (when enabled).
fn display_peers(peer_list: PeerList) {
    loop {
        thread::sleep(SELF_AWARENESS_DISPLAY_INTERVAL);

        let names: Vec<String> = peer_list
            .lock()
            .unwrap()
            .iter()
            .map(|p| p.name.clone())
            .collect();

        println!("#AWAREOF|PEERLIST:{names:?}");
    }
}

// --- UDP discovery listener --------------------------------------------------

/// Create a UDP socket with SO_REUSEADDR and broadcast enabled.
fn create_udp_socket(addr: &SocketAddr) -> io::Result<UdpSocket> {
    let socket = Socket::new(Domain::IPV4, Type::DGRAM, Some(Protocol::UDP))?;
    socket.set_reuse_address(true)?;
    socket.set_broadcast(true)?;
    socket.bind(&(*addr).into())?;
    Ok(socket.into())
}

fn extract_instance_name(msg: &str) -> Option<String> {
    Some(
        msg.strip_prefix("#BROADCAST|INSTANCE:")?
            .split('|')
            .next()?
            .to_string(),
    )
}

fn extract_tcp_addr(msg: &str) -> Option<String> {
    msg.split('|')
        .find_map(|part| part.strip_prefix("TCP_ADDR:"))
        .map(str::to_string)
}

/// Consume discovery broadcasts and keep the peer list up to date.
fn run_discovery_listener(socket: UdpSocket, peer_list: PeerList) {
    let mut buf = [0u8; SELF_AWARENESS_UDP_BUFFER_SIZE];

    loop {
        let (size, src) = match socket.recv_from(&mut buf) {
            Ok(r) => r,
            Err(e) => {
                eprintln!("discovery: receive failed: {e}");
                continue;
            }
        };

        let Ok(msg) = std::str::from_utf8(&buf[..size]) else {
            continue; // not one of our announcements
        };

        if let Some(name) = extract_instance_name(msg) {
            let tcp_addr = match extract_tcp_addr(msg) {
                Some(ip) => format!("{ip}:{APPLICATION_TCP_PORT}"),
                None => format!("0.0.0.0:{APPLICATION_TCP_PORT}"),
            };

            update_peers(&peer_list, name, src, tcp_addr);
        }
    }
}

/// Record a peer announcement; on first contact with a new peer, re-sync
/// public keys from the key server.
fn update_peers(peer_list: &PeerList, name: String, udp_addr: SocketAddr, tcp_addr: String) {
    let is_new = {
        let mut peers = peer_list.lock().unwrap();

        if let Some(existing) = peers.iter_mut().find(|p| p.name == name) {
            existing.last_seen = Instant::now();
            existing.udp_addr = udp_addr; // the peer's IP may have changed
            false
        } else {
            peers.push(Peer {
                name,
                udp_addr,
                tcp_addr,
                last_seen: Instant::now(),
            });
            true
        }
    };

    if is_new {
        match fetch_pub_keys(SERVER_URL) {
            Ok(keys) => PUB_KEYS.write().unwrap().extend(keys),
            Err(e) => eprintln!("{e}"),
        }
    }
}

// --- TCP messaging -----------------------------------------------------------

/// Read one encrypted payload from a peer connection, decrypt it, and hand the
/// message to the interpreter.
fn consume_stream(mut stream: TcpStream) {
    let mut buf = [0u8; SELF_AWARENESS_TCP_BUFFER_SIZE];

    loop {
        match stream.read(&mut buf) {
            Ok(0) => break, // connection closed
            Ok(n) => {
                let payload: ApplicationPayload = match serde_json::from_slice(&buf[..n]) {
                    Ok(p) => p,
                    Err(e) => {
                        eprintln!("tcp: failed to parse payload: {e}");
                        continue;
                    }
                };

                println!("#TCP|RECEIVED PAYLOAD FROM: {}", payload.from());

                let priv_key = PRIV_KEY.read().unwrap().clone();
                let pub_keys = PUB_KEYS.read().unwrap().clone();

                match ApplicationMessage::open(&payload, &priv_key, &pub_keys) {
                    Ok(message) => interpreter::from_message(Box::new(message)),
                    Err(e) => eprintln!("tcp: could not open payload: {e}"),
                }

                return;
            }
            Err(ref e) if e.kind() == io::ErrorKind::Interrupted => continue,
            Err(ref e) if e.kind() == io::ErrorKind::WouldBlock => {
                thread::sleep(Duration::from_millis(50));
            }
            Err(e) => {
                eprintln!(
                    "{}",
                    TCP_STREAM_READ_ERROR.with_details(format!("kind={:?} msg={e}", e.kind()))
                );
                break;
            }
        }
    }
}

/// Accept incoming peer connections, one thread per stream.
fn run_tcp_listener(listener: TcpListener) {
    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                thread::spawn(move || consume_stream(stream));
            }
            Err(ref e) if e.kind() == io::ErrorKind::WouldBlock => {
                thread::sleep(Duration::from_millis(100));
            }
            Err(e) => {
                eprintln!(
                    "{}",
                    TCP_LISTENER_ACCEPT_ERROR.with_details(format!("kind={:?} msg={e}", e.kind()))
                );
            }
        }
    }
}

/// Send one encrypted payload to a peer.
fn tcp_send_message(addr: &str, msg: &[u8]) {
    let mut stream = match TcpStream::connect(addr) {
        Ok(s) => s,
        Err(e) => {
            eprintln!(
                "{}",
                TCP_SEND_MESSAGE_ERROR.with_details(format!("connect to {addr}: {e}"))
            );
            return;
        }
    };

    if let Err(e) = stream.write_all(msg).and_then(|()| stream.flush()) {
        eprintln!(
            "{}",
            TCP_SEND_MESSAGE_ERROR.with_details(format!("write to {addr}: {e}"))
        );
    }
}

// --- Public API --------------------------------------------------------------

/// Encrypt `msg` individually for every known peer and send it over TCP.
pub fn broadcast_message_all_peers(peer_list: &PeerList, msg: &ApplicationMessage) {
    let priv_key = PRIV_KEY.read().unwrap().clone();
    let pub_keys = PUB_KEYS.read().unwrap().clone();
    let instance = INSTANCE_NAME.read().unwrap().clone();

    for peer in peer_list.lock().unwrap().iter() {
        if peer.name == instance {
            continue;
        }

        let Some(pub_key) = pub_keys.get(&peer.name) else {
            eprintln!("broadcast: no public key for peer {}, skipping", peer.name);
            continue;
        };

        match msg.seal(&instance, &peer.name, &priv_key, pub_key) {
            Ok(bytes) => tcp_send_message(&peer.tcp_addr, &bytes),
            Err(e) => eprintln!("broadcast: could not seal message for {}: {e}", peer.name),
        }
    }
}

/// Start all discovery and messaging routines.
///
/// Returns this node's UDP broadcast address, its TCP endpoint, and the live
/// peer list.
pub fn init_lan_awareness(
    instance_name: String,
) -> Result<(SocketAddr, String, PeerList), ApplicationError> {
    let peer_list: PeerList = Arc::new(Mutex::new(Vec::new()));

    let broadcast_addr = SocketAddr::from((discover_ipv4(false), APPLICATION_UDP_PORT));

    let udp_socket = create_udp_socket(&broadcast_addr)
        .map_err(|e| UDP_SOCKET_CREATE_ERROR.with_details(e.to_string()))?;

    // Separate handles for the sending and receiving routines.
    let udp_recv = udp_socket
        .try_clone()
        .map_err(|e| UDP_SOCKET_CLONE_ERROR.with_details(format!("receiver: {e}")))?;
    let udp_send = udp_socket
        .try_clone()
        .map_err(|e| UDP_SOCKET_CLONE_ERROR.with_details(format!("sender: {e}")))?;

    {
        let peer_list = peer_list.clone();
        thread::spawn(move || cleanup_peers(peer_list));
    }
    {
        let peer_list = peer_list.clone();
        thread::spawn(move || run_discovery_listener(udp_recv, peer_list));
    }
    {
        let lan_ip = discover_ipv4(true).to_string();
        thread::spawn(move || broadcast_presence(udp_send, broadcast_addr, instance_name, lan_ip));
    }
    if SELF_AWARENESS_DISPLAY_PEERLIST {
        let peer_list = peer_list.clone();
        thread::spawn(move || display_peers(peer_list));
    }

    let tcp_endpoint = format!("0.0.0.0:{APPLICATION_TCP_PORT}");
    let listener = TcpListener::bind(&tcp_endpoint)
        .map_err(|e| TCP_LISTENER_ACCEPT_ERROR.with_details(format!("bind {tcp_endpoint}: {e}")))?;
    listener
        .set_nonblocking(true)
        .map_err(|e| TCP_LISTENER_ACCEPT_ERROR.with_details(e.to_string()))?;
    thread::spawn(move || run_tcp_listener(listener));

    Ok((
        broadcast_addr,
        format!("{}:{APPLICATION_TCP_PORT}", discover_ipv4(true)),
        peer_list,
    ))
}
