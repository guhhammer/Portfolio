use crate::database_local_adapter::grid_patcher;
use crate::datastructures::appstate::Grids;
use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::message::Message;
use crate::guards::auth_role::{access_level_1, access_level_2};
use crate::guards::auth_user::AuthUser;
use chrono::Local;
use mongodb::bson::doc;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use serde_json::json;
use std::collections::HashMap;

/// Patch a single fuel price in price_controller
#[patch("/grid-price-controller/<grid_name>/<fuel>/<price>")]
pub async fn patch_one_price_controller_route(
  auth: AuthUser,
  grid_name: String,
  fuel: String,
  price: u32,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  let fuel_enum = Fuel::from_api(&fuel)
    .map_err(|e| status::Custom(Status::BadRequest, format!("Invalid fuel: {e}")))?;

  grid_patcher::patch_one_price_controller(&state.all_grids_db, &grid_name, fuel_enum, price)
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "patch price_controller",
    "ok",
    "success",
    Some(s),
  )))
}

/// Patch multiple fuels in price_controller
#[patch("/grid-price-controller/<grid_name>", data = "<prices>")]
pub async fn patch_many_price_controller_route(
  auth: AuthUser,
  grid_name: String,
  prices: Json<HashMap<Fuel, u32>>,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_patcher::patch_many_price_controller(&state.all_grids_db, &grid_name, prices.into_inner())
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "patch many price_controller",
    "ok",
    "success",
    Some(s),
  )))
}

/// Patch grid name
#[patch("/grid-name/<grid_name>/<new_name>")]
pub async fn patch_grid_name_route(
  auth: AuthUser,
  grid_name: String,
  new_name: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_2(&auth)?;

  grid_patcher::patch_name(&state.all_grids_db, &grid_name, &new_name)
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "patch grid name",
    "ok",
    "success",
    Some(s),
  )))
}

/// Patch a single supplier price
#[patch("/grid-supplier/<grid_name>/<fuel>/<price>")]
pub async fn patch_one_supplier_route(
  auth: AuthUser,
  grid_name: String,
  fuel: String,
  price: u32,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  let fuel_enum = Fuel::from_api(&fuel)
    .map_err(|e| status::Custom(Status::BadRequest, format!("Invalid fuel: {e}")))?;

  grid_patcher::patch_one_supplier(&state.all_grids_db, &grid_name, fuel_enum, price)
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "patch supplier",
    "ok",
    "success",
    Some(s),
  )))
}

/// Patch multiple supplier prices
#[patch("/grid-supplier/<grid_name>", data = "<suppliers>")]
pub async fn patch_many_supplier_route(
  auth: AuthUser,
  grid_name: String,
  suppliers: Json<HashMap<Fuel, u32>>,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_patcher::patch_many_supplier(&state.all_grids_db, &grid_name, suppliers.into_inner())
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "patch many supplier",
    "ok",
    "success",
    Some(s),
  )))
}

/// Patch isles
#[patch("/grid-isles/<grid_name>", data = "<isles>")]
pub async fn patch_one_isles_route(
  auth: AuthUser,
  grid_name: String,
  isles: Json<Vec<Vec<GasPump>>>,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_patcher::patch_one_isles(&state.all_grids_db, &grid_name, isles.into_inner())
    .await
    .map_err(|e| status::Custom(Status::InternalServerError, e))?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new("patch isles", "ok", "success", Some(s))))
}
