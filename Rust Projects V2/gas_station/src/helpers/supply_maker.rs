use crate::datastructures::grid::Grid;
use crate::datastructures::message_buf::MessageBuffer;
use crate::simulations::supply_values;
use std::sync::{Arc, Mutex};

pub fn make_supply(grid: &mut Arc<Mutex<Grid>>, display: bool, run: bool, api_return: bool) {
  if !run {
    return;
  }

  grid
    .lock()
    .unwrap()
    .add_supply(supply_values::supply_of_fuel());

  let mut m: MessageBuffer = MessageBuffer::new();

  grid.lock().unwrap().display_supply(&mut m, api_return);

  m.log("log/fuel-supply/", "fuel-supply");

  if display {
    m.print();
  }
}
