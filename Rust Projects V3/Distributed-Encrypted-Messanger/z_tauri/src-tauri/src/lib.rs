/*
##     ##  #######  ########  ##     ## ##       ########  ######
###   ### ##     ## ##     ## ##     ## ##       ##       ##    ##
#### #### ##     ## ##     ## ##     ## ##       ##       ##
## ### ## ##     ## ##     ## ##     ## ##       ######    ######
##     ## ##     ## ##     ## ##     ## ##       ##             ##
##     ## ##     ## ##     ## ##     ## ##       ##       ##    ##
##     ##  #######  ########   #######  ######## ########  ######
*/
mod adapters;
mod config;
mod core;
mod errors;
mod routines;
mod tests;
/*
#### ##     ## ########   #######  ########  ########  ######
 ##  ###   ### ##     ## ##     ## ##     ##    ##    ##    ##
 ##  #### #### ##     ## ##     ## ##     ##    ##    ##
 ##  ## ### ## ########  ##     ## ########     ##     ######
 ##  ##     ## ##        ##     ## ##   ##      ##          ##
 ##  ##     ## ##        ##     ## ##    ##     ##    ##    ##
#### ##     ## ##         #######  ##     ##    ##     ######
*/
use crate::core::{
    interfaces::ApplicationErrorTrait,
    logger::{log_info, log_start},
    message::ApplicationMessage,
    self_awareness::{broadcast_message_all_peers, init_lan_awareness, PeerList},
};
use chrono::Utc;
use once_cell::sync::Lazy;
use std::{
    collections::HashMap,
    env,
    net::SocketAddr,
    sync::{Arc, LazyLock, Mutex, RwLock},
};
use tauri::State;
/*
 ######  ######## ########  ##     ##  ######  ########  ######
##    ##    ##    ##     ## ##     ## ##    ##    ##    ##    ##
##          ##    ##     ## ##     ## ##          ##    ##
 ######     ##    ########  ##     ## ##          ##     ######
      ##    ##    ##   ##   ##     ## ##          ##          ##
##    ##    ##    ##    ##  ##     ## ##    ##    ##    ##    ##
 ######     ##    ##     ##  #######   ######     ##     ######
*/
#[derive(Debug, Clone)]
pub struct AppState {
    peerlist: Arc<RwLock<PeerList>>,
    messages: Arc<RwLock<Vec<ApplicationMessage>>>,
}
/*
 ######  ########    ###    ######## ####  ######     ##     ##    ###    ########   ######
##    ##    ##      ## ##      ##     ##  ##    ##    ##     ##   ## ##   ##     ## ##    ##
##          ##     ##   ##     ##     ##  ##          ##     ##  ##   ##  ##     ## ##
 ######     ##    ##     ##    ##     ##  ##          ##     ## ##     ## ########   ######
      ##    ##    #########    ##     ##  ##           ##   ##  ######### ##   ##         ##
##    ##    ##    ##     ##    ##     ##  ##    ##      ## ##   ##     ## ##    ##  ##    ##
 ######     ##    ##     ##    ##    ####  ######        ###    ##     ## ##     ##  ######
 */
pub static APP_STATE: Lazy<Arc<RwLock<AppState>>> = Lazy::new(|| {
    Arc::new(RwLock::new(AppState {
        peerlist: Arc::new(RwLock::new(Arc::new(Mutex::new(Vec::new())))),
        messages: Arc::new(RwLock::new(Vec::new())),
    }))
});

pub static INIT_COMPLETE: LazyLock<RwLock<bool>> = LazyLock::new(|| RwLock::new(false));

pub static INSTANCE_NAME: LazyLock<RwLock<String>> = LazyLock::new(|| RwLock::new("".to_string()));

pub static MY_UDP_EXPOSE_ADDR: LazyLock<RwLock<String>> =
    LazyLock::new(|| RwLock::new("".to_string()));

pub static MY_TCP_EXPOSE_ADDR: LazyLock<RwLock<String>> =
    LazyLock::new(|| RwLock::new("".to_string()));

pub static PRIV_KEY: LazyLock<RwLock<String>> = LazyLock::new(|| RwLock::new("".to_string()));

pub static PUB_KEYS: LazyLock<RwLock<HashMap<String, String>>> =
    LazyLock::new(|| RwLock::new(HashMap::new()));

/*
########    ###    ##     ## ########  ####     ######  ##     ## ########   ######
   ##      ## ##   ##     ## ##     ##  ##     ##    ## ###   ### ##     ## ##    ##
   ##     ##   ##  ##     ## ##     ##  ##     ##       #### #### ##     ## ##
   ##    ##     ## ##     ## ########   ##     ##       ## ### ## ##     ##  ######
   ##    ######### ##     ## ##   ##    ##     ##       ##     ## ##     ##       ##
   ##    ##     ## ##     ## ##    ##   ##     ##    ## ##     ## ##     ## ##    ##
   ##    ##     ##  #######  ##     ## ####     ######  ##     ## ########   ######
*/
// Learn more about Tauri commands at https://tauri.app/develop/calling-rust/

/* EXAMPLE FUNCTION BELOW: */
// #[tauri::command]
// fn greet(name: &str) -> String {
//     format!("Hello, {}! You've been greeted from Rust!", name)
// }

#[tauri::command]
fn get_messages(state: State<Arc<RwLock<AppState>>>) -> Vec<ApplicationMessage> {
    state
        .read()
        .unwrap()
        .messages
        .read()
        .expect("SOMETHING WRONG")
        .clone()
}

#[tauri::command]
fn send_message(msg: &str, state: State<Arc<RwLock<AppState>>>) {
    let i = INSTANCE_NAME.read().unwrap();

    let now = Utc::now();

    // Unix timestamp in seconds
    let ts_secs = now.timestamp();

    let sim = ApplicationMessage {
        instance_name: i.to_string(),
        timestamp: ts_secs as u64,
        action: "send broadcast test".to_string(),
        changes: Some(msg.to_string()),
        state: Some("58 (increment)".to_string()),
    };

    let my_peerlist_clone = state
        .read()
        .unwrap()
        .peerlist
        .read()
        .expect("SEND ERROR")
        .clone();

    log_info("#sending message");
    broadcast_message_all_peers(&my_peerlist_clone, &sim);
}
/*
########  ##     ## ##    ##
##     ## ##     ## ###   ##
##     ## ##     ## ####  ##
########  ##     ## ## ## ##
##   ##   ##     ## ##  ####
##    ##  ##     ## ##   ###
##     ##  #######  ##    ##
*/
#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() -> Result<(), Box<dyn ApplicationErrorTrait>> {
    log_start();

    let pl: PeerList;

    log_info("#APPLICATION-INIT-LOADING...");
    {
        crate::routines::run();
        while !*INIT_COMPLETE.read().unwrap() {}

        pl = {
            let i = INSTANCE_NAME.read().unwrap();

            let (udp_addr, tcp_addr, peerlist): (SocketAddr, String, PeerList) =
                init_lan_awareness(i.to_string())?;

            {
                let mut j = MY_UDP_EXPOSE_ADDR.write().unwrap();
                *j = format!("{udp_addr}");
                log_info(&format!("#APPLICATION-UDP-ADDRESS: {j}"));
            }
            {
                let mut j = MY_TCP_EXPOSE_ADDR.write().unwrap();
                *j = tcp_addr;
                log_info(&format!("#APPLICATION-TCP-ADDRESS: {j}"));
            }

            peerlist
        };
    }

    {
        let mut a = APP_STATE.write().unwrap();
        a.peerlist = Arc::new(RwLock::new(pl));
    }

    log_info("#APPLICATION-TESTS-RUNNING:");
    {
        crate::tests::run(false);
    }
    log_info("#APPLICATION-TESTS-ENDED");
    log_info("#APPLICATION-MAIN-START");

    tauri::Builder::default()
        .manage(APP_STATE.clone())
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![send_message, get_messages])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");

    Ok(())
}

/*

// needs to proper name routes and routines

/*
CORE::SELF_AWARENESS:

check if can set more configs on create tcp listener

*/

CONVERT ALL EXPECTS AND PANICS INTO ApplicationError.

TO generate sections: https://patorjk.com/software/taag/
> text > banner 3


ADD THE SERVER TO HOLD THE KEYS,

*/

/*

ABOUT THE CRYPTOGRAPHY USED, NEEDS TO IMPLEMENT LOGIC ON LAST SEEN, SEQ NUMBERS TO PREVENT ATTACKS.

Replay attacks        Partially covered                             We include seq numbers and timestamps in the signed
                                                                    payload. Nodes must persist last_seq_seen and reject
                                                                    duplicates for full protection.


*/
