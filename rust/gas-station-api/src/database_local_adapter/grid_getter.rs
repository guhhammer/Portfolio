use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::grid::Grid;
use futures::TryStreamExt;
use mongodb::Collection;
use mongodb::bson::doc;
use std::collections::HashMap;

async fn get_grid_field<T, F>(
  db: &Collection<Grid>,
  grid_name: &str,
  extractor: F,
) -> Result<T, String>
where
  T: Clone,
  F: Fn(&Grid) -> &T,
{
  match db
    .find(doc! { "name": grid_name })
    .await
    .map_err(|e| e.to_string())?
    .try_next()
    .await
    .map_err(|e| e.to_string())?
  {
    Some(doc) => Ok(extractor(&doc).clone()),
    _ => Err("Grid not found".to_string()),
  }
}

pub async fn get_grid_supplier(
  db: &Collection<Grid>,
  grid_name: &str,
) -> Result<HashMap<Fuel, u32>, String> {
  get_grid_field(db, grid_name, |g| g.get_supplier()).await
}

pub async fn get_grid_price_controller(
  db: &Collection<Grid>,
  grid_name: &str,
) -> Result<HashMap<Fuel, u32>, String> {
  get_grid_field(db, grid_name, |g| g.read_prices()).await
}

pub async fn get_grid_isles(
  db: &Collection<Grid>,
  grid_name: &str,
) -> Result<Vec<Vec<GasPump>>, String> {
  get_grid_field(db, grid_name, |g| g.get_isles()).await
}
