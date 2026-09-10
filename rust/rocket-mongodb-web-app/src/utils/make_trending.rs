use std::sync::Arc;
use std::fs;
use base64::{engine::general_purpose, Engine as _};

use crate::logger::{log_simple_error, log_simple_info, log_simple_warn};
use crate::{db::crud::get_user_by_donate_address, models::trending::Trending, state};
use crate::utils::{match_category, match_tier};
use crate::config::db_config;

pub async fn set_folder() -> () {

    if let Err(e) = fs::remove_dir_all(db_config::TRENDS_FOLDER_PATH) {

        log_simple_warn(format!("Failed To Remove Old Images: {}.", e));

    }

    if let Err(e) = fs::create_dir_all(db_config::TRENDS_FOLDER_PATH) {

        log_simple_warn(format!("Failed to Recreate Directory: {}.", e));

    }

} // called by get_trending_users; defined here for moduling.

pub async fn document_to_trending(doc: &(String, String, String, u8), state: &rocket::State<Arc<state::AppState>>) -> Option<Trending> {

    let user_data = get_user_by_donate_address(doc.0.clone(), state).await; // This function would fetch the user details based on the address

    let filename = format!("{}/trend{}.jpg", db_config::TRENDS_FOLDER_PATH, doc.3);

    match user_data {
        Some(user) => { 
            
            let mut pic = ""; // check if image is stored as "" or " ".to_string()

            match general_purpose::STANDARD.decode(&user.profile_image) {
                Ok(image_bytes) => {
                   
                    if fs::write(&filename, &image_bytes).is_ok() {
                
                        pic = &filename;

                        log_simple_info(format!("Saved Image ({:#?}) From User #{},", filename, &doc.0));
                   
                    } else {
                        
                        log_simple_error(format!("Error Saving Image ({:#?}) From User #{}.", filename, &doc.0));
                
                    }
                
                }
                Err(e) => log_simple_error(format!("Failed To Decode Base64 For {}: {}.", filename, e)),
            }

            log_simple_info(format!("Made Trending User #{}.", &doc.0));

            Some(Trending {
                profile_image: pic.to_string(),
                profile_name: user.profile_name.clone(),
                donate_address: user.donate_address.clone(),
                number_donations_24h: doc.2.clone(),
                amount_donated_24h: doc.1.clone(),
                categories: match_category::match_categories_to_string(&user.categories),
                tier: match_tier::to_str(&user.tier),
            })
        },
        None => { log_simple_warn(format!("No Data Found For User #{}.", &doc.0)); None },
    }

}
