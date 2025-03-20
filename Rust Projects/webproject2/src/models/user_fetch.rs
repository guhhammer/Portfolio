use rocket::serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct UserFetch {
   
    pub profile_image: String, // The profile image will be a base64 string
   
    pub profile_name: String,
   
    pub profile_short_description: String,
   
    pub donate_address: String,
   
    pub social_media: String,
   
    pub long_description: String,

    pub categories: String,

    pub tier: String,

}

