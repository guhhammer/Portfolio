use mongodb::Collection;
use mongodb::bson::Document;
use serde::{Deserialize, Serialize};

use crate::models::{donation::Donation, trending::Trending, user::User, user_fetch::UserFetch};

// Rocket state to hold the MongoDB collection
pub struct AppState {
 
    pub users_collection: Collection<Document>,

    pub donations_collection: Collection<Document>,

}

#[derive(Debug, Serialize, Deserialize)]
#[serde(tag = "type")] // Adds a "type" field to JSON to differentiate structs
pub enum DbEntry {
    Donation(Donation),
    Trending(Trending),
    UserFetch(UserFetch),
    User(User),
}

