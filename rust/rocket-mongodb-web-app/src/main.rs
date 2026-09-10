//! Creator-donation platform: a server-rendered Rocket web app backed by
//! MongoDB, with Tera templates for the interface.

#[macro_use]
extern crate rocket;

mod config;
mod db;
mod error;
mod fairings;
mod logger;
mod models;
mod routes;
mod state;
mod utils;

use logger::log_config;
use std::fs;
use std::sync::Arc;

use fern::colors::{Color, ColoredLevelConfig};
use mongodb::{Collection, Database, bson::Document};

async fn init_collections(conn: &Database, names: &[&str]) -> Vec<Collection<Document>> {
    names
        .iter()
        .map(|n| conn.collection::<Document>(n))
        .collect()
}

fn setup_logger() -> Result<(), fern::InitError> {
    fs::create_dir_all("logs").expect("Failed to create logs directory");

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
        .chain(std::io::stdout())
        .chain(fern::log_file("logs/app.log")?)
        .apply()
        .expect("Failed to apply logger configuration");

    Ok(())
}

#[rocket::main]
async fn main() -> Result<(), Box<rocket::Error>> {
    setup_logger().expect("Failed to initialize logger");
    log_config();

    let appdb: Database = db::connection::connect()
        .await
        .expect("Failed to connect to the database.");

    let names: &[&str] = &crate::config::db_config::COLLECTION_NAMES;

    let collections: Vec<Collection<Document>> = init_collections(&appdb, names).await;

    let state = Arc::new(state::AppState {
        users_collection: appdb.collection::<Document>(names[0]),
        donations_collection: appdb.collection::<Document>(names[1]),
    });

    let _rocket = rocket::build()
        .mount("/static", rocket::fs::FileServer::from("static"))
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
