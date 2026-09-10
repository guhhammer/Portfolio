use mongodb::{Client, Database};
use std::env;

pub async fn connect() -> Result<Database, mongodb::error::Error> {
  let mongo_uri = env::var("MONGO_URI").expect("MONGO_URI must be set");
  let mongo_db_name = env::var("DATABASE_NAME").expect("DATABASE_NAME must be set");

  let client = Client::with_uri_str(mongo_uri).await?;

  let db = client.database(&mongo_db_name);

  if env_convert_to_boolean("DATABASE_START_EMPTY") {
    db.drop().await?;

    log::info!("Database '{mongo_db_name}' Dropped Successfully.");
  } else {
    log::info!("Using Existing Database '{mongo_db_name}'.");
  }

  Ok(db)
}

pub fn env_convert_to_boolean(env_var_name: &str) -> bool {
  matches!(
    env::var(env_var_name)
      .unwrap_or("false".to_string())
      .to_lowercase()
      .as_str(),
    "1" | "true" | "yes" | "ok"
  )
}
