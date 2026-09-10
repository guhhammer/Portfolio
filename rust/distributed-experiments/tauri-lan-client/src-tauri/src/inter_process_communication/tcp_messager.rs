use crate::inter_process_communication::others_finder::{Peer, PeerList};
use std::io::{self, Read, Write};
use std::net::{TcpListener, TcpStream};
use std::thread;
use std::time::Duration;

fn create_tcp_listener(addr: &str) -> io::Result<TcpListener> {
    let listener = TcpListener::bind(addr)?;
    listener.set_nonblocking(true)?;
    Ok(listener)
}

fn start_tcp_listener(listener: TcpListener) {
    println!("[LISTENER] Running on {}", listener.local_addr().unwrap());

    thread::spawn(move || {
        for stream in listener.incoming() {
            match stream {
                Ok(mut stream) => {
                    thread::spawn(move || {
                        let mut buf = [0u8; 1024];
                        loop {
                            match stream.read(&mut buf) {
                                Ok(0) => break, // closed
                                Ok(n) => {
                                    let msg = String::from_utf8_lossy(&buf[..n]);
                                    println!("[RECEIVED] {}", msg);
                                }
                                Err(_) => break,
                            }
                        }
                    });
                }
                Err(ref e) if e.kind() == io::ErrorKind::WouldBlock => {
                    // no connection yet
                    thread::sleep(Duration::from_millis(100));
                }
                Err(e) => eprintln!("Listener error: {}", e),
            }
        }
    });
}

pub fn send_tcp_message(addr: &str, msg: &str) {
    if let Ok(mut stream) = TcpStream::connect(addr) {
        let _ = stream.write_all(msg.as_bytes());
    } else {
        eprintln!("Failed to connect to {}", addr);
    }
}

fn start_user_input(peer_list: &PeerList, msg: &str) {
    {
        peer_list
            .lock()
            .unwrap()
            .iter()
            .map(|p: &Peer| p.addr.to_string())
            .for_each(|addr| {
                let _ = send_tcp_message(&addr, &msg);
            });
    }
}

pub fn run_tcp_messager(peer_list: &PeerList) {
    let tcp_bind_addr = "0.0.0.0:15000".to_string();

    let listener = create_tcp_listener(&tcp_bind_addr).unwrap();
    start_tcp_listener(listener);

    // Spawn message-sending thread
    let peer_list_clone = peer_list.clone();
    thread::spawn(move || {
        loop {
            thread::sleep(Duration::from_millis(3000));
            println!("#sending message");
            start_user_input(&peer_list_clone, "Hello from here | tcp");
        }
    });
}
