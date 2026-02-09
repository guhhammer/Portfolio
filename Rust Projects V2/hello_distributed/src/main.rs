use socket2::{Socket, Domain, Type, Protocol};
use std::net::{UdpSocket, SocketAddr};
use std::str;
use std::thread;
use std::time::Duration;
use std::env;

fn main() -> std::io::Result<()> {
    let args: Vec<String> = env::args().collect();
    
    if args.len() < 2 {
        eprintln!("Usage: cargo run <id>");
        std::process::exit(1);
    }
    let my_id = args[1].clone();

    let port = 34254;

    // Create socket with reuse options
    let socket = Socket::new(Domain::IPV4, Type::DGRAM, Some(Protocol::UDP))?;
    socket.set_reuse_address(true)?;

    // Bind to 0.0.0.0:port
    let addr: SocketAddr = format!("0.0.0.0:{}", port).parse().unwrap();
    socket.bind(&addr.into())?;

    let socket: UdpSocket = socket.into();
    socket.set_broadcast(true)?;
    socket.set_nonblocking(true)?;

    let socket_clone = socket.try_clone()?;
    thread::spawn(move || {
        let mut buf = [0; 1024];
        loop {
            match socket_clone.recv_from(&mut buf) {
                Ok((amt, src)) => {
                    let msg = str::from_utf8(&buf[..amt]).unwrap_or("[invalid utf8]");
                    println!("Received from {}: {}", src, msg);
                }
                Err(_) => {
                    thread::sleep(Duration::from_millis(100));
                }
            }
        }
    });

    loop {
        let msg = format!("hello from {}", my_id);
        let broadcast_addr: SocketAddr = format!("255.255.255.255:{}", port).parse().unwrap();
        socket.send_to(msg.as_bytes(), &broadcast_addr)?;
        println!("Sent: {}", msg);
        thread::sleep(Duration::from_secs(2));
    }
}
