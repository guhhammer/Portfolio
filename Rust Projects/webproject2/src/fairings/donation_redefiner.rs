use rocket::fairing::AdHoc;

use crate::logger::log_fairing_running;
use crate::utils::{adjust_donation_date_example, adjust_donation_transactions};
use crate::config::db_config;

pub fn run() -> AdHoc {
    AdHoc::on_ignite("Donation Redefiner", move |rocket| async move {
        
        log_fairing_running("Donation Redefiner");

        if db_config::POPULATE_DB_EXAMPLE {

            let _ = adjust_donation_transactions::transactions_updater().await;
    
            let _ = adjust_donation_date_example::date_update().await;
        
        }

        rocket 
    })
}
