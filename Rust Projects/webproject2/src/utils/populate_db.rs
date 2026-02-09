use crate::{config::db_config, logger::{log_simple_error, log_simple_info}, models::{donation::Donation, trending::Trending, user::User}};
use mongodb::{Collection, bson::{self, Document}};
use std::{error::Error, fs};
use serde::de::DeserializeOwned;
use serde_json;

async fn inner_populate<T>(x: &Collection<Document>, path: &str) -> Result<(), Box<dyn Error>>
where
    T: DeserializeOwned + serde::Serialize, 
{
    let json_data = fs::read_to_string(path)?;      // takes json file.
    let vec_t: Vec<T> = serde_json::from_str(&json_data)?;  // select struct.

    let t_doc: Vec<Document> = vec_t
        .into_iter()
        .filter_map(|d| bson::to_document(&d).ok()) // Avoids panic on serialization failure
        .collect(); // binds struct and json data in bson docs.

    if !t_doc.is_empty() {
        x.insert_many(t_doc, None).await?;
    }

    Ok(())
}

pub async fn populate(x: &Vec<Collection<Document>>) -> Result<(), Box<dyn Error>> {
    if db_config::POPULATE_DB_EXAMPLE {
        for i in 0..db_config::COLLECTION_NAMES.len() {
            let collection_name = db_config::COLLECTION_NAMES[i];
            let file_path = db_config::COLLECTION_PATHS[i];

            log_simple_info(format!("Inserting Into Collection {}.", collection_name));
        
            // Dynamically choose the type based on collection name
            match collection_name {
                "users" => inner_populate::<User>(&x[i], file_path).await?,
                "donations" => inner_populate::<Donation>(&x[i], file_path).await?,
                "trending" => inner_populate::<Trending>(&x[i], file_path).await?,
                _ => {
                    log_simple_error(format!("Unknown Collection: {}.", collection_name));
                    continue;
                }
            }
        }
    }
    Ok(())
}