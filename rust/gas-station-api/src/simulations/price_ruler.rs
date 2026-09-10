/*

Min and Max values the price of each fuel flutuate into.

*/

pub const G70_MIN: u32 = 6000;
pub const G70_MAX: u32 = 7000;

pub const G85_MIN: u32 = 20;
pub const G85_MAX: u32 = 40;

pub const G95_MIN: u32 = 45;
pub const G95_MAX: u32 = 70;

pub const E70_MIN: u32 = 3800;
pub const E70_MAX: u32 = 4900;

pub const D50_MIN: u32 = 4900;
pub const D50_MAX: u32 = 5600;

pub const D500_MIN: u32 = 5100;
pub const D500_MAX: u32 = 5800;

/*
Fuel Prices:

    Gasoline70 = $6.10   \\ [6.00   7.00] // ~ 100
    Gasoline85 = $6.50   \\ [ +20 - +40 ]
    Gasoline95 = $6.87   \\ [ +45 - +70 ]

    Ethanol70 = $4.19    \\ [3.80 - 4.90] // ~ 110

    Diesel50 = $4.98     \\ [4.90 - 5.60] // ~ 70
    Diesel500 = $5.08    \\ [5.10 - 5.80] // ~ 70

*/
