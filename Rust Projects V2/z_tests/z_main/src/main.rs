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
    message::ApplicationMessage,
    self_awareness::{PeerList, broadcast_message_all_peers, init_lan_awareness},
};

use chrono::Utc;
use std::collections::HashMap;
use std::env;
use std::net::SocketAddr;
use std::sync::{LazyLock, RwLock};
use std::thread;
use std::time::Duration;
/*
 ######  ########    ###    ######## ####  ######     ##     ##    ###    ########   ######
##    ##    ##      ## ##      ##     ##  ##    ##    ##     ##   ## ##   ##     ## ##    ##
##          ##     ##   ##     ##     ##  ##          ##     ##  ##   ##  ##     ## ##
 ######     ##    ##     ##    ##     ##  ##          ##     ## ##     ## ########   ######
      ##    ##    #########    ##     ##  ##           ##   ##  ######### ##   ##         ##
##    ##    ##    ##     ##    ##     ##  ##    ##      ## ##   ##     ## ##    ##  ##    ##
 ######     ##    ##     ##    ##    ####  ######        ###    ##     ## ##     ##  ######
*/
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
##     ##    ###    #### ##    ##
###   ###   ## ##    ##  ###   ##
#### ####  ##   ##   ##  ####  ##
## ### ## ##     ##  ##  ## ## ##
##     ## #########  ##  ##  ####
##     ## ##     ##  ##  ##   ###
##     ## ##     ## #### ##    ##
*/
fn main() -> Result<(), Box<dyn ApplicationErrorTrait>> {
    let name: String = match env::args().nth(1) {
        Some(n) => n,
        None => "".to_string(),
    };

    let pl: PeerList;

    println!("#APPLICATION-INIT-LOADING...");
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
                println!("#APPLICATION-UDP-ADDRESS: {j}");
            }
            {
                let mut j = MY_TCP_EXPOSE_ADDR.write().unwrap();
                *j = tcp_addr;
                println!("#APPLICATION-TCP-ADDRESS: {j}");
            }

            peerlist
        };
    }
    println!("#APPLICATION-TESTS-RUNNING:");
    {
        crate::tests::run(false);
    }
    println!("#APPLICATION-TESTS-ENDED");
    println!("#APPLICATION-MAIN-START");

    let simulation = {
        let i = INSTANCE_NAME.read().unwrap();

        let now = Utc::now();

        // Unix timestamp in seconds
        let ts_secs = now.timestamp();

        ApplicationMessage {
            instance_name: i.to_string(),
            timestamp: ts_secs as u64,
            action: "send broadcast test".to_string(),
            changes: Some("value: 42".to_string()),
            state: Some("58 (increment)".to_string()),
        }
    };

    thread::sleep(Duration::from_millis(3000));

    if name == "alice" {
        // Spawn message-sending thread
        let my_peerlist_clone = pl.clone();
        let simulation_clone = simulation.clone();

        thread::spawn(move || {
            loop {
                println!("#sending message");
                broadcast_message_all_peers(&my_peerlist_clone, &simulation_clone);
                thread::sleep(Duration::from_millis(3000));
            }
        });
    }

    // KEEP MAIN RUNNING | TEST ENV.
    loop {
        if false {
            break;
        }
    }
    Ok(())
}
/*

// needs to proper name routes and routines


{ NEEDS TO IMPLEMENT LOG FOR THIS:

    CORE::SELF_AWARENESS:
        /// Logs awareness.
        fn display_aware(peer_list: &PeerList) {}

        /// CHECK IF IT WORKS FOR OTHER MACHINES.
        let addr: SocketAddr = format!("{}:{}", broadcast_addr_discovery(), APPLICATION_UDP_PORT)
             .parse()
             .unwrap();

        /*

        check if can set more configs on create tcp listener

        */

}


{ERROR

/*make error */
impl ApplicationMessage {
    pub fn bytes_to_message(buf: Vec<u8>) -> ApplicationMessage {
        serde_json::from_slice(&buf).expect("Failed to parse payload")
    }
}

}


TO generate sections: https://patorjk.com/software/taag/
> text > banner 3


ADD THE SERVER TO HOLD THE KEYS,
MAKE THIS FILE HOLD KEYS FOR EVERYTHING IT NEEDS,
CHECK SHADCN NOW TO SEE USAGES,

*/

/*

ABOUT THE CRYPTOGRAPHY USED, NEEDS TO IMPLEMENT LOGIC ON LAST SEEN, SEQ NUMBERS TO PREVENT ATTACKS.

Replay attacks        Partially covered                             We include seq numbers and timestamps in the signed
                                                                    payload. Nodes must persist last_seq_seen and reject
                                                                    duplicates for full protection.


*/
