use mongodb::{Client, Database};

use crate::{config::db_config, logger::log_simple_info};

pub async fn connect() -> Result<Database, mongodb::error::Error> {
    
    let client = Client::with_uri_str(db_config::DATABASE_URI).await?;

    let db = client.database(db_config::DATABASE_NAME);

    if db_config::DATABASE_START_EMPTY {
    
        db.drop(None).await?;

        log_simple_info(format!("Database '{}' Dropped Successfully.", db_config::DATABASE_NAME));
    
    } else { log_simple_info(format!("Using Existing Database '{}'.", db_config::DATABASE_NAME)); }

    Ok(db)
    
}

