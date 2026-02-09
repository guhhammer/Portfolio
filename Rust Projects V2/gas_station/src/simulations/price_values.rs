use crate::datastructures::fuel::Fuel;

/*
    DEFINE THE INITIAL PRICE FOR EACH FUEL.
*/

pub fn price_initial_values() -> Vec<(Fuel, u32)> {
  vec![
    (Fuel::Gasoline70, 6100), // PRICE IN THOUSANDTHS 6100 -> $6.10
    (Fuel::Gasoline85, 6496),
    (Fuel::Gasoline95, 6870),
    (Fuel::Ethanol70, 4190),
    (Fuel::Diesel50, 4980),
    (Fuel::Diesel500, 5080),
  ]
}
