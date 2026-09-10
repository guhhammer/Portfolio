use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::grid::Grid;
use mongodb::Collection;
use mongodb::bson::{doc, to_bson};
use serde::Serialize;
use std::collections::HashMap;

async fn patch_one_field<T: Serialize>(
  db: &Collection<Grid>,
  grid_name: &str,
  property: &str,
  new_value: T,
) -> Result<(), String> {
  let filter = doc! { "name": grid_name };

  let bson_value = to_bson(&new_value).map_err(|e| e.to_string())?;

  let update = doc! { "$set": { property: bson_value } };

  let result = db
    .update_one(filter, update)
    .await
    .map_err(|e| e.to_string())?;

  if result.matched_count == 0 {
    return Err("Grid not found".to_string());
  }

  Ok(())
}

async fn patch_many_fields(
  db: &Collection<Grid>,
  grid_name: &str,
  updates: HashMap<String, impl Serialize>,
) -> Result<(), String> {
  let filter = doc! { "name": grid_name };

  // Build the $set document dynamically
  let mut set_doc = doc! {};

  for (field, value) in updates {
    let bson_value = to_bson(&value).map_err(|e| e.to_string())?;
    set_doc.insert(field, bson_value);
  }

  let update = doc! { "$set": set_doc };

  let result = db
    .update_one(filter, update)
    .await
    .map_err(|e| e.to_string())?;

  if result.matched_count == 0 {
    return Err("Grid not found".to_string());
  }

  Ok(())
}

pub async fn patch_one_price_controller(
  db: &Collection<Grid>,
  grid_name: &str,
  fuel: Fuel,
  new_price: u32,
) -> Result<(), String> {
  patch_one_field(
    db,
    grid_name,
    format!("price_controller.{}", fuel.api_return_format()).as_str(),
    new_price,
  )
  .await
}

pub async fn patch_many_price_controller(
  db: &Collection<Grid>,
  grid_name: &str,
  price_controller_subset: HashMap<Fuel, u32>,
) -> Result<(), String> {
  if price_controller_subset.is_empty() {
    // Patch the whole supplier field to an empty object
    return patch_one_field(
      db,
      grid_name,
      "price_controller",
      mongodb::bson::Document::new(),
    )
    .await;
  }

  let updates = price_controller_subset
    .into_iter()
    .map(|(fuel, price)| {
      (
        format!("price_controller.{}", fuel.api_return_format()),
        price,
      )
    })
    .collect();
  patch_many_fields(db, grid_name, updates).await
}

pub async fn patch_name(
  db: &Collection<Grid>,
  grid_name: &str,
  new_name: &str,
) -> Result<(), String> {
  patch_one_field(db, grid_name, "name", new_name).await
}

pub async fn patch_one_supplier(
  db: &Collection<Grid>,
  grid_name: &str,
  fuel: Fuel,
  new_price: u32,
) -> Result<(), String> {
  patch_one_field(
    db,
    grid_name,
    format!("supplier.{}", fuel.api_return_format()).as_str(),
    new_price,
  )
  .await
}

pub async fn patch_many_supplier(
  db: &Collection<Grid>,
  grid_name: &str,
  supplier_subset: HashMap<Fuel, u32>,
) -> Result<(), String> {
  if supplier_subset.is_empty() {
    // Patch the whole supplier field to an empty object
    return patch_one_field(db, grid_name, "supplier", mongodb::bson::Document::new()).await;
  }

  let updates = supplier_subset
    .into_iter()
    .map(|(fuel, price)| (format!("supplier.{}", fuel.api_return_format()), price))
    .collect();
  patch_many_fields(db, grid_name, updates).await
}

pub async fn patch_one_isles(
  db: &Collection<Grid>,
  grid_name: &str,
  isles: Vec<Vec<GasPump>>,
) -> Result<(), String> {
  let value = if isles.is_empty() {
    // Correctly patch as empty sequence
    Vec::<Vec<GasPump>>::new()
  } else {
    isles
  };

  patch_one_field(db, grid_name, "isles", value).await
}
