use crate::datastructures::grid::Grid;
use mongodb::Collection;
use mongodb::bson::Document;
use std::sync::RwLock;

#[derive(Debug)]
pub struct Grids {
  pub company_name: String,
  pub all_grids_db: Collection<Grid>,
  pub users_db: Collection<Document>,
  pub grid_name_ids: RwLock<Vec<String>>,
}

impl Grids {
  pub fn new(
    company_name: String,
    all_grids_db: Collection<Grid>,
    users_db: Collection<Document>,
    grid_name_ids: Vec<String>,
  ) -> Self {
    Self {
      company_name,
      all_grids_db,
      users_db,
      grid_name_ids: grid_name_ids.into(),
    }
  }

  pub fn add_grid(&mut self, grid_name_id: String) {
    {
      let mut ids = self.grid_name_ids.write().unwrap();
      ids.push(grid_name_id);
    }
  }
}
