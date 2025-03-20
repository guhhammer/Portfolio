use rocket::serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize, Clone)]
pub enum Category {
    // A
    AffordableHousing,
    AddictionRecovery,
    AnimalRescue,
    AnimalRights, 
    AntiCorruption,
    AntiHumanTrafficking,
    // A (continued)
    ArtsEducation,

    // B
    BiodiversityProtection,

    // C
    CancerResearch,
    CancerSupport,
    ChildWelfare,
    CleanAir,
    CleanEnergy,
    CleanWater,
    ClimateChange,
    CyberSecurityAwareness,

    // D
    DisasterPreparedness,
    DisasterRelief,
    DisabilitySupport,

    // E
    EconomicEmpowerment,
    Education,
    ElderlyCare,
    EnvironmentalConservation,

    // F
    FairTrade,
    FoodSecurity,

    // H
    HealthCare,
    Homelessness,
    // H (continued)
    HumanRights,
    Hunger,

    // I
    InternationalAid,
    IndigenousRights,

    // J
    JobCreation,

    // K

    // L
    LiteracyPrograms,

    // M
    MentalHealth,

    // N

    // O
    OceanConservation,

    // P
    Poverty,
    PublicHealth,

    // Q

    // R
    RacialEquality,
    RenewableEnergy,
    RefugeeAid,

    // S
    SocialJustice,

    // T

    // U

    // V
    VeteransSupport,

    // W
    WaterSanitation,
    WildlifeProtection,
    // W (continued)
    WomensHealth,
    WomensRights,

    // X

    // Y
    YouthPrograms,

    // Z 

}
