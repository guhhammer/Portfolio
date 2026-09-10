use crate::datastructures::grid::Grid;
use crate::datastructures::message_buf::MessageBuffer;
use crate::simulations::price_values;
use std::sync::{Arc, Mutex};

pub fn make_price(
  grid: &mut Arc<Mutex<Grid>>,
  display: bool,
  run: bool,
  api_return: bool,
) -> Option<String> {
  if !run {
    log::warn!("make price run: false");
    return None;
  }

  grid
    .lock()
    .unwrap()
    .update_prices(price_values::price_initial_values());

  let mut m: MessageBuffer = MessageBuffer::new();

  grid.lock().unwrap().display_prices(&mut m, api_return);

  m.log("log/price-maker/", "price-maker");

  if display {
    m.print();
  }

  if api_return {
    return Some(m.output_as_string());
  }

  None
}
