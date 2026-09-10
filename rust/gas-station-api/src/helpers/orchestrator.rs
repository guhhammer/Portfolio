use crate::datastructures::config::Config;
use crate::datastructures::grid::Grid;
use crate::helpers::{
  freeze, grid_maker, incoming_simulation, price_maker, price_tracker, supply_maker,
};
use std::sync::{Arc, Mutex};

pub fn initialize(config: &Config) -> Arc<Mutex<Grid>> {
  log::info!("\n\tOrchestrator is initializing grid...\n");

  log::debug!("initialize()");

  let mut grid: Arc<Mutex<Grid>> = Arc::new(Mutex::new(grid_maker::grid_maker()));

  if config.load_grid_locally {
    match freeze::load_grid_from_json(&config.grid_filepath) {
      Ok(x) => {
        log::info!("grid loaded from json");
        grid = x;
      }
      _ => {
        log::error!("attempted to load from json");
      }
    }
  } else {
    log::info!("making grid from helpers::<functions>");

    grid
      .lock()
      .unwrap()
      .update_name(config.my_grid_name.clone());

    supply_maker::make_supply(
      &mut grid,
      config.make_supply_display,
      config.make_supply_run,
      false,
    );

    let _ = price_maker::make_price(
      &mut grid,
      config.price_maker_display,
      config.price_maker_run,
      false,
    );
  }

  grid
}

pub async fn trackers_and_procedures(grid: &Arc<Mutex<Grid>>, config: &Config, activate: bool) {
  if !activate {
    log::warn!("trackers and procedures are activate");
    return;
  }

  log::info!("\n\tOrchestrator is running all procedures...\n");

  let _ = grid_maker::grid_schema(
    grid,
    config.grid_schema_display,
    config.grid_schema_reverse,
    config.grid_schema_run,
    false,
  );

  let _ = grid_maker::gaspump_schema(
    grid,
    config.gaspump_schema_display,
    config.gaspump_schema_run,
    false,
  );

  let _ = grid_maker::fuel_catalog(
    grid,
    config.fuel_catalog_display,
    config.fuel_catalog_run,
    false,
  );

  let _ = grid_maker::display(grid, config.display_display, config.display_run, false);

  price_tracker::follow(
    grid.clone(),
    config.price_tracker_display,
    config.price_tracker_price_changes_time_secs,
    false,
  );

  incoming_simulation::run(
    &grid.clone(),
    config.incoming_simulation_display,
    config.incoming_simulation_run,
  );

  freeze::periodic_save_grid(
    grid,
    config.grid_filepath.clone(),
    config.periodic_save_grid_period,
    config.periodic_save_grid_display,
    config.periodic_save_grid_run,
  );
}
