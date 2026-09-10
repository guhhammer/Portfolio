# Gas Station Desktop

Desktop client for the [gas-station system](../gas-station-api/), built with Tauri (Rust shell) and a React + TypeScript front end (Vite).

The interesting part is the Rust side: `src-tauri/src/distributed.rs` joins a UDP broadcast group on the local network, announces the client every couple of seconds and collects what the other stations broadcast. The React page `DistributedTest.tsx` starts a node with a chosen id and polls the received messages through Tauri commands (`start_distributed`, `get_messages`, `greet`). This is the same LAN-discovery technique later used by the [encrypted messenger](../p2p-encrypted-messenger/).

## Run

Tauri needs GTK/WebKit system packages on Linux; the list is in [`../notes/tauri-setup.md`](../notes/tauri-setup.md).

```bash
npm install
npm run tauri dev      # opens the desktop window
cargo check --manifest-path src-tauri/Cargo.toml   # Rust side only
```
