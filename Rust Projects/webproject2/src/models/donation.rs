use rocket::serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Donation {

    pub from: String,
    pub to: String,
    pub amount: String, 
    pub date: i64, // Store the date of the donation in unix timestamp seconds.

} 
