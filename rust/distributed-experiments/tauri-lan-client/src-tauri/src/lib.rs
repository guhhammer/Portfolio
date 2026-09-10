
// Learn more about Tauri commands at https://tauri.app/develop/calling-rust/

mod error_catalog;
mod inter_process_communication;

use crate::inter_process_communication::naming::generate_instance_name;
use crate::inter_process_communication::others_finder::PeerList;
use crate::inter_process_communication::others_finder::display_peerlist;
use crate::inter_process_communication::others_finder::run_discovery;
use crate::inter_process_communication::tcp_messager::run_tcp_messager;
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

#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

// remember to call `.manage(MyState::default())`
#[tauri::command]
fn show() -> String {
    error_catalog::mod_tester::run();
    "ok".to_string()
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {

    let instance_name: String = generate_instance_name();

    let peer_list: PeerList = aware_of_lan(instance_name);

    aware_tcp(&peer_list);

    tauri::Builder::default()
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![greet, show])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}


// need to refactor the inter_process_communication completely.