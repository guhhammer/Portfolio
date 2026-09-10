use rocket::fairing::AdHoc;
use mongodb::Database;
use std::process;

use crate::{config::db_config, db::load_from_snapshot::load_collection_snapshot, logger::log_fairing_running};

pub fn run(appdb: Database) -> AdHoc {
    
    AdHoc::on_ignite("Snapshots Loader", move |rocket| async move {
        
        log_fairing_running("Snapshots Loader");

        if db_config::LOAD_FROM_LAST_SNAPSHOTS {

            for n in db_config::COLLECTION_NAMES.to_vec() {
                
                let status = load_collection_snapshot(&appdb, n).await;
    
                if status == 1 {
                    
                    println!("Error loading existing snapshot from {} collection", n);
    
                    process::exit(1);
                    // end process if snapshot is bad formatted somehow.
                
                }
    
            }
                
        }
       
        rocket 

    })
}





