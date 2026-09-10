# Rust

Backend services, a peer-to-peer encrypted messenger, desktop apps and an algorithms library, all written in Rust. This is the language I use for production work; the projects below are the ones that live in this portfolio. My larger Rust applications have their own repositories and are listed in [`external-projects/`](external-projects/).

Every crate in this folder compiles with a current stable toolchain (`cargo check` was run on each one in September 2026).

## What to look at first

| Project | What it is | Shows |
| --- | --- | --- |
| [`gas-station-api/`](gas-station-api/) | REST API that manages a fuel station's pump grid: prices, fuel supply, live flow simulation, persistence | Rocket, MongoDB, JWT + Argon2 authentication, request guards, TLS, structured logging, Docker |
| [`p2p-encrypted-messenger/`](p2p-encrypted-messenger/) | Messenger where peers find each other on the local network and exchange end-to-end encrypted messages | X25519 key exchange, ChaCha20-Poly1305, Ed25519 signatures, UDP discovery, TCP messaging, hexagonal architecture, Tauri desktop client |
| [`rocket-mongodb-web-app/`](rocket-mongodb-web-app/) | Server-rendered donation platform for creators and non-profits, with an Ethereum donation contract | Rocket + Tera templates, MongoDB CRUD and snapshots, Docker Compose, Hardhat/Truffle |
| [`algorithms-library/`](algorithms-library/) | 80+ classic algorithms and data structures implemented from scratch | Generics, ownership, iterators, testing by example |
| [`gas-station-desktop/`](gas-station-desktop/) and [`gas-station-global-server/`](gas-station-global-server/) | Desktop client and central aggregator for the gas-station system | Tauri + React, multi-service design |

## Folder map

```
rust/
├── gas-station-api/            Rocket REST API + MongoDB + JWT (the main backend project)
├── gas-station-desktop/        Tauri + React desktop client for the API
├── gas-station-global-server/  central server every station reports to
├── rocket-mongodb-web-app/     creator-donation website (Rocket, Tera, MongoDB, Docker Compose)
├── p2p-encrypted-messenger/    node, key server and desktop app of the encrypted messenger
├── algorithms-library/         sorting, searching, graphs, DP, crypto, math, data structures
├── distributed-experiments/    UDP LAN discovery, TCP peer messaging, Tauri LAN client
├── experiments/                small crates written while learning a library or a feature
├── notes/                      cheat sheets and design notes I keep while working
└── external-projects/          one text file per Rust project that lives in its own repository
```

## How to run any crate

```bash
cd rust/<project>
cargo run            # or: cargo check, cargo test, cargo build --release
```

Projects that need extra services (MongoDB, a browser, GTK for Tauri) say so in their own README.

## Related

- Rust on Solana (Anchor programs): [`../blockchain/solana/`](../blockchain/solana/)
- Docker files used by these services are indexed in [`../docker-devops/`](../docker-devops/)
- Rust course certificates (Duke University specialization): [`../certificates/`](../certificates/)
