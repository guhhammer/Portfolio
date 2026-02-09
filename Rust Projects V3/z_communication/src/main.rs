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

    loop {}
}

/*

Got it 👍 You’re basically building the skeleton of a **distributed system**:

* **UDP** for **discovery** (who is alive in the LAN, what’s their ID, IP/port, etc.)
* **TCP** for **reliable messaging** (send JSON, update peer state, maybe request/response).

That’s already the **minimum viable communication layer**. But in practice, professional distributed apps add a few extra building blocks to make it really work. Let me break it down:

---

## 🔹 What you already have

* Peer discovery (UDP broadcast → find others).
* Peer-to-peer messaging (TCP → exchange JSON).
* A way to interpret messages and mutate state.

That’s enough for a simple LAN-based distributed chat or toy system.

---

## 🔹 What you’ll likely need for a *real distributed app*

1. **Message format & schema**

   * JSON works, but you may want something stricter like **MessagePack**, **CBOR**, or even **protobufs**.
   * Helps you evolve the protocol without breaking older versions.

2. **Message types**

   * You’ll want a small “protocol” of message kinds:

     ```json
     { "type": "ping", "from": "NODE_A" }
     { "type": "state_update", "data": { ... } }
     { "type": "command", "action": "restart" }
     ```
   * This allows your `interpret_message()` function to switch based on `type`.

3. **Heartbeat / Liveness detection**

   * Periodically ping peers to make sure they’re alive.
   * If no response in X seconds → remove from peer list.

4. **Peer state replication**

   * When a peer joins, it should ask someone else for the **current state snapshot**.
   * Then apply incremental updates from messages.

5. **Conflict resolution**

   * What happens if two peers update the same value at the same time?
   * Simple options: “last write wins”, “whoever has higher ID wins”, or something like **CRDTs** if you want correctness.

6. **Persistence**

   * Store peer state in a file/DB so an instance can crash/restart without losing everything.

7. **Security**

   * Even on LAN, TCP without authentication = anyone could impersonate another node.
   * Add simple signing (HMAC with a shared secret) or TLS if needed.

---

## 🔹 Things you may add later (depending on goals)

* **Leader election** (one peer becomes “manager” if needed, others follow).
* **Versioning** (so older/newer peers can still talk).
* **Retry & reconnection logic** (if TCP drops, auto-reconnect).
* **Load balancing / sharding** (if you go beyond a small LAN demo).

---

✅ So in short:

* For a **toy system**, what you have (UDP discovery + TCP JSON messaging + interpreter) is enough.
* For a **serious distributed app**, you’ll want heartbeats, state sync, conflict resolution, and persistence at minimum.

---

👉 Do you want me to sketch out a **JSON message protocol** for your app (like a small enum of message types with example payloads) so you can start writing the interpreter cleanly?


*/
