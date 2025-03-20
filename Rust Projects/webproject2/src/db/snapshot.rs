use mongodb::{options::ClientOptions, Client};
use serde::{Deserialize, Serialize};
use tokio::fs;
use tokio::{task, time::sleep};
use futures::TryStreamExt;
use chrono::prelude::*;
use std::error::Error;
use serde_json;
use chrono::{Local, NaiveDate};

use crate::logger::{log_simple_info, log_simple_warn};
use crate::models::{trending::Trending, user::User, donation::Donation};
use crate::{logger::log_simple_error, config::db_config};

// Define a common enum for both User and Donation
#[derive(Serialize, Deserialize)]
pub enum DatabaseState {
    Users(Vec<User>),
    Donations(Vec<Donation>),
    Trendings(Vec<Trending>), 
}

fn create_snapshot_filename(prefix: &str) -> String {
    let local_time: DateTime<Local> = Local::now();

    local_time
        .format(format!("snapshot_{}_%Y%m%d_%H%M%S.json", prefix).as_str())
        .to_string()
}

async fn get_database_state(name: &str) -> Result<DatabaseState, Box<dyn Error + Send + Sync>> {
    let client = Client::with_options(ClientOptions::parse(db_config::DATABASE_URI).await?)?;
    let database = client.database(db_config::DATABASE_NAME);

    match name {
        "users" => {
            let collection = database.collection::<User>(name);
            let users: Vec<User> = collection.find(None, None).await?.try_collect().await?;
            Ok(DatabaseState::Users(users))
        }
        "donations" => {
            let collection = database.collection::<Donation>(name);
            let donations: Vec<Donation> = collection.find(None, None).await?.try_collect().await?;
            Ok(DatabaseState::Donations(donations))
        }
        "trending" => {
            let collection = database.collection::<Trending>(name);
            let trendings: Vec<Trending> = collection.find(None, None).await?.try_collect().await?;
            Ok(DatabaseState::Trendings(trendings))
        }
        _ => Err(Box::new(std::io::Error::new(
            std::io::ErrorKind::InvalidInput,
            "Invalid collection name",
        ))),
    } 
}

async fn save_snapshot_to_file(
    name: &str,
    state: &DatabaseState,
) -> Result<(), Box<dyn Error + Send + Sync>> {

    let file_path = format!(
        "{}{}",
        db_config::SNAPSHOTS_FILE_PATH,
        create_snapshot_filename(name)
    );

    let file = std::fs::File::create(file_path)?;

    match state {
        DatabaseState::Users(users) => {
            serde_json::to_writer(file, &users)?;
        }
        DatabaseState::Donations(donations) => {
            serde_json::to_writer(file, &donations)?;
        }
        DatabaseState::Trendings(trendings) => {
            serde_json::to_writer(file, &trendings)?;
        }
    }

    Ok(())
}

async fn set_daily_folder() -> () {

    if let Err(e) = fs::create_dir_all(db_config::DAILY_FOLDER_PATH).await {

        log_simple_warn(format!("Failed to Recreate Daily Directory: {}.", e));

    }

}

fn extract_date_from_filename(filename: &str) -> Option<NaiveDate> {
    let parts: Vec<&str> = filename.split('_').collect();
    if parts.len() < 3 {
        return None;
    }
    
    // YYYYMMDD is always at index 2
    let date_str = parts[2];
    NaiveDate::parse_from_str(date_str, "%Y%m%d").ok()
}

async fn push_daily(names: Vec<String>) {

    let mut counter: usize = 0;
    for n in names {        
        
        if let Some(file_date) = extract_date_from_filename(&n) {
            
            let today = Local::now().date_naive(); // Get current local date

            if file_date + chrono::Duration::days(1) == today {
      
                let source_path = format!("{}{}", db_config::SNAPSHOTS_FILE_PATH, n);
                let destination_path = format!("{}{}", db_config::DAILY_FOLDER_PATH, n);

                if let Err(e) = fs::copy(&source_path, &destination_path).await {
                    
                    log_simple_error(format!("Failed to copy {}: {}", n, e));
                
                } else {

                    log_simple_info(format!("Copied {} -> {}", n, destination_path));

                    counter += 1;
                    
                }

            }

        }

    }

    if counter == db_config::COLLECTION_NAMES.len() {


        if let Err(e) = fs::remove_dir_all(db_config::SNAPSHOTS_FILE_PATH).await {

            log_simple_warn(format!("Failed To Remove Old Images: {}.", e));
    
        }

        if let Err(e) = fs::create_dir_all(db_config::SNAPSHOTS_FILE_PATH).await {

            log_simple_warn(format!("Failed to Recreate Directory: {}.", e));
    
        }
    
    }


}

async fn periodic_collection_snapshot(name: String) -> Result<(), Box<dyn Error + Send + Sync>> {

    let mut check = true;

    set_daily_folder().await;

    loop {

        if db_config::LOAD_FROM_LAST_SNAPSHOTS && check { 
        
            check = false; 
            sleep(std::time::Duration::from_secs(db_config::SNAPSHOT_TIMER_SECONDS)).await;
       
        }

        if name == db_config::COLLECTION_NAMES[0] {
            
            let mut v_files = Vec::new();
            
            let mut entries = fs::read_dir(db_config::SNAPSHOTS_FILE_PATH).await?; // ✅ Await here
            
            while let Some(entry) = entries.next_entry().await? {
                if let Some(filename) = entry.file_name().to_str() {
                    v_files.push(filename.to_string());
                }
            }
            
            v_files.sort();

            let mut ans = Vec::new();

            for n in db_config::COLLECTION_NAMES {

                if let Some(last) =  v_files.clone().into_iter().filter(|x| x.contains(n) ).last() {

                    ans.push(last);

                }
            
            }

            push_daily(ans.clone()).await;
        
        }

        match get_database_state(&name).await {
            Ok(state) => {
                if let Err(e) = save_snapshot_to_file(&name, &state).await {
                    log_simple_error(format!("Error Saving Snapshot: {}.", e));
                }
            }
            Err(e) => {
                log_simple_error(format!("Error Getting Database State: {}.", e));
                return Err(Box::new(std::io::Error::new(
                    std::io::ErrorKind::Other,
                    "Failed to get database state",
                )));
            }
        }

        // Wait for X seconds before taking the next snapshot.
        sleep(std::time::Duration::from_secs(db_config::SNAPSHOT_TIMER_SECONDS)).await;
    }
}

pub async fn task_periodic_collection_snapshot(
    name: String,
) -> Result<(), Box<dyn Error + Send + Sync>> {
    let _task = task::spawn(async move {
        let _ = periodic_collection_snapshot(name).await;
    });

    Ok(())
}
