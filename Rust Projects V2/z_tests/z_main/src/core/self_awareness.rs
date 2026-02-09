use crate::config::{
    APPLICATION_TCP_ADDR, APPLICATION_TCP_PORT, APPLICATION_UDP_PORT,
    SELF_AWARENESS_BROADCAST_ALIVE, SELF_AWARENESS_BUFFER_SIZE,
    SELF_AWARENESS_CLEANUP_REFRESH_RATE, SELF_AWARENESS_DISPLAY_PEERLIST_SHOW,
    SELF_AWARENESS_DISPLAY_PEERLIST_TIME, SELF_AWARENESS_TCP_BUFFER, SELF_AWARENESS_TIMEOUT,
    SERVER_URL,
};
use crate::core::interfaces::{ApplicationErrorTrait, ApplicationMessageTrait};
use crate::core::interpreter;
use crate::core::message::ApplicationMessage;
use crate::errors::lan::{
    BROADCAST_PRESENCE_ERROR, TCP_LISTENER_ACCEPT_ERROR, TCP_SEND_MESSAGE_ERROR,
    TCP_STREAM_READ_ERROR, UDP_SOCKET_CLONE_ERROR_RECV, UDP_SOCKET_CLONE_ERROR_SEND,
    UDP_SOCKET_CREATE_ERROR,
};
use crate::{INSTANCE_NAME, PRIV_KEY, PUB_KEYS};
use if_addrs::get_if_addrs;
use socket2::{Domain, Protocol, Socket, Type};
use std::{
    io::{self, Read, Write},
    net::{Ipv4Addr, SocketAddr, TcpListener, TcpStream, UdpSocket},
    str,
    sync::{Arc, Mutex},
    thread,
    time::{Duration, Instant},
};
/*
######  ####### ####### ######   #####
#     # #       #       #     # #     #
#     # #       #       #     # #
######  #####   #####   ######   #####
#       #       #       #   #         #
#       #       #       #    #  #     #
#       ####### ####### #     #  #####
*/
#[derive(Debug)]
pub struct Peer {
    pub name: String,
    pub udp_addr: SocketAddr,
    pub tcp_addr: String,
    pub last_seen: Instant,
}

pub type PeerList = Arc<Mutex<Vec<Peer>>>;

impl Peer {
    pub fn new(name: String, udp_addr: SocketAddr, tcp_addr: String, last_seen: Instant) -> Self {
        Self {
            name,
            udp_addr,
            tcp_addr,
            last_seen,
        }
    }
}
/*
###### #    # #    #  ####  ##### #  ####  #    #  ####
#      #    # ##   # #    #   #   # #    # ##   # #
#####  #    # # #  # #        #   # #    # # #  #  ####
#      #    # #  # # #        #   # #    # #  # #      #
#      #    # #   ## #    #   #   # #    # #   ## #    #
#       ####  #    #  ####    #   #  ####  #    #  ####
*/

/// Attempt to find a valid IPv4 broadcast address on any machine
fn broadcast_addr_discovery(exact_lan_ip: bool) -> Ipv4Addr {
    let ifaces = match get_if_addrs() {
        Ok(f) => f,
        Err(_) => return Ipv4Addr::new(255, 255, 255, 255), // fallback
    };

    for iface in ifaces {
        // Borrow ipv4_addr, don’t move it
        if let if_addrs::IfAddr::V4(ref ipv4_addr) = iface.addr {
            // Skip loopback IPs
            if ipv4_addr.ip.is_loopback() {
                continue;
            }

            // Skip loopback interfaces
            if iface.is_loopback() {
                continue;
            }

            if exact_lan_ip {
                return ipv4_addr.ip;
            }

            // Calculate broadcast
            let ip_u32 = u32::from(ipv4_addr.ip);
            let mask_u32 = u32::from(ipv4_addr.netmask);
            let broadcast = std::net::Ipv4Addr::from(ip_u32 | !mask_u32);

            return broadcast;
        }
    }

    // Fallback to universal broadcast
    Ipv4Addr::new(255, 255, 255, 255)
}

/// Broadcast a presence message every few seconds.
fn broadcast_presence(socket: UdpSocket, addr: SocketAddr, instance_name: String, tcp: String) {
    loop {
        let msg = format!("#BROADCAST|INSTANCE:{instance_name}|TCP_ADDR:{tcp}");

        if let Err(_e) = socket.send_to(msg.as_bytes(), addr) {
            eprintln!(
                "Broadcast error: {}",
                BROADCAST_PRESENCE_ERROR.clone().throw()
            );
        }

        thread::sleep(Duration::from_secs(SELF_AWARENESS_BROADCAST_ALIVE));
    }
}

/// Thread routine to keep peers updated.
fn cleanup_peers(peer_list: PeerList) {
    let timeout = Duration::from_secs(SELF_AWARENESS_TIMEOUT);

    loop {
        thread::sleep(Duration::from_secs(SELF_AWARENESS_CLEANUP_REFRESH_RATE));

        let mut peers = peer_list.lock().unwrap();

        peers.retain(|p| p.last_seen.elapsed() < timeout);
    }
}

/// Creates a TCP listener.
fn create_tcp_listener(addr: &str) -> io::Result<TcpListener> {
    let listener = TcpListener::bind(addr)?;

    listener.set_nonblocking(true)?;

    Ok(listener)
}

/// Creates a UDP socket bound to given address with SO_REUSEADDR/PORT.
fn create_udp_socket(addr: &SocketAddr) -> std::io::Result<UdpSocket> {
    let socket = Socket::new(Domain::IPV4, Type::DGRAM, Some(Protocol::UDP))?;

    socket.set_reuse_address(true)?;

    socket.set_broadcast(true)?;

    #[allow(clippy::clone_on_copy)]
    socket.bind(&addr.clone().into())?;

    Ok(socket.into())
}

use crate::core::payload::ApplicationPayload;

/// Consumes message stream over tcp.
fn consume_stream(mut stream: TcpStream) {
    let mut buf = SELF_AWARENESS_TCP_BUFFER;

    loop {
        match stream.read(&mut buf) {
            Ok(0) => break, // closed
            Ok(n) => {
                let data = &buf[..n];

                // Try to parse as JSON
                match serde_json::from_slice::<ApplicationPayload>(data) {
                    Ok(payload) => {
                        println!("#TCP|RECEIVED PAYLOAD FROM: {}", payload.get_from());

                        let priv_key = PRIV_KEY.read().unwrap();
                        let pub_keys = PUB_KEYS.read().unwrap();

                        let application_payload = ApplicationPayload::new(
                            payload.get_from(),
                            "".to_string(),
                            payload.get_nonce(),
                            payload.get_ct(),
                        );

                        let application_message_encrypted = ApplicationMessage::empty().decrypt(
                            application_payload,
                            &priv_key,
                            pub_keys.clone(),
                        );

                        let application_message =
                            ApplicationMessage::bytes_to_message(application_message_encrypted);

                        interpreter::from_message(Box::new(application_message));

                        return;
                    }
                    Err(e) => {
                        eprintln!("Failed to parse payload: {e}");
                    }
                }
            }
            Err(ref e) if e.kind() == io::ErrorKind::Interrupted => continue,
            Err(ref e) if e.kind() == io::ErrorKind::WouldBlock => {
                thread::sleep(Duration::from_millis(50));
                continue;
            }
            Err(e) => {
                println!(
                    "{}",
                    TCP_STREAM_READ_ERROR.clone().with_details(&format!(
                        "kind={:?} msg={}",
                        e.kind(),
                        e
                    ))
                );
                break;
            }
        }
    }
}

/// Logs awareness.
fn display_aware(peer_list: &PeerList) {
    let peer_list_clone = peer_list.clone();

    if !SELF_AWARENESS_DISPLAY_PEERLIST_SHOW {
        return;
    }

    loop {
        thread::sleep(SELF_AWARENESS_DISPLAY_PEERLIST_TIME);
        println!("#AWAREOF|PEERLIST:{}", display_peerlist(&peer_list_clone));
    }
}

/// Shows peerlist.
fn display_peerlist(peer_list: &PeerList) -> String {
    {
        let pl = peer_list
            .lock()
            .unwrap()
            .iter()
            .map(|p: &Peer| p.name.clone())
            .collect::<Vec<String>>();

        format!("{pl:?}")
    }
}

/// Gets instance name.
fn extract_instance_name(msg: &str) -> Option<String> {
    Some(
        msg.strip_prefix("#BROADCAST|INSTANCE:")?
            .split("|")
            .next()?
            .to_string(),
    )
}

fn extract_tcp_addr(msg: &str) -> Option<String> {
    msg.split('|')
        .find(|part| part.starts_with("TCP_ADDR:"))
        .map(|part| part.trim_start_matches("TCP_ADDR:").to_string())
}

/// Inits TCP listener.
fn run_tcp_messager() -> Result<(), Box<dyn ApplicationErrorTrait>> {
    let listener = create_tcp_listener(APPLICATION_TCP_ADDR).unwrap();

    thread::spawn(move || start_tcp_listener(listener));

    Ok(())
}

/// Spawn a background listener for peerlist.
fn start_listener(socket: UdpSocket, peer_list: &PeerList) {
    let peer_list_clone = peer_list.clone();

    let mut buf = SELF_AWARENESS_BUFFER_SIZE; // buffer = max message size. (CHANGE ACCORDINGLY)

    loop {
        let (size, src) = socket.recv_from(&mut buf).unwrap();
        let msg = std::str::from_utf8(&buf[..size]).unwrap();

        if let Some(name) = extract_instance_name(msg) {
            let tcpaddr = match extract_tcp_addr(msg) {
                Some(t) => format!("{t}:{APPLICATION_TCP_PORT}"),
                _ => format!("0.0.0.0:{APPLICATION_TCP_PORT}"),
            };

            update_peers(&peer_list_clone, name.to_string(), src, tcpaddr);
        }
    }
}

/// Spawn tcp listener in background.
fn start_tcp_listener(listener: TcpListener) {
    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                thread::spawn(move || consume_stream(stream));
            }
            Err(ref e) if e.kind() == io::ErrorKind::WouldBlock => {
                thread::sleep(Duration::from_millis(100));
            }

            Err(e) => {
                println!(
                    "{}",
                    TCP_LISTENER_ACCEPT_ERROR.clone().with_details(&format!(
                        "kind={:?} msg={}",
                        e.kind(),
                        e
                    ))
                );
            }
        }
    }
}

/// Sends message over tcp.
fn tcp_send_message(addr: &str, msg: &[u8]) {
    match TcpStream::connect(addr) {
        Ok(mut stream) => {
            if let Err(e) = stream.write_all(msg) {
                // Print descriptive error to console
                println!(
                    "{}",
                    TCP_SEND_MESSAGE_ERROR
                        .clone()
                        .with_details(&format!("Failed to write to {addr}: {e:?}"))
                );
            }
            stream.flush().unwrap();
        }
        Err(e) => {
            // Print connection error
            println!(
                "{}",
                TCP_SEND_MESSAGE_ERROR
                    .clone()
                    .with_details(&format!("Failed to connect to {addr}: {e:?}"))
            )
        }
    }
}

/// Refresh peerlist procedure;
fn update_peers(peer_list: &PeerList, name: String, udp_addr: SocketAddr, tcp_addr: String) {
    let mut peers = peer_list.lock().unwrap();

    if let Some(existing) = peers.iter_mut().find(|p| p.name == name) {
        existing.last_seen = Instant::now(); // refresh heartbeat.
        existing.udp_addr = udp_addr; // update IP in case it changed.
    } else {
        peers.push(Peer::new(name, udp_addr, tcp_addr, Instant::now()));
    }

    {
        let mut pk = PUB_KEYS.write().unwrap();

        let i = INSTANCE_NAME.read().unwrap();

        // Clear existing keys
        pk.clear();

        // Insert new keys from gpk

        pk.extend(gpk(&i, SERVER_URL));
    }
}
use reqwest::blocking::Client;
use std::collections::HashMap;

fn gpk(_instance_name: &str, server_url: &str) -> HashMap<String, String> {
    let client = Client::new();

    let resp = client
        .get(format!("{server_url}/get_pub_keys"))
        .send()
        .unwrap();

    if !resp.status().is_success() {
        panic!("Request failed: {:?}", resp.text().unwrap());
    }

    // Parse JSON into a HashMap<String, String>
    let hm: HashMap<String, String> =
        serde_json::from_str(&resp.text().unwrap()).expect("Failed to parse JSON into HashMap");

    hm
}

/*
######  ######  #######    #    ######   #####     #     #####  #######
#     # #     # #     #   # #   #     # #     #   # #   #     #    #
#     # #     # #     #  #   #  #     # #        #   #  #          #
######  ######  #     # #     # #     # #       #     #  #####     #
#     # #   #   #     # ####### #     # #       #######       #    #
#     # #    #  #     # #     # #     # #     # #     # #     #    #
######  #     # ####### #     # ######   #####  #     #  #####     #

#     # #######  #####   #####     #     #####  #######  #####
##   ## #       #     # #     #   # #   #     # #       #     #
# # # # #       #       #        #   #  #       #       #
#  #  # #####    #####   #####  #     # #  #### #####    #####
#     # #             #       # ####### #     # #             #
#     # #       #     # #     # #     # #     # #       #     #
#     # #######  #####   #####  #     #  #####  #######  #####
*/
/// Broadcast message procedure.
pub fn broadcast_message_all_peers(peer_list: &PeerList, msg: &ApplicationMessage) {
    let priv_key = PRIV_KEY.read().unwrap();
    let pub_keys = PUB_KEYS.read().unwrap();
    let instance = INSTANCE_NAME.read().unwrap();

    {
        peer_list.lock().unwrap().iter().for_each(|p: &Peer| {
            if *p.name != *instance {
                let pub_key = &pub_keys[&p.name].clone();
                let app_msg_bytes =
                    msg.send(&instance, &p.name, priv_key.as_str(), pub_key.as_str());

                tcp_send_message(&p.tcp_addr, &app_msg_bytes);
            }
        });
    }
}

/*
  ##   #    #   ##   #####  ###### #    # ######  ####   ####
 #  #  #    #  #  #  #    # #      ##   # #      #      #
#    # #    # #    # #    # #####  # #  # #####   ####   ####
###### # ## # ###### #####  #      #  # # #           #      #
#    # ##  ## #    # #   #  #      #   ## #      #    # #    #
#    # #    # #    # #    # ###### #    # ######  ####   ####
*/

/// awareness run procedure.
pub fn init_lan_awareness(
    instance_name: String,
) -> Result<(SocketAddr, String, PeerList), Box<dyn ApplicationErrorTrait>> {
    let peer_list: PeerList = Arc::new(Mutex::new(Vec::new()));

    let addr: SocketAddr = format!(
        "{}:{}",
        broadcast_addr_discovery(false),
        APPLICATION_UDP_PORT
    )
    .parse()
    .unwrap();

    let udp_socket = create_udp_socket(&addr).map_err(|_| {
        Box::new(UDP_SOCKET_CREATE_ERROR.clone().throw()) as Box<dyn ApplicationErrorTrait>
    })?;

    // Clone for sending vs listening.
    let udp_recv = udp_socket.try_clone().map_err(|_| {
        Box::new(UDP_SOCKET_CLONE_ERROR_RECV.clone().throw()) as Box<dyn ApplicationErrorTrait>
    })?;
    let udp_send = udp_socket.try_clone().map_err(|_| {
        Box::new(UDP_SOCKET_CLONE_ERROR_SEND.clone().throw()) as Box<dyn ApplicationErrorTrait>
    })?;

    // Background tasks
    {
        let peer_list = peer_list.clone();
        thread::spawn(move || cleanup_peers(peer_list));
    }
    {
        let peer_list = peer_list.clone();
        thread::spawn(move || start_listener(udp_recv, &peer_list));
    }
    {
        thread::spawn(move || {
            broadcast_presence(
                udp_send,
                addr,
                instance_name,
                broadcast_addr_discovery(true).to_string(),
            )
        });
    }
    {
        let peer_list = peer_list.clone();
        thread::spawn(move || display_aware(&peer_list));
    }

    run_tcp_messager()?;

    Ok((
        addr,
        format!("{}:{APPLICATION_TCP_PORT}", broadcast_addr_discovery(true)),
        peer_list,
    ))
}
