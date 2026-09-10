//! Peer node of the distributed encrypted messenger.
//!
//! On startup the node registers with the key server, discovers peers on the
//! local network over UDP broadcast, and exchanges end-to-end encrypted
//! messages with them over TCP (X25519 key exchange + ChaCha20-Poly1305).

mod adapters;
mod config;
mod core;
mod errors;
mod routines;

use crate::core::error::ApplicationError;
use crate::core::message::ApplicationMessage;
use crate::core::self_awareness::{broadcast_message_all_peers, init_lan_awareness};

use chrono::Utc;
use std::collections::HashMap;
use std::env;
use std::sync::{LazyLock, RwLock};
use std::thread;
use std::time::Duration;

/// Human-readable identity of this node, derived from the machine's MAC address.
pub static INSTANCE_NAME: LazyLock<RwLock<String>> = LazyLock::new(|| RwLock::new(String::new()));

/// This node's X25519 private key (base64), fetched from the key server.
pub static PRIV_KEY: LazyLock<RwLock<String>> = LazyLock::new(|| RwLock::new(String::new()));

/// Known peers' X25519 public keys (base64), keyed by instance name.
pub static PUB_KEYS: LazyLock<RwLock<HashMap<String, String>>> =
    LazyLock::new(|| RwLock::new(HashMap::new()));

fn main() -> Result<(), ApplicationError> {
    let name = env::args().nth(1).unwrap_or_default();

    println!("#APPLICATION-INIT-LOADING...");

    // Register with the key server and wait until the first key sync finishes.
    routines::run()
        .recv()
        .expect("init routine terminated unexpectedly")?;

    let instance_name = INSTANCE_NAME.read().unwrap().clone();
    let (udp_addr, tcp_addr, peer_list) = init_lan_awareness(instance_name.clone())?;

    println!("#APPLICATION-UDP-ADDRESS: {udp_addr}");
    println!("#APPLICATION-TCP-ADDRESS: {tcp_addr}");
    println!("#APPLICATION-MAIN-START");

    // Demo driver: the instance started as "alice" periodically broadcasts an
    // encrypted message to every discovered peer.
    if name == "alice" {
        let message = ApplicationMessage {
            instance_name,
            timestamp: Utc::now().timestamp() as u64,
            action: "send broadcast test".to_string(),
            changes: Some("value: 42".to_string()),
            state: Some("58 (increment)".to_string()),
        };

        thread::spawn(move || {
            loop {
                thread::sleep(Duration::from_secs(3));
                println!("#sending message");
                broadcast_message_all_peers(&peer_list, &message);
            }
        });
    }

    // All work happens on background threads; keep the main thread parked.
    loop {
        thread::park();
    }
}
