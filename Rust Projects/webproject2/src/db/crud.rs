use mongodb::{bson, bson::{doc, Document, Bson}, options::FindOptions};
#[allow(unused_imports)] use crate::rocket::futures::TryStreamExt;
use futures::stream::StreamExt;
use rocket::serde::json::Json;
use std::sync::Arc;
use chrono::Utc;

use crate::{state, utils::make_trending::{document_to_trending, set_folder}};
use crate::logger::{log_simple_error, log_simple_info, log_simple_warn}; 
use crate::{models, models::user::User, models::trending::Trending};

// Converts a MongoDB Document into a User model
async fn document_to_user(doc: Document) -> Option<User> {
    match bson::from_bson(bson::Bson::Document(doc)) {
        Ok(user) => { log_simple_info(format!("Converted Document to User: {:p}.", &user)); Some(user) } ,
        Err(_) => { log_simple_error("Failed To Convert Document To User.".to_string()); None }
    }
}

// Create.

pub async fn db_create(
    user: Json<models::user::User>, 
    state: &rocket::State<Arc<state::AppState>>
) -> usize {
    
    let user_doc = doc! {
        "profile_image": &user.profile_image, // base 64 is sent from js directly.
        "profile_name": &user.profile_name,
        "profile_short_description": &user.profile_short_description,
        "donate_address": &user.donate_address,
        "social_media": bson::to_bson(&user.social_media).unwrap_or(Bson::Null),
        "long_description": &user.long_description,
        "categories": &user.categories.iter().map(|cat| format!("{:?}", cat)).collect::<Vec<String>>(),
        "tier": format!("{:?}", user.tier), // Serialize enum to string
    };

    match state.users_collection.insert_one(&user_doc, None).await {
        Ok(_) => {log_simple_info(format!("User Created Successfully: {:p}.", &user_doc )); 0},
        Err(e) => {log_simple_error(format!("Error Creating User: {}.", e)); 1}
    }

}

pub async fn create_donation(
    donation: Json<models::donation::Donation>, 
    state: &rocket::State<Arc<state::AppState>>
) -> usize {
    
    let donation_doc = doc! {
        "from": &donation.from,
        "to": &donation.to,
        "amount": &donation.amount,
        "date": Utc::now().timestamp(),
    };

    match state.donations_collection.insert_one(&donation_doc, None).await {
        Ok(_) => {log_simple_info(format!("Donation Recorded Successfully: {:p}!", &donation_doc)); 1},
        Err(e) => {log_simple_error(format!("Error Recording Donation: {}", e)); 0}
    }

}

// Read.

pub async fn get_user_by_donate_address(
    donate_address: String, 
    state: &rocket::State<Arc<state::AppState>>
) -> Option<Json<User>> {
    
    let filter: Document = doc! { "donate_address": &donate_address };

    match state.users_collection.find_one(filter, None).await {
        Ok(Some(doc)) => {
            // Attempt to deserialize the document into a User struct
            match bson::from_bson(bson::Bson::Document(doc)) {
                Ok(user) => { log_simple_info(format!("Found User: {:p}.", &user)); Some(Json(user)) },
                Err(e) => { log_simple_error(format!("Error Deserializing Document: {}.", e)); None }
            }
        },
        Ok(None) => { log_simple_warn(format!("No User Found For Donation Address: {:?}.", &donate_address)); None},
        Err(e) => { log_simple_error(format!("Error Getting User: {}.", e)); None }
    }
    
}

pub async fn get_users_search(
    query: &str, 
    category: bool, 
    page: u32, 
    limit: u32, 
    state: &rocket::State<Arc<state::AppState>>
) -> Json<Vec<User>> {
    
    let skip = (page - 1) * limit; // Calculate how many to skip

    // Building the pagination options
    let options = FindOptions::builder().limit(limit as i64).skip(skip as u64).build();

    let filter: Document;

    // If searching by category
    if category {
        filter = doc! { "categories": query };
    } else {
        // If searching by name
        filter = doc! { "profile_name": { "$regex": query, "$options": "i" } }; // "i" makes it case-insensitive
    }

    match state.users_collection.find(filter, options).await {
        Ok(mut cursor) => {
            let mut users = Vec::new();
            while let Some(doc) = cursor.next().await {
                if let Ok(d) = doc {
                    if let Some(user) = document_to_user(d).await {
                        users.push(user);
                    }
                }
            }
            log_simple_info(format!("Found Users at: {:p}.",&users));
            Json(users) // Return the list of users as JSON
        }
        Err(e) => {
            log_simple_error(format!("Error During Search: {}.", e));
            Json(vec![]) // Return an empty array in case of error
        }
    }
}

pub async fn get_all_users(
    page: u32, 
    limit: u32, 
    state: &rocket::State<Arc<state::AppState>>
) -> Json<Vec<User>> {
    
    let skip = (page - 1) * limit; // Calculate how many to skip

    // Pagination options
    let options = FindOptions::builder()
        .limit(limit as i64)
        .skip(skip as u64)
        .build();

    let filter = doc! {}; // Empty filter to get all users

    match state.users_collection.find(filter, options).await {
        Ok(mut cursor) => {
            let mut users = Vec::new();
            while let Some(doc) = cursor.next().await {
                if let Ok(d) = doc {
                    if let Some(user) = document_to_user(d).await {
                        users.push(user);
                    }
                }
            }
            log_simple_info(format!("Retrieved batch of Users at: {:p}.", &users));
            Json(users)
        }
        Err(e) => {
            log_simple_error(format!("Error fetching users: {}.", e));
            Json(vec![]) // Return an empty array on error
        }
    }
}


pub async fn get_trending_users(
    state: &rocket::State<Arc<state::AppState>>
) -> Json<Vec<Trending>> {
    use chrono::{Utc, Duration as ChronoDuration};

    let now = Utc::now();
    let last_24_hours_timestamp = (now - ChronoDuration::hours(24)).timestamp();
    
    log_simple_info(format!("Current Timestamp: {}.", now.timestamp()));
    log_simple_info(format!("24 Hours Ago Timestamp: {}.", &last_24_hours_timestamp));

    let pipeline = vec![
        doc! { "$match": { "date": { "$gte": last_24_hours_timestamp/1000 as i64 } } },
        doc! {
            "$group": {
                "_id": "$to", // Group by recipient (user's donation address)
                "total_donations": { "$sum": { "$toDouble": "$amount" } }, // Sum the donation amounts
                "donation_count": { "$sum": 1 } // Count the number of donations
            }
        },
        doc! { "$sort": { "total_donations": -1 } },
        doc! { "$limit": 15 }
    ];

    let mut cursor = match state.donations_collection.aggregate(pipeline, None).await {
        Ok(c) => c,
        Err(e) => { log_simple_error(format!("MongoDB Aggregation Error: {:?}", e)); return Json(vec![]); }
    };

    let mut trending_users: Vec<models::trending::Trending> = Vec::new();
    let mut c = 0;

    set_folder().await;

    while let Some(doc) = cursor.next().await {
        
        if let Ok(d) = doc {

            let id = match d.get_str("_id") {
                Ok(val) => val.to_string(),
                Err(_) => "".to_string(), // Handle error if the field is missing
            };
    
            let total_donations = match d.get_f64("total_donations") {
                Ok(val) => val.to_string(),
                Err(_) => "".to_string(), // Handle error if the field is missing
            };
    
            let donation_count = match d.get_i32("donation_count") {
                Ok(val) => (val as i64).to_string(),
                Err(_) => "".to_string()
                , // Handle error if the field is missing
            };
            
            let x = (id, total_donations, donation_count, c as u8);
            
            // Now pass a reference to the trending_user to your function
            if let Some(user) = document_to_trending(&x, state).await {
                log_simple_info(format!("Pushing User To Trends: {:p}.", &user));
                trending_users.push(user);
            }
        }
        
        c += 1;

    }

    log_simple_info(format!("Processed {} Trending Users.", c));

    Json(trending_users)
}

// Update.

pub async fn update_user(
    user_address: String, 
    updates: Json<Document>, 
    state: &rocket::State<Arc<state::AppState>>
) -> usize {
    
    let filter = doc! { "donate_address": &user_address };
    
    let update_doc = doc! { "$set": updates.into_inner() };

    match state.users_collection.update_one(filter, update_doc, None).await {
        Ok(result) if result.modified_count > 0 => { 
            log_simple_info(format!("User Updated Successfully: {}!", &user_address)); 0
        },
        Ok(_) => { log_simple_warn(format!("User not found or no changes made: {}.", &user_address)); 1}, 
        Err(e) =>{ log_simple_error(format!("Error Updating User: {}.", e)); 2 } 
    }
    
}

// Delete.

pub async fn delete_user(
    user_address: String, 
    state: &rocket::State<Arc<state::AppState>>
) -> usize {

    let filter = doc! { "donate_address": user_address };

    match state.users_collection.delete_one(filter, None).await {
        Ok(result) if result.deleted_count > 0 => {
           log_simple_info("User Deleted Successfully!".to_string()); 0
        },
        Ok(_) => { log_simple_warn("User Not Found.".to_string()); 1 }
        Err(e) => { log_simple_error(format!("Error Deleting User: {}.", e)); 2 }
    }

}