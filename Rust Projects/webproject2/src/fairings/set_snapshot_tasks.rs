use rocket::fairing::AdHoc;
use rocket::tokio::spawn;

use crate::config::db_config;
use crate::db::snapshot::task_periodic_collection_snapshot;
use crate::logger::log_fairing_running;

pub fn run() -> AdHoc {
    AdHoc::on_ignite("Snapshot Saver", move |rocket| async move {
        
        log_fairing_running("Set Snapshot Tasks");

        for n in db_config::COLLECTION_NAMES.to_vec() {
            let task_name = n.to_string();
            
            spawn(async move {
                if let Err(e) = task_periodic_collection_snapshot(task_name).await {
                    eprintln!("❌ Error in snapshot task: {}", e);
                }
            });
        }

        rocket 
    })
}
