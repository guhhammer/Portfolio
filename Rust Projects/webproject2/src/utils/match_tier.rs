use crate::models::tier::Tier;

#[allow(dead_code)]
pub fn from_str(tier: &str) -> Option<Tier> {

    match tier {
    
        // A
        "Affiliate" => Some(Tier::Affiliate),

        // B

        // C
        "Coach" => Some(Tier::Coach),
        "Consultant" => Some(Tier::Consultant),
        "Creator" => Some(Tier::Creator),

        // D
        "Donor" => Some(Tier::Donor),

        // E
        "Entrepreneur" => Some(Tier::Entrepreneur),

        // I
        "Influencer" => Some(Tier::Influencer),
        "Investor" => Some(Tier::Investor),

        // F G H I J K L 

        // M
        "Mentor" => Some(Tier::Mentor),

        // N
        "NonGovernmentalOrganization" => Some(Tier::NonGovernmentalOrganization),

        // O
        "Organization" => Some(Tier::Organization),

        // P
        "Professional" => Some(Tier::Professional),

        // Q

        // R
        "Researcher" => Some(Tier::Researcher),

        // S
        "Student" => Some(Tier::Student),
        "Supporter" => Some(Tier::Supporter),
        "Sponsor" => Some(Tier::Sponsor),

        // T
        "TeamMember" => Some(Tier::TeamMember),

        // U
        "User" => Some(Tier::User),

        // V
        "VerifiedUser" => Some(Tier::VerifiedUser),

        // W X Y Z

        // Default case if the tier is not recognized
        _ => None,

    }

}

#[allow(dead_code)]
pub fn to_str(tier: &Tier) -> String {
    match tier {
        // A
        Tier::Affiliate => "Affiliate".to_string(),

        // B
        // (Add any Tier variants starting with B if needed)

        // C
        Tier::Coach => "Coach".to_string(),
        Tier::Consultant => "Consultant".to_string(),
        Tier::Creator => "Creator".to_string(),

        // D
        Tier::Donor => "Donor".to_string(),

        // E
        Tier::Entrepreneur => "Entrepreneur".to_string(),

        // I
        Tier::Influencer => "Influencer".to_string(),
        Tier::Investor => "Investor".to_string(),

        // F G H I J K L
        // (Add any Tier variants starting with these letters if needed)

        // M
        Tier::Mentor => "Mentor".to_string(),

        // N
        Tier::NonGovernmentalOrganization => "NonGovernmentalOrganization".to_string(),

        // O
        Tier::Organization => "Organization".to_string(),

        // P
        Tier::Professional => "Professional".to_string(),

        // Q
        // (Add any Tier variants starting with Q if needed)

        // R
        Tier::Researcher => "Researcher".to_string(),

        // S
        Tier::Student => "Student".to_string(),
        Tier::Supporter => "Supporter".to_string(),
        Tier::Sponsor => "Sponsor".to_string(),

        // T
        Tier::TeamMember => "TeamMember".to_string(),

        // U
        Tier::User => "User".to_string(),

        // V
        Tier::VerifiedUser => "VerifiedUser".to_string(),

        // W X Y Z
        // (Add any Tier variants starting with these letters if needed)
    }
}
