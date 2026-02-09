use if_addrs::get_if_addrs;
use socket2::{Domain, Protocol, Socket, Type};
use std::{
    net::{Ipv4Addr, SocketAddr, UdpSocket},
    str, thread,
    time::{Duration, Instant},
};

#[derive(Debug, Clone)]
pub struct Peer {
    pub name: String,
    pub addr: SocketAddr,
    pub last_seen: Instant,
}

use std::sync::{Arc, Mutex};

pub type PeerList = Arc<Mutex<Vec<Peer>>>;

pub fn display_peerlist(peer_list: &PeerList) -> String {
    {
        let a = peer_list
            .lock()
            .unwrap()
            .iter()
            .map(|p: &Peer| p.name.clone())
            .collect::<Vec<String>>();
        format!("{a:?}")
    }
}

fn update_peers(peer_list: &PeerList, name: String, addr: SocketAddr) {
    let mut peers = peer_list.lock().unwrap();

    if let Some(existing) = peers.iter_mut().find(|p| p.name == name) {
        existing.last_seen = Instant::now(); // refresh heartbeat.
        existing.addr = addr; // update IP in case it changed.
    } else {
        peers.push(Peer {
            name,
            addr,
            last_seen: Instant::now(),
        });
    }
}

fn cleanup_peers(peer_list: PeerList) {
    let timeout = Duration::from_secs(10); // adjust as needed.

    thread::spawn(move || {
        loop {
            thread::sleep(Duration::from_secs(5));

            let mut peers = peer_list.lock().unwrap();
            peers.retain(|p| p.last_seen.elapsed() < timeout);
        }
    });
}

/// Create a UDP socket bound to given address with SO_REUSEADDR/PORT.
fn create_udp_socket(addr: &SocketAddr) -> std::io::Result<UdpSocket> {
    let socket = Socket::new(Domain::IPV4, Type::DGRAM, Some(Protocol::UDP))?;

    socket.set_reuse_address(true)?;

    socket.set_broadcast(true)?;

    socket.bind(&addr.clone().into())?;

    Ok(socket.into())
}

fn extract_instance_name(msg: &str) -> Option<String> {
    // Remove the leading "[Instance: " and trailing "]"
    let trimmed = msg
        .strip_prefix("[Instance: ")?
        .split(']') // split at the first closing bracket
        .next()?;

    Some(trimmed.to_string())
}

/// Spawn a background listener thread that prints received messages.
fn start_listener(socket: UdpSocket, peer_list: &PeerList, display: bool) {
    let peer_list_clone = peer_list.clone();

    thread::spawn(move || {
        let mut buf = [0u8; 1024]; // buffer = max message size. (CHANGE ACCORDINGLY)

        loop {
            let (size, src) = socket.recv_from(&mut buf).unwrap();
            let msg = std::str::from_utf8(&buf[..size]).unwrap();

            if let Some(name) = extract_instance_name(msg) {
                update_peers(&peer_list_clone, name.to_string(), src);

                if display {
                    println!("{src}.peer_list: {:?}", display_peerlist(&peer_list_clone));
                }
            }
        }
    });
}

/// Broadcast a presence message every few seconds
fn broadcast_presence(socket: UdpSocket, addr: SocketAddr, instance_name: String) {
    loop {
        let msg = format!(
            "[Instance: {instance_name}] -PID {}- #broadcast#",
            std::process::id()
        );
        if let Err(e) = socket.send_to(msg.as_bytes(), addr) {
            eprintln!("send error: {e}");
        }
        thread::sleep(Duration::from_secs(2));
    }
}

/// Main discovery runner.
pub fn run_discovery(
    instance_name: String,
    peer_list: &PeerList,
    display: bool,
) -> std::io::Result<()> {
    cleanup_peers(peer_list.clone());

    let _broadcast = broadcast_addr_discovery();

    let addr: SocketAddr = format!("192.168.0.255:15000").parse().unwrap();

    let udp_socket = create_udp_socket(&addr)?;

    // Clone for sending vs listening.
    let udp_send = udp_socket.try_clone()?;
    let udp_recv = udp_socket.try_clone()?;

    // Start listener in background.
    start_listener(udp_recv, &peer_list, display);

    // Broadcast in main thread.
    broadcast_presence(udp_send, addr, instance_name);

    Ok(())
}

/*
Doesnt work equally for all machines.
*/
fn broadcast_addr_discovery() -> String {
    let ifaces = get_if_addrs().unwrap();

    for iface in ifaces {
        // Only IPv4

        if !iface.name.contains("eth0") || iface.name.contains("wlan0") {
            continue;
        }

        if let if_addrs::IfAddr::V4(ipv4_addr) = iface.addr {
            // Skip loopback and down interfaces
            if ipv4_addr.ip.is_loopback() {
                continue;
            }

            let ip = ipv4_addr.ip;
            let netmask = ipv4_addr.netmask;

            // Calculate broadcast: ip | !netmask
            let broadcast = Ipv4Addr::from(u32::from(ip) | !u32::from(netmask));

            return format!("{broadcast}");
        }
    }

    "127.0.0.1".to_string()
}
