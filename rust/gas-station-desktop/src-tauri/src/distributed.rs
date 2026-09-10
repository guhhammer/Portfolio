//! LAN presence over UDP broadcast: announce this client every couple of
//! seconds and collect whatever the other stations broadcast.

use socket2::{Domain, Protocol, Socket, Type};
use std::collections::VecDeque;
use std::net::{SocketAddr, UdpSocket};
use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

pub type MessageQueue = Arc<Mutex<VecDeque<String>>>;

const BROADCAST_PORT: u16 = 34254;

pub fn start(my_id: String, messages: MessageQueue) {
    thread::spawn(move || {
        let socket = match Socket::new(Domain::IPV4, Type::DGRAM, Some(Protocol::UDP)) {
            Ok(s) => s,
            Err(e) => {
                eprintln!("Failed to create socket: {e}");
                return;
            }
        };

        if let Err(e) = socket.set_reuse_address(true) {
            eprintln!("Failed to set reuse_address: {e}");
            return;
        }

        let addr = SocketAddr::from(([0, 0, 0, 0], BROADCAST_PORT));
        if let Err(e) = socket.bind(&addr.into()) {
            eprintln!("Failed to bind socket: {e}");
            return;
        }

        let socket: UdpSocket = socket.into();
        if let Err(e) = socket.set_broadcast(true) {
            eprintln!("Failed to set broadcast: {e}");
            return;
        }
        if let Err(e) = socket.set_nonblocking(true) {
            eprintln!("Failed to set nonblocking: {e}");
            return;
        }

        // Receiver thread: collect everything the LAN broadcasts.
        let receiver = match socket.try_clone() {
            Ok(s) => s,
            Err(e) => {
                eprintln!("Failed to clone socket: {e}");
                return;
            }
        };
        thread::spawn(move || {
            let mut buf = [0u8; 1024];
            loop {
                match receiver.recv_from(&mut buf) {
                    Ok((amt, src)) => {
                        let msg = std::str::from_utf8(&buf[..amt]).unwrap_or("[invalid utf8]");
                        let mut queue = messages.lock().unwrap();
                        queue.push_back(format!("Received from {src}: {msg}"));
                    }
                    Err(_) => thread::sleep(Duration::from_millis(100)),
                }
            }
        });

        // Sender loop: announce this client every two seconds.
        let broadcast_addr = SocketAddr::from(([255, 255, 255, 255], BROADCAST_PORT));
        let msg = format!("hello from {my_id}");

        loop {
            if let Err(e) = socket.send_to(msg.as_bytes(), broadcast_addr) {
                eprintln!("Failed to send message: {e}");
            }

            println!("Sent: {msg}");
            thread::sleep(Duration::from_secs(2));
        }
    });
}
