//! Desktop client for the gas-station system (Tauri shell).

#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::collections::VecDeque;
use std::sync::{Arc, Mutex};
use tauri::State;

mod distributed;
use distributed::{MessageQueue, start};

#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {name}! You've been greeted from Rust!")
}

/// Drain and return every message received from the network so far.
#[tauri::command]
fn get_messages(messages: State<MessageQueue>) -> Vec<String> {
    let mut queue = messages.lock().unwrap();
    queue.drain(..).collect()
}

/// Join the LAN broadcast network under the given id.
#[tauri::command]
fn start_distributed(my_id: String, messages: State<MessageQueue>) {
    start(my_id, messages.inner().clone());
}

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
