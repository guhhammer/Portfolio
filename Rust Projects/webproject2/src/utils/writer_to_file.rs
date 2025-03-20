use crate::models::donation::Donation;
use std::fs::OpenOptions;
use std::io::Write;

pub fn write_json_to_file(file_path: &str, json_data: &Vec<Donation>) -> Result<(), Box<dyn std::error::Error>> {
    let mut file = OpenOptions::new()
        .write(true)
        .truncate(true) // Clear the file before writing
        .open(file_path)?;
    let json_str = serde_json::to_string_pretty(json_data)?;
    file.write_all(json_str.as_bytes())?;
    Ok(())
}
