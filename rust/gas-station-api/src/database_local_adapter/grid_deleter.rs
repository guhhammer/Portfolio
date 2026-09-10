use crate::database_local_adapter::grid_patcher;
use crate::datastructures::grid::Grid;
use chrono::Local;
use mongodb::Collection;
use mongodb::bson::doc;
use std::collections::HashMap;

async fn cloner(db: &Collection<Grid>, grid_name: &str) -> Result<(), String> {
  match db
    .find_one(doc! { "name": grid_name })
    .await
    .map_err(|e| e.to_string())?
  {
    Some(grid) => {
      let mut g = grid.clone();
      g.update_name(format!(
        "older_{}_{}",
        grid_name,
        Local::now().format("%Y%m%d-%H%M%S")
      ));
      db.insert_one(g).await.map_err(|e| e.to_string())?;

      Ok(())
    }
    None => Err("Grid not found".to_string()),
  }
}

async fn deleter(db: &Collection<Grid>, grid_name: &str, property: &str) -> Result<(), String> {
  cloner(db, grid_name).await?;

  match property {
        "supplier" | "isles" | "price_controller" => {
            let result = db
                .update_one(
                    doc! { "name": grid_name },
                    doc! { "$set": { property: doc! {} }},
                )
                .await
                .map_err(|e| e.to_string())?;

            if result.matched_count == 0 {
                return Err("Grid not found".to_string());
            }

            Ok(())
        }
        _ /* | "grid" */ => {
            db
            .delete_one(doc! { "name": grid_name })
            .await
            .map_err(|e| e.to_string())?;

            Ok(())
        }
    }
}

pub async fn delete_grid(db: &Collection<Grid>, grid_name: &str) -> Result<(), String> {
  deleter(db, grid_name, "grid").await
}

pub async fn delete_supplier(db: &Collection<Grid>, grid_name: &str) -> Result<(), String> {
  cloner(db, grid_name).await?;
  grid_patcher::patch_many_supplier(db, grid_name, HashMap::new()).await?;
  Ok(())
}

pub async fn delete_isles(db: &Collection<Grid>, grid_name: &str) -> Result<(), String> {
  cloner(db, grid_name).await?;
  grid_patcher::patch_one_isles(db, grid_name, vec![]).await?;
  Ok(())
}

pub async fn delete_price_controller(db: &Collection<Grid>, grid_name: &str) -> Result<(), String> {
  cloner(db, grid_name).await?;
  grid_patcher::patch_many_price_controller(db, grid_name, HashMap::new()).await?;
  Ok(())
}
