#[macro_use] extern crate rocket;

mod config;
mod db;
mod error;
mod fairings;
mod logger;
mod models;
mod routes;
mod state;
mod utils;

use std::sync::Arc;
use logger::log_config;
use std::fs;

use mongodb::{bson::Document, Collection, Database};
use fern::colors::{Color, ColoredLevelConfig};

async fn init_collections(conn: &Database, names: &Vec<&str>) -> Vec<Collection<Document>> {
    
    let mut ret: Vec<Collection<Document>> = Vec::new();

    for n in names { ret.push( conn.collection::<Document>(n) ); }

    ret

}

fn setup_logger() -> Result<(), fern::InitError> {

    fs::create_dir_all("logs").unwrap();

    let colors = ColoredLevelConfig::new()
    .trace(Color::Cyan)
    .debug(Color::Blue)
    .info(Color::Green)
    .warn(Color::Yellow)
    .error(Color::Red);

    fern::Dispatch::new()
        .format(move |out, message, record| {
            out.finish(format_args!(
                "[{} {}] {}",
                chrono::Local::now().format("%H:%M:%S"),
                colors.color(record.level()),
                message
            ))
        })
        .level(log::LevelFilter::Info)
        .chain(std::io::stdout()) // Log to terminal
        .chain(fern::log_file("logs/app.log")?) // Log to file
        .apply()
        .unwrap();

    Ok(())
    
}

#[rocket::main]
async fn main() -> Result<(), rocket::Error> {

    setup_logger().expect("Failed to initialize logger");

    log_config(); 

    let appdb: Database =  db::connection::connect().await.expect("Failed to connect to the database.");

    let names: &Vec<&str> =  &crate::config::db_config::COLLECTION_NAMES.to_vec();

    let collections: Vec<Collection<Document>> = init_collections(&appdb, &names).await;

    let state: Arc<state::AppState> = Arc::new(state::AppState {

        users_collection: appdb.collection::<Document>(&names[0]),
        
        donations_collection: appdb.collection::<Document>(&names[1]),

    }); 
    
    let _rocket: rocket::Rocket<rocket::Ignite> = rocket::build()
    
    .mount("/static", rocket::fs::FileServer::from("static")) // Serve CSS/JS

    .attach(fairings::donation_redefiner::run())
    .attach(fairings::populater::run(collections.clone()))
    .attach(fairings::snapshots_loader::run(appdb.clone()))
    .attach(fairings::set_snapshot_tasks::run())
    
    .attach(rocket_dyn_templates::Template::fairing())    
    
    .manage(state)

    .mount("/", routes::mount_routes())
    
    .register("/", error::catchers())
    
    .launch()
    .await?;

    Ok(())

}
