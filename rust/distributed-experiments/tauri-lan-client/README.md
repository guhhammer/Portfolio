# Tauri LAN client

A Tauri (Rust + React/TypeScript) desktop app that discovers other instances on the local network over UDP broadcast and shows them in the window. The Rust side lives in `src-tauri/src/`: the discovery loop, the peer list, and an `error_catalog` module that turns socket and parsing failures into typed application errors.

```bash
npm install
npm run tauri dev                                   # desktop window
cargo check --manifest-path src-tauri/Cargo.toml    # Rust side only
```

Tauri system packages for Debian are listed in [`../../notes/tauri-setup.md`](../../notes/tauri-setup.md).
