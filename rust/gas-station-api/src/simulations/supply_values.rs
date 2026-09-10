use crate::datastructures::fuel::Fuel;
use std::collections::HashMap;

/*
    DEFINE VALUES FOR STOCK OF FUEL IN SIMULATION.
*/

pub fn supply_of_fuel() -> HashMap<Fuel, u32> {
  HashMap::from([
    (Fuel::Gasoline70, 200_000_000), // 200_000 liters.
    (Fuel::Gasoline85, 100_000_000),
    (Fuel::Gasoline95, 50_000_000),
    (Fuel::Ethanol70, 250_000_000),
    (Fuel::Diesel50, 300_000_000),
    (Fuel::Diesel500, 500_000_000),
  ])
}
