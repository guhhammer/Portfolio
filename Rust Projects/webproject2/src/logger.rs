use log::{trace, debug, info, warn, error};

use crate::config::db_config::*;

// Simple Usage:

#[allow(dead_code)]
pub fn log_simple_debug(d: String) { debug!("DEBUG: {:#?}", d); } // Used in dev debugging.

pub fn log_simple_error(e: String) { error!("ERROR: {:#?}", e); }

pub fn log_simple_info(i: String) { info!("INFO: {:#?}", i); }

#[allow(dead_code)]
pub fn log_simple_trace(t: String) { trace!("TRACE: {:#?}", t); } // Used in dev debugging.

pub fn log_simple_warn(w: String) { warn!("WARNING: {:#?}", w); }

// Database: // No need to make specific logs (just simple logs).
// Errors: // Rocket logs all the catcher requests. 
// Fairings:

pub fn log_fairing_running(f: &str) { info!("{:#?} Fairing Running...", f); }

// Guards: // No guards are made yet.

// Models: // No need for structs.
// Routes: // Rocket logs all the route requests.
// Utils: // No need to make specific logs (just simple logs).
// Config: 

pub fn log_config() {

    info!("Database Name: {}", DATABASE_NAME);
    info!("Database URI: {}", DATABASE_URI);
    info!("Database Start Empty: {}", DATABASE_START_EMPTY);
    info!("Load From Last Snapshots: {}", LOAD_FROM_LAST_SNAPSHOTS);
    info!("Populate DB Example: {}", POPULATE_DB_EXAMPLE);
    info!("Populate DB Users Path: {}", POPULATE_DB_USERS_PATH);
    info!("Populate DB Donations Path: {}", POPULATE_DB_DONATIONS_PATH);
    info!("Populate DB Trending Path: {}", POPULATE_DB_TRENDING_PATH);
    info!("Collection Names: {:#?}", COLLECTION_NAMES);
    info!("Collection Paths: {:#?}", COLLECTION_PATHS);
    info!("Snapshots File Path: {}", SNAPSHOTS_FILE_PATH);
    info!("Snapshot Timer Seconds: {}", SNAPSHOT_TIMER_SECONDS);
    info!("Trends Folder Path: {}", TRENDS_FOLDER_PATH);
    info!("Trend Refresh Timer: {}", TREND_REFRESH_TIMER);

}