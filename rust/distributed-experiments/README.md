# Distributed experiments

Three small crates written while working out how machines on the same network can find and talk to each other without a central server. The techniques ended up in the [gas-station desktop client](../gas-station-desktop/) and the [encrypted messenger](../p2p-encrypted-messenger/).

| Crate | What it does |
| --- | --- |
| [`udp-lan-discovery/`](udp-lan-discovery/) | Binds a reusable UDP socket, broadcasts its id every few seconds and prints every other id it hears. Run two copies with different ids (`cargo run -- a`, `cargo run -- b`) and watch them see each other. |
| [`lan-peer-communication/`](lan-peer-communication/) | Adds a peer list, instance names derived from the MAC address, and a TCP messenger on top of the discovery loop; includes a `Dockerfile` so several containers can form a network. |
| [`tauri-lan-client/`](tauri-lan-client/) | The same discovery logic inside a Tauri desktop app, plus an error-catalog module that maps low-level failures to typed application errors. |

Each crate builds with `cargo check`; the Tauri one needs the packages listed in [`../notes/tauri-setup.md`](../notes/tauri-setup.md).
