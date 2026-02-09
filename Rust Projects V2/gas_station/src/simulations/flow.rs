pub const SIZE_TANK_CAR: (u32, u32) = (10_000, 70_000); // min. and max. tank fuel for car.
pub const SIZE_TANK_BIKE: (u32, u32) = (5_000, 20_000); // min. and max. tank fuel for bike.
pub const SIZE_TANK_TRUCK: (u32, u32) = (30_000, 250_000); // min. and max. tank fuel for truck.

pub const DIESEL50_INCOME: (usize, f64) = (400, 8.0); // (CAR AMOUNT, PERCENT TO SPLIT)
// pub const DIESEL500_INCOME: (usize, f64) = (600, 8.0); // Values are copie from DIESEL50.
pub const ETHANOL70_INCOME: (usize, f64) = (600, 8.0);
pub const GASOLINE70_INCOME: (usize, f64) = (1000, 40.0);
pub const GASOLINE85_INCOME: (usize, f64) = (400, 8.0); // percent won't affect the calculation.
pub const GASOLINE95_INCOME: (usize, f64) = (150, 8.0); // percent won't affect the calculation.

pub const IDLE_TIME: usize = 30; // max milliseconds after fuelling.
