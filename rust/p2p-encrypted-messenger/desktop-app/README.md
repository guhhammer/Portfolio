# Messenger desktop app (Tauri + React)

Desktop front end of the [peer-to-peer encrypted messenger](../README.md). The Rust side (`src-tauri/`) embeds the same core as the command-line node; the React side (`src/`) is a login page and a chat page that call Tauri commands (`get_messages`, `send_message`) and refresh every few seconds. UI components under `src/components/ui` are shadcn/ui-style primitives (Radix + Tailwind).

```bash
npm install
npm run tauri dev                                   # desktop window
cargo check --manifest-path src-tauri/Cargo.toml    # Rust side only
```

System packages for Tauri on Debian are listed in [`../../notes/tauri-setup.md`](../../notes/tauri-setup.md).
