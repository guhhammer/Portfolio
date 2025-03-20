use rand::Rng;
use chrono::{Utc, Duration};
use std::fs::File;
use std::io::Read;

use crate::logger::log_simple_info;
use crate::utils::writer_to_file::write_json_to_file;
use crate::{config::db_config, models::donation::Donation};

fn generate_random_timestamp() -> i64 {
    let now = Utc::now();
    let random_seconds = rand::thread_rng().gen_range(0..=86400); // 86400 seconds = 24 hours
    let random_time = now - Duration::seconds(random_seconds);
    random_time.timestamp()
}

fn update_json_with_random_timestamp(json_data: &mut Vec<Donation>) {
    for item in json_data.iter_mut() {
        item.date = generate_random_timestamp();
    }
}

fn read_json_from_file(file_path: &str) -> Result<Vec<Donation>, Box<dyn std::error::Error>> {
    let mut file = File::open(file_path)?;
    let mut contents = String::new();
    file.read_to_string(&mut contents)?;
    let items: Vec<Donation> = serde_json::from_str(&contents)?;
    Ok(items)
}

pub async fn date_update() -> Result<(), Box<dyn std::error::Error>> {
    
    let file_path = db_config::POPULATE_DB_DONATIONS_PATH;
    
    // Step 1: Read the existing JSON data from the file
    let mut items: Vec<Donation> = read_json_from_file(file_path)?;
    
    // Step 2: Update the date field with random timestamps
    update_json_with_random_timestamp(&mut items);
    
    // Step 3: Write the updated JSON data back to the file
    write_json_to_file(file_path, &items)?;
    
    log_simple_info(format!("Adjusted time to now ({}) and updated json data written to file.", Utc::now().timestamp()));

    Ok(())

}