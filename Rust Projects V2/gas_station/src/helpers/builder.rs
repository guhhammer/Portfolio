use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::grid::Grid;
use crate::datastructures::user::User;
use crate::helpers::convert::user_to_doc;
use crate::helpers::grid_maker;
use crate::helpers::hasher::string_to_hash;
use crate::simulations;

use mongodb::Collection;
use mongodb::bson::{DateTime, Document, doc};
use mongodb::error::Error;
use std::collections::HashMap;

pub fn fixed_test_builder(name_id: &str) -> Grid {
  // name:
  let mut g: Grid = Grid::new_with_name(name_id);

  // supplier:
  let supply_of_fuels: HashMap<Fuel, u32> = simulations::supply_values::supply_of_fuel();
  g.add_supply(supply_of_fuels);

  // price_controller:
  let price_controller: Vec<(Fuel, u32)> = simulations::price_values::price_initial_values();
  g.update_prices(price_controller);

  // isles:
  let binding = grid_maker::grid_maker();
  let mut isles: Vec<Vec<GasPump>> = binding.get_isles().clone();
  g.add_isles(&mut isles);

  g
}

pub fn grid_builder(
  name: &str,
  supply_of_fuels: HashMap<Fuel, u32>,
  price_controller: Vec<(Fuel, u32)>,
  isles: Vec<Vec<GasPump>>,
) -> Grid {
  // name:
  let mut g: Grid = Grid::new_with_name(name);

  // supplier:
  g.add_supply(supply_of_fuels);

  // price_controller:
  g.update_prices(price_controller);

  // isles:
  let mut m_isles: Vec<Vec<GasPump>> = isles.clone();
  g.add_isles(&mut m_isles);

  g
}

pub async fn create_user(
  users: &Collection<Document>,
  username: String,
  email: String,
  password: String,
  roles: Vec<String>,
) -> Result<(), Error> {
  // 1. Check if username or email exists
  if check_user_exists(users, Some(&username), Some(&email)).await? {
    return Err(Error::from(std::io::Error::other(
      "Username or email is already in use",
    )));
  }

  // 2. Hash password
  let pwd = string_to_hash(password);

  // 3. Create user struct
  let user = User::new(
    username.to_string(),
    email.to_string(),
    pwd,
    roles,
    DateTime::now(),
    DateTime::now(),
  );

  // 4. Convert to Document and insert
  let doc = user_to_doc(&user);

  users.insert_one(doc).await?;
  Ok(())
}

pub async fn check_user_exists(
  users: &Collection<Document>,
  username: Option<&str>,
  email: Option<&str>,
) -> mongodb::error::Result<bool> {
  if let Some(u) = username
    && users.find_one(doc! { "username": u }).await?.is_some()
  {
    return Ok(true);
  }

  if let Some(e) = email
    && users.find_one(doc! { "email": e }).await?.is_some()
  {
    return Ok(true);
  }

  Ok(false)
}
