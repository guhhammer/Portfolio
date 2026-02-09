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

static EXPIRATION_TIME: Lazy<u64> = Lazy::new(|| {
  let num: u64 = env::var("EXPIRATION_TIME")
    .unwrap_or_else(|_| "3600".to_string()) // default to 42
    .parse()
    .expect("Failed to parse EXPIRATION_TIME");

  num
});

///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
//
// //      //////  //////  //////   //      //////  //////  //////  //  //  //////  //////
// //      //  //  //      //  //   //      //      //      //  //  //  //  //      //  //
// //      //  //  //      //////   //      //////  //////  ////    //  //  //////  ////
// //      //  //  //      //  //   //          //  //      ////    ////    //      ////
// //////  //////  //////  //  //   //////  //////  //////  //  //  //      //////  //  //
//
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
#[rocket::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
  // Server Configurations:
  dotenv::dotenv().ok();

  setup_logger::set().expect("Failed to initialize logger");

  let g_config: Config = load_config();

  // Server Local Database configurations:
  let local_mongodb: Database = connect().await.expect("Failed to connect to the database.");

  // Server appstate configuration:
  let grids: Grids = main_config::grids_loader(&local_mongodb, &g_config).await?;

  // Server Run:
  let _rocket = rocket::build() // START SERVER. [Needs to be https for JWT to work properly.]
    // .mount("/static", rocket::fs::FileServer::from("static")) // Serve CSS/JS // IF static files, do:
    .attach(fairings::run())
    .manage(grids) // APPSTATE.
    .mount("/api/", routes::mount_routes())
    .mount("/simulation/", routes::mount_simulation_routes())
    .mount("/test/", routes::mount_test_routes()) // REMOVE THIS FROM TARGET BUILD.
    .launch()
    .await?;

  Ok(())
}

// Check structure.memo to see/check features.
/*

{ ABOUT LOCALSERVER:
    create route for sign up | create route for specialmanager to change user roles.
}

{ ABOUT GLOBAL SERVER:
    - MAYBE USE IT TO PROVIDE WEB PAGES TOO.
    - USE IT TO HOLD COPIES OF DBS AND SIGNATURES OF GASSTATIONS, ALLTOGETHER WITH ENABLING FEATURES.

    - EVERYTIME THE LOCALSERVER FALLS, IT LOSES THE GRID_NAME_IDS LIST, NEEDS TO ASK THIS
      TO THE GLOBAL SERVER IN THE FUTURE AT ROCKET LAUNCH. SO IT CAN BE AWARE OF COMPANY STATES.

}

{ ABOUT INTERFACE:
    - NEED TO MAKE THE INTERFACE.
}

*/
