use serde::Deserialize;

#[derive(Debug, Deserialize)]
pub struct Config {
  pub load_grid_locally: bool,

  pub grid_filepath: String,

  pub my_grid_name: String,

  pub make_supply_display: bool,
  pub make_supply_run: bool,

  pub price_maker_display: bool,
  pub price_maker_run: bool,

  pub grid_schema_display: bool,
  pub grid_schema_reverse: bool,
  pub grid_schema_run: bool,

  pub gaspump_schema_display: bool,
  pub gaspump_schema_run: bool,

  pub fuel_catalog_display: bool,
  pub fuel_catalog_run: bool,

  pub display_display: bool,
  pub display_run: bool,

  pub price_tracker_display: bool,
  pub price_tracker_price_changes_time_secs: u64,

  pub incoming_simulation_display: bool,
  pub incoming_simulation_run: bool,

  pub periodic_save_grid_period: u64,
  pub periodic_save_grid_display: bool,
  pub periodic_save_grid_run: bool,
}

pub fn load_config() -> Config {
  let data = include_str!("../../src/etc/config.json"); // embeds file into binary
  serde_json::from_str(data).unwrap()
}
