use rocket::fairing::AdHoc;
use mongodb::{bson::Document, Collection};

use crate::{logger::log_fairing_running, utils::populate_db::populate};

pub fn run(x: Vec<Collection<Document>>) -> AdHoc {
    AdHoc::on_ignite("Populate With Examples", move |rocket| async move {
     
        log_fairing_running("Populater");

        populate(&x).await.unwrap(); // SET CONFIG OPTIONS.
        // 
        // TO SEE IF YOU POPULATED THE DB, FOLLOW THE STEPS IN MIGRATIONS/TUTORIAL.MD.

        rocket 
    })
}