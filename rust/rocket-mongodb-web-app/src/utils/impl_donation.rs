use crate::models::donation::Donation;

impl Donation {

    #[allow(dead_code)]
    pub fn new() -> Self {

        Donation { 
            
            from: "".to_string(), 
        
            to: "".to_string(), 
        
            amount: "".to_string(), 
            
            date: 0 
        
        }

    }

}