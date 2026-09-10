//! Local gas-station server.
//!
//! A Rocket REST API that manages a station's pump grid: authentication
//! (JWT + Argon2), price updates, fuel-flow simulation, and persistence in a
//! local MongoDB instance. See structure.memo for the feature checklist.

mod database_local_adapter;
mod datastructures;
mod fairings;
mod guards;
mod helpers;
mod routes;
mod simulations;

#[macro_use]
extern crate rocket;
use crate::{
  database_local_adapter::connection::connect,
  datastructures::{
    appstate::Grids,
    config::{Config, load_config},
  },
  helpers::{main_config, setup_logger},
};
use mongodb::Database;
use once_cell::sync::Lazy;
use std::env;

static SIMULATION_NAME_ID: Lazy<String> = Lazy::new(|| load_config().my_grid_name.clone());

/// JWT expiration time in seconds, configurable via the environment.
static EXPIRATION_TIME: Lazy<u64> = Lazy::new(|| {
  env::var("EXPIRATION_TIME")
    .unwrap_or_else(|_| "3600".to_string())
    .parse()
    .expect("Failed to parse EXPIRATION_TIME")
});

/// Secret used to sign and verify JWTs. Always set JWT_SECRET outside of
/// local development.
static JWT_SECRET: Lazy<String> =
  Lazy::new(|| env::var("JWT_SECRET").unwrap_or_else(|_| "dev-only-insecure-secret".to_string()));

#[rocket::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
  dotenv::dotenv().ok();

  setup_logger::set().expect("Failed to initialize logger");

  let g_config: Config = load_config();

  let local_mongodb: Database = connect().await.expect("Failed to connect to the database.");

  let grids: Grids = main_config::grids_loader(&local_mongodb, &g_config).await?;

  // Needs to run behind HTTPS for the JWT cookies to be safe in production.
  let _rocket = rocket::build()
    .attach(fairings::run())
    .manage(grids)
    .mount("/api/", routes::mount_routes())
    .mount("/simulation/", routes::mount_simulation_routes())
    .mount("/test/", routes::mount_test_routes()) // dev only: excluded from release builds by ops
    .launch()
    .await?;

  Ok(())
}
