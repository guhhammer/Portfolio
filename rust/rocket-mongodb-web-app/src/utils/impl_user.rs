use crate::models::{tier::Tier, user::User};

impl User {

    #[allow(dead_code)]
    pub fn new() -> Self {

        User { 
        
            profile_image: "".to_string(), 
        
            profile_name: "".to_string(), 
        
            profile_short_description: "".to_string(), 
        
            donate_address: "".to_string(), 
        
            social_media: Vec::new(), 
        
            long_description: "".to_string(), 
        
            categories: Vec::new(), 
        
            tier: Tier::User
        
        }

    }

}