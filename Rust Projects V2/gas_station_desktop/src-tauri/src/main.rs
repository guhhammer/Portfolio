#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::collections::VecDeque;
use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;
use std::net::{UdpSocket, SocketAddr};
use std::str;
use tauri::State;

mod distributed;
use distributed::{start, MessageQueue};


// --------------------
// Commands
// --------------------
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[tauri::command]
fn get_messages(messages: State<MessageQueue>) -> Vec<String> {
    let mut queue = messages.lock().unwrap();
    queue.drain(..).collect()
}

#[tauri::command]
fn start_distributed(my_id: String, messages: State<MessageQueue>) {
    let messages_clone = messages.inner().clone();
    start(my_id, messages_clone);
}

// --------------------
// Main
// --------------------
fn main() {
    let messages: MessageQueue = Arc::new(Mutex::new(VecDeque::new()));

    tauri::Builder::default()
        .manage(messages)
        .invoke_handler(tauri::generate_handler![
            greet,
            get_messages,
            start_distributed
        ])
        .run(tauri::generate_context!())
        .expect("error while running Tauri app");
}
