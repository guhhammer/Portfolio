use rocket::serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, Clone)]
pub enum Tier {
    // A
    Affiliate,
    
    // C
    Coach,
    Consultant,
    Creator,

    // D
    Donor,

    // E
    Entrepreneur,

    // I
    Influencer,
    Investor,
    
    // M
    Mentor,

    // N
    NonGovernmentalOrganization,
    
    // O
    Organization,
    
    // P
    Professional,
    
    // R
    Researcher,
    
    // S
    Student,
    Supporter,
    Sponsor,
    
    // T
    TeamMember,
    
    // U
    User,

    // V
    VerifiedUser,

}
