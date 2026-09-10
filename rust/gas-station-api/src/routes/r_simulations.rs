use crate::SIMULATION_NAME_ID;
use crate::datastructures::appstate::Grids;
use crate::datastructures::grid::Grid;
use crate::datastructures::message::Message;
use crate::datastructures::message_buf::MessageBuffer;
use crate::helpers::grid_maker;
use crate::helpers::price_maker::make_price;
use chrono::Local;
use mongodb::bson::doc;
use regex::Regex;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use serde::{Deserialize, Serialize};
use serde_json::json;
use std::fs;
use std::fs::File;
use std::io::BufRead;
use std::io::BufReader;
use std::sync::{Arc, Mutex};

#[derive(Serialize, Deserialize, Debug, Clone)]
struct FuelEntry {
  fuel: String,
  amount: u64,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
struct Prices {
  entries: Vec<Vec<FuelEntry>>, // array of arrays
}

fn latest_file_in_dir(dir: &str) -> Option<String> {
  let mut files: Vec<_> = fs::read_dir(dir)
    .ok()?
    .filter_map(|entry| entry.ok())
    .filter(|entry| entry.path().is_file())
    .collect();

  // Sort by timestamp in filename
  files.sort_by(|a, b| {
    let a_name = a.file_name().into_string().unwrap_or_default();
    let b_name = b.file_name().into_string().unwrap_or_default();

    // Extract timestamp: incoming_YYYYMMDD-HHMMSS.log
    let a_ts = a_name
      .trim_start_matches("incoming_")
      .trim_end_matches(".log");
    let b_ts = b_name
      .trim_start_matches("incoming_")
      .trim_end_matches(".log");

    a_ts.cmp(b_ts)
  });

  // Return the last (latest) file
  files
    .last()
    .map(|entry| entry.path().to_string_lossy().into_owned())
}

fn strip_ansi_codes(input: &str) -> String {
  // Matches escape sequences like \u001b[1;32m
  let re = Regex::new(r"\x1b\[[0-9;]*m").unwrap();
  re.replace_all(input, "").to_string()
}

fn tail_file(file_path: &str, n: usize) -> Vec<String> {
  let file = File::open(file_path).unwrap();
  let reader = BufReader::new(file);
  let lines: Vec<String> = reader.lines().map(|l| l.unwrap()).collect();
  lines
    .into_iter()
    .rev()
    .filter(|l| !l.trim().is_empty())
    .map(|l| strip_ansi_codes(&l))
    .take(n)
    .collect::<Vec<_>>()
    .into_iter()
    .rev()
    .collect()
}

async fn selector(
  state: &State<Grids>,
  route_name: &str,
  tail: Option<usize>,
) -> Result<Json<Message>, status::Custom<String>> {
  let simulation_grid: Grid = state
    .all_grids_db
    .find_one(doc! {"name": &*SIMULATION_NAME_ID})
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e.to_string()))?
    .ok_or_else(|| status::Custom(Status::NotFound, "Simulation grid not found".to_string()))?;

  let mut wrapper: Arc<Mutex<Grid>> = Arc::new(Mutex::new(simulation_grid.clone()));

  let ret_value: String = match route_name {
    "fuel-catalog" => grid_maker::fuel_catalog(&wrapper, false, true, true),
    "gaspump-schema" => grid_maker::gaspump_schema(&wrapper, false, true, true),
    "grid-display" => grid_maker::display(&wrapper, false, true, true),
    "grid-schema" => grid_maker::grid_schema(&wrapper, false, false, true, true),
    "grid-supply" => {
      let mut m_buf: MessageBuffer = MessageBuffer::new();

      simulation_grid.display_supply(&mut m_buf, true);

      Some(m_buf.output_as_string())
    }
    "grid" => match &serde_json::to_string(&simulation_grid) {
      Ok(s) => Some(s.clone()),
      Err(_) => {
        return Err(status::Custom(
          Status::InternalServerError,
          "Could not serialize simulation grid".to_string(),
        ));
      }
    },
    "grid-name" => Some(SIMULATION_NAME_ID.clone()),
    "price-maker" => make_price(&mut wrapper, false, true, true),
    "incoming" => {
      let last = match latest_file_in_dir("./log/incoming-simulation/") {
        Some(s) => s,
        _ => {
          return Err(status::Custom(
            Status::NotFound,
            "No price entries found".to_string(),
          ));
        }
      };

      let tail_lines: String = tail_file(&last, tail.unwrap_or(10)).join("\n");

      Some(tail_lines)
    }

    _ => Some("_".to_string()),
  }
  .ok_or_else(|| {
    status::Custom(
      Status::NotFound,
      format!("Server could not retrieve {route_name}!"),
    )
  })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    route_name,
    &ret_value,
    "success",
    Some(s),
  )))
}

#[get("/fuel-catalog")]
pub async fn fuel_catalog(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "fuel-catalog", None).await
}

#[get("/gaspump-schema")]
pub async fn gaspump_schema(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "gaspump-schema", None).await
}

#[get("/grid-display")]
pub async fn grid_display(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "grid-display", None).await
}

#[get("/grid-schema")]
pub async fn grid_schema(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "grid-schema", None).await
}

#[get("/grid-supply")]
pub async fn grid_supply(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "grid-supply", None).await
}

#[get("/grid")]
pub async fn full_grid(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "grid", None).await
}

#[get("/grid-name")]
pub async fn grid_name(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "grid-name", None).await
}

#[get("/price-maker")]
pub async fn price_maker(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  selector(state, "price-maker", None).await
}

#[get("/incoming?<tail_index>")]
pub async fn incoming(
  tail_index: Option<usize>,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  let tail = tail_index.unwrap_or(10);
  selector(state, "incoming", Some(tail)).await
}

#[get("/price-tracker")]
pub async fn price_tracker(_state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  // Open the JSON file
  let file = File::open("src/etc/price_records.json")
    .map_err(|_| status::Custom(Status::NotFound, "Price record file not found".to_string()))?;
  let reader = BufReader::new(file);

  // Deserialize JSON
  let prices: Prices = serde_json::from_reader(reader).map_err(|_| {
    status::Custom(
      Status::InternalServerError,
      "Failed to parse price record".to_string(),
    )
  })?;

  // Get the latest entry (last element of entries)
  let latest_entry = prices
    .entries
    .last()
    .ok_or_else(|| status::Custom(Status::NotFound, "No price entries found".to_string()))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "price-tracker",
    &serde_json::to_string(latest_entry).unwrap(),
    "success",
    Some(s),
  )))
}
