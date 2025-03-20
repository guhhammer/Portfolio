use crate::models::category::Category;

#[allow(dead_code)]
pub fn from_str(category: &str) -> Option<Category> {
    
    match category {
    
        // A
        "AffordableHousing" => Some(Category::AffordableHousing),
        "AddictionRecovery" => Some(Category::AddictionRecovery),
        "AnimalRescue" => Some(Category::AnimalRescue),
        "AnimalRights" => Some(Category::AnimalRights),
        "AntiCorruption" => Some(Category::AntiCorruption),
        "AntiHumanTrafficking" => Some(Category::AntiHumanTrafficking),
        "ArtsEducation" => Some(Category::ArtsEducation),
        
        // B
        "BiodiversityProtection" => Some(Category::BiodiversityProtection),

        // C
        "CancerResearch" => Some(Category::CancerResearch),
        "CancerSupport" => Some(Category::CancerSupport),
        "ChildWelfare" => Some(Category::ChildWelfare),
        "CleanAir" => Some(Category::CleanAir),
        "CleanEnergy" => Some(Category::CleanEnergy),
        "CleanWater" => Some(Category::CleanWater),
        "ClimateChange" => Some(Category::ClimateChange),
        "CyberSecurityAwareness" => Some(Category::CyberSecurityAwareness),

        // D
        "DisasterPreparedness" => Some(Category::DisasterPreparedness),
        "DisasterRelief" => Some(Category::DisasterRelief),
        "DisabilitySupport" => Some(Category::DisabilitySupport),

        // E
        "EconomicEmpowerment" => Some(Category::EconomicEmpowerment),
        "Education" => Some(Category::Education),
        "ElderlyCare" => Some(Category::ElderlyCare),
        "EnvironmentalConservation" => Some(Category::EnvironmentalConservation),

        // F
        "FairTrade" => Some(Category::FairTrade),
        "FoodSecurity" => Some(Category::FoodSecurity),

        // G

        // H
        "HealthCare" => Some(Category::HealthCare),
        "Homelessness" => Some(Category::Homelessness),
        "HumanRights" => Some(Category::HumanRights),
        "Hunger" => Some(Category::Hunger),

        // I
        "InternationalAid" => Some(Category::InternationalAid),
        "IndigenousRights" => Some(Category::IndigenousRights),

        // J
        "JobCreation" => Some(Category::JobCreation),

        // K

        // L
        "LiteracyPrograms" => Some(Category::LiteracyPrograms),

        // M
        "MentalHealth" => Some(Category::MentalHealth),

        // N

        // O
        "OceanConservation" => Some(Category::OceanConservation),

        // P
        "Poverty" => Some(Category::Poverty),
        "PublicHealth" => Some(Category::PublicHealth),

        // Q

        // R
        "RacialEquality" => Some(Category::RacialEquality),
        "RenewableEnergy" => Some(Category::RenewableEnergy),
        "RefugeeAid" => Some(Category::RefugeeAid),

        // S
        "SocialJustice" => Some(Category::SocialJustice),

        // T

        // U

        // V
        "VeteransSupport" => Some(Category::VeteransSupport),

        // W
        "WaterSanitation" => Some(Category::WaterSanitation),
        "WildlifeProtection" => Some(Category::WildlifeProtection),
        "WomensHealth" => Some(Category::WomensHealth),
        "WomensRights" => Some(Category::WomensRights),

        // X

        // Y
        "YouthPrograms" => Some(Category::YouthPrograms),

        // Z

        _ => None, // If the string doesn't match any category
    
    }

}

#[allow(dead_code)]
pub fn to_str(category: &Category) -> String {
    match category {
        // A
        Category::AffordableHousing => "AffordableHousing".to_string(),
        Category::AddictionRecovery => "AddictionRecovery".to_string(),
        Category::AnimalRescue => "AnimalRescue".to_string(),
        Category::AnimalRights => "AnimalRights".to_string(),
        Category::AntiCorruption => "AntiCorruption".to_string(),
        Category::AntiHumanTrafficking => "AntiHumanTrafficking".to_string(),
        Category::ArtsEducation => "ArtsEducation".to_string(),

        // B
        Category::BiodiversityProtection => "BiodiversityProtection".to_string(),

        // C
        Category::CancerResearch => "CancerResearch".to_string(),
        Category::CancerSupport => "CancerSupport".to_string(),
        Category::ChildWelfare => "ChildWelfare".to_string(),
        Category::CleanAir => "CleanAir".to_string(),
        Category::CleanEnergy => "CleanEnergy".to_string(),
        Category::CleanWater => "CleanWater".to_string(),
        Category::ClimateChange => "ClimateChange".to_string(),
        Category::CyberSecurityAwareness => "CyberSecurityAwareness".to_string(),

        // D
        Category::DisasterPreparedness => "DisasterPreparedness".to_string(),
        Category::DisasterRelief => "DisasterRelief".to_string(),
        Category::DisabilitySupport => "DisabilitySupport".to_string(),

        // E
        Category::EconomicEmpowerment => "EconomicEmpowerment".to_string(),
        Category::Education => "Education".to_string(),
        Category::ElderlyCare => "ElderlyCare".to_string(),
        Category::EnvironmentalConservation => "EnvironmentalConservation".to_string(),

        // F
        Category::FairTrade => "FairTrade".to_string(),
        Category::FoodSecurity => "FoodSecurity".to_string(),

        // G
        // (Add any Category variants starting with G if needed)

        // H
        Category::HealthCare => "HealthCare".to_string(),
        Category::Homelessness => "Homelessness".to_string(),
        Category::HumanRights => "HumanRights".to_string(),
        Category::Hunger => "Hunger".to_string(),

        // I
        Category::InternationalAid => "InternationalAid".to_string(),
        Category::IndigenousRights => "IndigenousRights".to_string(),

        // J
        Category::JobCreation => "JobCreation".to_string(),

        // K
        // (Add any Category variants starting with K if needed)

        // L
        Category::LiteracyPrograms => "LiteracyPrograms".to_string(),

        // M
        Category::MentalHealth => "MentalHealth".to_string(),

        // N
        // (Add any Category variants starting with N if needed)

        // O
        Category::OceanConservation => "OceanConservation".to_string(),

        // P
        Category::Poverty => "Poverty".to_string(),
        Category::PublicHealth => "PublicHealth".to_string(),

        // Q
        // (Add any Category variants starting with Q if needed)

        // R
        Category::RacialEquality => "RacialEquality".to_string(),
        Category::RenewableEnergy => "RenewableEnergy".to_string(),
        Category::RefugeeAid => "RefugeeAid".to_string(),

        // S
        Category::SocialJustice => "SocialJustice".to_string(),

        // T
        // (Add any Category variants starting with T if needed)

        // U
        // (Add any Category variants starting with U if needed)

        // V
        Category::VeteransSupport => "VeteransSupport".to_string(),

        // W
        Category::WaterSanitation => "WaterSanitation".to_string(),
        Category::WildlifeProtection => "WildlifeProtection".to_string(),
        Category::WomensHealth => "WomensHealth".to_string(),
        Category::WomensRights => "WomensRights".to_string(),

        // X
        // (Add any Category variants starting with X if needed)

        // Y
        Category::YouthPrograms => "YouthPrograms".to_string(),

        // Z
        // (Add any Category variants starting with Z if needed)
    }
}

#[allow(dead_code)]
pub fn match_categories_to_string(vcat: &Vec<Category>) -> String {
   
    vcat.iter()
    .map(|c| to_str(c))
    .collect::<Vec<String>>() // Convert iterator to Vec<String>
    .join("&&") // Use 

}
