use crate::database_local_adapter::grid_patcher;
use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::grid::Grid;
use mongodb::Collection;
use mongodb::bson::doc;
use std::collections::HashMap;

async fn create_grid_with_name(db: &Collection<Grid>, grid_name: &str) -> Result<(), String> {
  let count = db
    .count_documents(doc! { "name": grid_name })
    .await
    .map_err(|e| e.to_string())?;

  if count > 0 {
    return Err("Grid already exists".to_string());
  }

  db.insert_one(Grid::new_with_name(grid_name))
    .await
    .map_err(|e| e.to_string())?;

  Ok(())
}

async fn create_price_controller(
  db: &Collection<Grid>,
  grid_name: &str,
  price_controller: HashMap<Fuel, u32>,
) -> Result<(), String> {
  grid_patcher::patch_many_price_controller(db, grid_name, price_controller).await
}

async fn create_supplier(
  db: &Collection<Grid>,
  grid_name: &str,
  supplier: HashMap<Fuel, u32>,
) -> Result<(), String> {
  grid_patcher::patch_many_supplier(db, grid_name, supplier).await
}

async fn create_isles(
  db: &Collection<Grid>,
  grid_name: &str,
  isles: Vec<Vec<GasPump>>,
) -> Result<(), String> {
  grid_patcher::patch_one_isles(db, grid_name, isles).await
}

pub async fn post_grid(db: &Collection<Grid>, grid: Grid) -> Result<(), String> {
  create_grid_with_name(db, grid.read_name()).await?;

  create_price_controller(db, grid.read_name(), grid.read_prices().clone()).await?;

  create_supplier(db, grid.read_name(), grid.get_supplier().clone()).await?;

  create_isles(db, grid.read_name(), grid.get_isles().to_vec()).await?;

  Ok(())
}
