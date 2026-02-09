use rocket::serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Trending {
   
    pub profile_image: String, // The profile image will be a base64 string
   
    pub profile_name: String,
   
    pub donate_address: String,
   
    pub number_donations_24h: String,

    pub amount_donated_24h: String,

    pub categories: String,

    pub tier: String,
 
}

pub struct SingletonTrending {

    pub counter: u32,

    pub starter: bool,
    
    pub vec_t: Vec<Trending>,
    
    pub last_updated_time: i64,

}