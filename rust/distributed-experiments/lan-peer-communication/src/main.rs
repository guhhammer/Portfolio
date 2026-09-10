mod communication;

use crate::communication::naming::generate_instance_name;
use crate::communication::others_finder::PeerList;
use crate::communication::others_finder::display_peerlist;
use crate::communication::others_finder::run_discovery;
use crate::communication::tcp_messager::run_tcp_messager;
use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

const DISPLAY_PEERLIST_SHOW: bool = true;
const DISPLAY_PEERLIST_TIME: Duration = Duration::from_millis(5000);

fn aware_of_lan(instance_name: String) -> PeerList {
    let peer_list: PeerList = Arc::new(Mutex::new(Vec::new()));

    println!("#ME: {instance_name}");

    let peer_list_clone = peer_list.clone();
    thread::spawn(move || {
        let _ = run_discovery(instance_name, &peer_list_clone, false);
    });

    display_aware(&peer_list);

    peer_list
}

fn display_aware(peer_list: &PeerList) {
    let peer_list_clone = peer_list.clone();

    thread::spawn(move || {
        loop {
            if DISPLAY_PEERLIST_SHOW {
                std::thread::sleep(DISPLAY_PEERLIST_TIME);
                println!(
                    "#Aware of (Peerlist): {}",
                    display_peerlist(&peer_list_clone)
                );
            }
        }
    });
}

fn aware_tcp(peer_list: &PeerList) {
    let peer_list_clone = peer_list.clone();

    run_tcp_messager(&peer_list_clone);
}

fn main() -> std::io::Result<()> {
    let instance_name: String = generate_instance_name();

    let peer_list: PeerList = aware_of_lan(instance_name);

    aware_tcp(&peer_list);

    // All work happens on background threads; keep the main thread parked.
    loop {
        std::thread::park();
    }
}

// Roadmap toward a production-grade communication layer: typed message kinds,
// heartbeats and liveness detection, state snapshots for joining peers,
// conflict resolution, persistence, and authenticated transport. The full
// version of these ideas lives in Distributed-Encrypted-Messenger.
