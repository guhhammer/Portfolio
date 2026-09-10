use mongodb::{Client, Database};

use crate::{config::db_config, logger::log_simple_info};

pub async fn connect() -> Result<Database, mongodb::error::Error> {
    
    let client = Client::with_uri_str(db_config::get_db_uri()).await?;

    let db = client.database(&db_config::get_db_name());

    if db_config::DATABASE_START_EMPTY {
    
        db.drop(None).await?;

        log_simple_info(format!("Database '{}' Dropped Successfully.", db_config::get_db_name()));
    
    } else { log_simple_info(format!("Using Existing Database '{}'.", db_config::get_db_name())); }

    Ok(db)
    
}

