use crate::{
  database_local_adapter::connection::env_convert_to_boolean,
  database_local_adapter::grid_poster::post_grid,
  datastructures::{appstate::Grids, config::Config, grid::Grid},
  helpers::{builder::create_user, orchestrator},
};
use mongodb::{
  Collection, Database, IndexModel,
  bson::{Document, doc},
};
use std::env;
use std::sync::{Arc, Mutex};

/*

Collection of Users

#[derive(Debug, Serialize, Deserialize)]
pub struct User {
    username: String,                        INDEXED
    email: String,                           INDEXED
    password_hash: String,
    roles: Vec<String>,
    created_at: DateTime,
    last_login: DateTime,
}

*/
pub async fn make_appstate_users_db(
  local_db_ref: &Database,
) -> Result<Collection<Document>, mongodb::error::Error> {
  let users = local_db_ref.collection::<Document>("users");

  // unique index on username
  users
    .create_index(
      IndexModel::builder()
        .keys(doc! { "username": 1 })
        .options(Some(
          mongodb::options::IndexOptions::builder()
            .unique(true)
            .build(),
        ))
        .build(),
    )
    .await?;

  // unique index on email
  users
    .create_index(
      IndexModel::builder()
        .keys(doc! { "email": 1 })
        .options(Some(
          mongodb::options::IndexOptions::builder()
            .unique(true)
            .build(),
        ))
        .build(),
    )
    .await?;

  Ok(users)
}

/*

#[derive(Debug, Clone, serde::Serialize, serde::Deserialize)]
pub struct Grid {
    isles: Vec<Vec<GasPump>>,

    price_controller: HashMap<Fuel, u32>,

    supplier: HashMap<Fuel, u32>,

    name: String,
}

*/
pub async fn make_appstate_grids_db(
  local_db_ref: &Database,
) -> Result<Collection<Grid>, mongodb::error::Error> {
  let grids = local_db_ref.collection::<Grid>("grid");

  // unique index on grid name
  grids
    .create_index(
      IndexModel::builder()
        .keys(doc! { "name": 1 })
        .options(Some(
          mongodb::options::IndexOptions::builder()
            .unique(true)
            .build(),
        ))
        .build(),
    )
    .await?;

  Ok(grids)
}

pub async fn enable_simulations(
  grids: &mut Grids,
  g_config: &Config,
) -> Result<(), mongodb::error::Error> {
  if !env_convert_to_boolean("ENABLE_SIMULATION_ROUTES") {
    return Ok(());
  }

  let grid: Arc<Mutex<Grid>> = orchestrator::initialize(g_config); // START GRID FROM SCRIPT OR LOCAL .JSON.

  match grids
    .all_grids_db
    .delete_one(doc! {"name": &g_config.my_grid_name})
    .await
  {
    Ok(_) => log::info!("\n\tSimulation Grid dropped from db.\n"),
    Err(e) => log::error!("\n\tSimulation Grid not dropped from local db. REASON: {e}"),
  }

  let gclone = {
    let guard = grid.lock().unwrap();
    guard.clone()
  };

  match post_grid(&grids.all_grids_db, gclone).await {
    Ok(_) => {
      grids.add_grid(g_config.my_grid_name.clone());
      log::info!("\n\tSimulation Grid appended to local db...\n")
    }
    Err(e) => log::error!("\n\tSimulation Grid not appended to local db. REASON: {e}"),
  };

  orchestrator::trackers_and_procedures(&grid, g_config, true).await; // RUN ALL TRACKERS AND PROCEDURES.

  Ok(())
}

pub async fn grids_loader(
  local_db_ref: &Database,
  g_config: &Config,
) -> Result<Grids, mongodb::error::Error> {
  let all_grids: Collection<Grid> = make_appstate_grids_db(local_db_ref).await?;

  let users: Collection<Document> = make_appstate_users_db(local_db_ref).await?;

  let cn = env::var("COMPANY_NAME").expect("DATABASE_NAME must be set");

  let mut grids: Grids = Grids::new(cn, all_grids, users, Vec::new());

  register_local_manager(&grids).await?;

  log::info!("\n\tCompany Name: {}\n", &grids.company_name);

  enable_simulations(&mut grids, g_config).await?;

  Ok(grids)
}

async fn register_local_manager(state: &Grids) -> Result<(), mongodb::error::Error> {
  let username = env::var("LOCAL_MANAGER_USERNAME").expect("LOCAL_MANAGER_USERNAME must be set");
  let email = env::var("LOCAL_MANAGER_EMAIL").expect("LOCAL_MANAGER_EMAIL must be set");
  let plain_pw = env::var("LOCAL_MANAGER_PASSWORD").expect("LOCAL_MANAGER_PASSWORD must be set");

  let _ = create_user(
    &state.users_db,
    username,
    email,
    plain_pw,
    vec!["SpecialManager".to_string()],
  )
  .await;

  Ok(())
}
