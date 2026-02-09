use std::collections::VecDeque;
use socket2::{Socket, Domain, Type, Protocol};
use std::net::{UdpSocket, SocketAddr};
use std::str;
use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

pub type MessageQueue = Arc<Mutex<VecDeque<String>>>;

pub fn start(my_id: String, messages: MessageQueue) {
   thread::spawn(move || {
        let port = 34254;

        // Create socket with error handling
        let socket = match Socket::new(Domain::IPV4, Type::DGRAM, Some(Protocol::UDP)) {
            Ok(s) => s,
            Err(e) => {
                eprintln!("Failed to create socket: {}", e);
                return; // exit the thread
            }
        };

        if let Err(e) = socket.set_reuse_address(true) {
            eprintln!("Failed to set reuse_address: {}", e);
            return;
        }

        let addr: SocketAddr = format!("0.0.0.0:{}", port).parse().unwrap();
        if let Err(e) = socket.bind(&addr.into()) {
            eprintln!("Failed to bind socket: {}", e);
            return;
        }

        let socket: UdpSocket = socket.into();
        if let Err(e) = socket.set_broadcast(true) {
            eprintln!("Failed to set broadcast: {}", e);
            return;
        }
        if let Err(e) = socket.set_nonblocking(true) {
            eprintln!("Failed to set nonblocking: {}", e);
            return;
        }

        // Receiver thread
        let socket_clone = match socket.try_clone() {
            Ok(s) => s,
            Err(e) => {
                eprintln!("Failed to clone socket: {}", e);
                return;
            }
        };
        thread::spawn(move || {
            let mut buf = [0; 1024];
            loop {
                match socket_clone.recv_from(&mut buf) {
                    Ok((amt, src)) => {
                        let msg = std::str::from_utf8(&buf[..amt]).unwrap_or("[invalid utf8]");
                        {
                            let mut m = messages.lock().unwrap();
                            m.push_back(format!("Received from {src}: {msg}"));
                        }
                    }
                    Err(_) => {
                        thread::sleep(std::time::Duration::from_millis(100));
                    }
                }
            }
        });

        // Sending loop
        loop {
            let msg = format!("hello from {}", my_id);
            let broadcast_addr: SocketAddr =
                format!("255.255.255.255:{}", port).parse().unwrap();

            if let Err(e) = socket.send_to(msg.as_bytes(), &broadcast_addr) {
                eprintln!("Failed to send message: {}", e);
            }

            println!("Sent: {}", msg);
            thread::sleep(std::time::Duration::from_secs(2));
        }
    });
}



