use mongodb::{Database, bson::Document};
use std::{fs, fs::File, io::Read};
use chrono::NaiveDateTime;
use regex::Regex;
use serde_json;

use crate::{config::db_config, logger::{log_simple_error, log_simple_info, log_simple_warn}};

fn get_latest_snapshot(prefix: &str, directory: &str) -> Option<String> {
    let re = Regex::new(&format!(r"^snapshot_{}_(\d{{8}}_\d{{6}})\.json$", regex::escape(prefix))).unwrap();

    let mut snapshots: Vec<(String, NaiveDateTime)> = fs::read_dir(directory).ok()?
        .filter_map(|entry| {
            let entry = entry.ok()?;
            let filename = entry.file_name().into_string().ok()?;

            if let Some(caps) = re.captures(&filename) {
                let timestamp_str = &caps[1];
                if let Ok(timestamp) = NaiveDateTime::parse_from_str(timestamp_str, "%Y%m%d_%H%M%S") {
                    return Some((filename, timestamp));
                }
            }
            None
        })
        .collect();

    snapshots.sort_by(|a, b| b.1.cmp(&a.1)); // Sort descending (newest first)

    snapshots.first().map(|(filename, _)| format!("{}/{}", directory, filename))
}

async fn loader(db: &Database, collection_name: &str, snapshot_path: String) -> Result<(), Box<dyn std::error::Error>>  {

    let mut file = File::open(snapshot_path)?;
    let mut data = Vec::new();

    file.read_to_end(&mut data)?;
    let documents: Vec<Document> = serde_json::from_slice(&data)?;

    // Get the collection and insert the documents
    let collection = db.collection::<Document>(collection_name);

    // Skip insertion if there are no valid documents
    if documents.is_empty() {
        log_simple_warn(format!("Documents Found In Latest Snapshot From Collection ({}) Are Empty.", collection_name));
        return Ok(());
    }

    // Insert the documents into the collection
    collection.insert_many(documents, None).await?;

    log_simple_info(format!("Loaded Documents Into Collection {}.", collection_name));
    
    Ok(()) 

}

pub async fn load_collection_snapshot(db: &Database, name: &str) -> u8 {

    let snapshot_path = get_latest_snapshot(name, db_config::SNAPSHOTS_FILE_PATH);

    match snapshot_path {
    
        Some(path) => {

            log_simple_info(format!("Latest Snapshot Found At: {}.", path));

            if let Err(e) = loader(db, name, path).await {
                log_simple_error(format!("Error Loading Snapshot: {}.", e));
                return 1;  // Return false if there was an error loading the snapshot
            }

            return 0;  // Return true if the snapshot was successfully loaded
        }
        None => {
        
            // No snapshot found for this collection
            log_simple_warn(format!("No Snapshot Found For This Collection: {}.", name));
            return 2;  // Return false if no snapshot was found
        
        }
    
    }

}