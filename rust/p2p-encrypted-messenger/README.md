# Peer-to-Peer Encrypted Messenger

A messenger for a local network with no message server: every node discovers the others by UDP broadcast, agrees on a shared secret with each peer using X25519, and sends messages over TCP encrypted with ChaCha20-Poly1305. Identities are Ed25519 keys. A small Rocket "key server" bootstraps the demo network, and a Tauri desktop app wraps the same core for people who prefer a window to a terminal.

This is the most architecturally mature project in the portfolio and the prototype of [Vega](../external-projects/vega.txt), the fully serverless successor that runs on the public internet.

For a non-technical reader: two laptops on the same Wi-Fi can chat, and even the server that helped them find each other cannot read the messages.

## The three crates

| Crate | Folder | Role |
| --- | --- | --- |
| `messenger_node` | [`node/`](node/) | Command-line peer. Registers with the key server, discovers peers, encrypts and broadcasts messages. `examples/crypto_demo.rs` walks through the cryptography step by step. |
| `messenger_key_server` | [`key-server/`](key-server/) | Rocket HTTP service on port 8000 that registers nodes and hands out key material. Acceptable only inside the trusted demo LAN; a production design generates keys on-device (see Vega). |
| `messenger_desktop` | [`desktop-app/`](desktop-app/) | Tauri desktop app: the same core (`src-tauri/src/core`, `adapters`, `routines`) behind a React + TypeScript + Tailwind interface with a login page and a chat page. `src-tauri/src/tests/security.rs` exercises signing, key exchange and encryption end to end. |

## Design

The node follows a hexagonal (ports-and-adapters) layout:

```
node/src/
├── core/           domain logic: crypt (X25519 + ChaCha20-Poly1305), instance identity,
│                   message and payload types, interpreter, self_awareness (LAN discovery), error type
├── adapters/       HTTP calls to the key server (GET/POST routes)
├── routines/       startup routine: register, fetch keys, keep them fresh
├── errors/         per-domain error constants (crypt, lan, server)
├── config.rs       ports, timers, server address
└── main.rs         wires everything and parks the main thread
```

Key points:

- **Errors, not panics**: every fallible path returns `Result<_, ApplicationError>`; startup uses a channel to wait for the first key sync instead of busy-waiting.
- **Discovery**: UDP broadcast on a fixed port announces `INSTANCE_NAME` (derived from the machine's MAC address); peers are kept in a shared `PeerList`.
- **Transport**: a TCP listener per node; messages are serialized with Serde, encrypted, base64-encoded and signed.
- **Cryptography**: `ed25519-dalek` (identity/signatures), `x25519-dalek` (Diffie-Hellman), `chacha20poly1305` (AEAD), `sha2` and `hmac` (fingerprints and integrity).

## Run the demo

```bash
# terminal 1: key server
cd key-server && cargo run                 # http://127.0.0.1:8000

# terminal 2 and 3: two nodes on the same machine or LAN
cd node && cargo run -- alice              # "alice" broadcasts an encrypted message every 3 s
cd node && cargo run -- bob                # bob receives and decrypts it

# cryptography walkthrough only (no network)
cd node && cargo run --example crypto_demo
```

Desktop app (needs the GTK/WebKit packages listed in [`../notes/tauri-setup.md`](../notes/tauri-setup.md)):

```bash
cd desktop-app
npm install
npm run tauri dev
```
