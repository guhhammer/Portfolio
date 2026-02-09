pub fn log_config() {
  // --- Server configs ---
  log::info!(
    "Server Address: {}",
    std::env::var("SERVER_ADDRESS").unwrap_or_else(|_| "127.0.0.1".to_string())
  );
  log::info!(
    "Port: {}",
    std::env::var("PORT").unwrap_or_else(|_| "8080".to_string())
  );

  // --- Orchestrator configs ---
  log::info!(
    "Load Grid Locally: {}",
    std::env::var("LOAD_GRID_LOCALLY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Grid Filepath: {}",
    std::env::var("GRID_FILEPATH").unwrap_or_else(|_| "src/etc/grid.json".to_string())
  );
  log::info!(
    "My Grid Name: {}",
    std::env::var("MY_GRID_NAME").unwrap_or_else(|_| "Gassy".to_string())
  );

  log::info!(
    "Make Supply Display: {}",
    std::env::var("MAKE_SUPPLY_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Make Supply Run: {}",
    std::env::var("MAKE_SUPPLY_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "Price Maker Display: {}",
    std::env::var("PRICE_MAKER_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Price Maker Run: {}",
    std::env::var("PRICE_MAKER_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "Grid Schema Display: {}",
    std::env::var("GRID_SCHEMA_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Grid Schema Reverse: {}",
    std::env::var("GRID_SCHEMA_REVERSE").unwrap_or_else(|_| "true".to_string())
  );
  log::info!(
    "Grid Schema Run: {}",
    std::env::var("GRID_SCHEMA_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "GasPump Schema Display: {}",
    std::env::var("GASPUMP_SCHEMA_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "GasPump Schema Run: {}",
    std::env::var("GASPUMP_SCHEMA_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "Fuel Catalog Display: {}",
    std::env::var("FUEL_CATALOG_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Fuel Catalog Run: {}",
    std::env::var("FUEL_CATALOG_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "Display Display: {}",
    std::env::var("DISPLAY_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Display Run: {}",
    std::env::var("DISPLAY_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "Price Tracker Display: {}",
    std::env::var("PRICE_TRACKER_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Price Tracker Price Changes Time (secs): {}",
    std::env::var("PRICE_TRACKER_PRICE_CHANGES_TIME_SECS").unwrap_or_else(|_| "15".to_string())
  );

  log::info!(
    "Incoming Simulation Display: {}",
    std::env::var("INCOMING_SIMULATION_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Incoming Simulation Run: {}",
    std::env::var("INCOMING_SIMULATION_RUN").unwrap_or_else(|_| "true".to_string())
  );

  log::info!(
    "Periodic Save Grid Period: {}",
    std::env::var("PERIODIC_SAVE_GRID_PERIOD").unwrap_or_else(|_| "30".to_string())
  );
  log::info!(
    "Periodic Save Grid Display: {}",
    std::env::var("PERIODIC_SAVE_GRID_DISPLAY").unwrap_or_else(|_| "false".to_string())
  );
  log::info!(
    "Periodic Save Grid Run: {}",
    std::env::var("PERIODIC_SAVE_GRID_RUN").unwrap_or_else(|_| "true".to_string())
  );
}
