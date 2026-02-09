use crate::database_local_adapter::grid_getter;
use crate::datastructures::appstate::Grids;
use crate::datastructures::message::Message;
use crate::guards::auth_role::{access_level_0, access_level_1};
use crate::guards::auth_user::AuthUser;
use chrono::Local;
use mongodb::bson::doc;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use serde_json::json;

#[get("/grid-supplier/<name_id>")]
pub async fn get_grid_supplier(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_0(
    &auth,
    &name_id,
    state.grid_name_ids.read().unwrap().first().unwrap(),
  )?;

  let supplier = grid_getter::get_grid_supplier(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to get grid supplier from db. Reason {e:?}"),
      )
    })?;

  let supplier_json = serde_json::to_string(&supplier).map_err(|e| {
    status::Custom(
      Status::InternalServerError,
      format!("Failed to serialize supplier. Reason {e:?}"),
    )
  })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "grid-supplier",
    &supplier_json,
    "success",
    Some(s),
  )))
}

#[get("/grid-price-controller/<name_id>")]
pub async fn get_grid_price_controller(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_0(
    &auth,
    &name_id,
    state.grid_name_ids.read().unwrap().first().unwrap(),
  )?;

  let price_controller = grid_getter::get_grid_price_controller(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to get grid price_controller from db. Reason {e:?}"),
      )
    })?;

  let price_controller_json = serde_json::to_string(&price_controller).map_err(|e| {
    status::Custom(
      Status::InternalServerError,
      format!("Failed to serialize price_controller. Reason {e:?}"),
    )
  })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "grid-price-controller",
    &price_controller_json,
    "success",
    Some(s),
  )))
}

#[get("/grid-isles/<name_id>")]
pub async fn get_grid_isles(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_0(
    &auth,
    &name_id,
    state.grid_name_ids.read().unwrap().first().unwrap(),
  )?;

  let isles = grid_getter::get_grid_isles(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to get grid isles from db. Reason {e:?}"),
      )
    })?;

  let isles_json = serde_json::to_string(&isles).map_err(|e| {
    status::Custom(
      Status::InternalServerError,
      format!("Failed to serialize isles. Reason {e:?}"),
    )
  })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "grid-isles",
    &isles_json,
    "success",
    Some(s),
  )))
}

#[get("/grid-name-list")]
pub async fn get_grid_list(
  auth: AuthUser,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  let names_json = serde_json::to_string(&state.grid_name_ids).map_err(|e| {
    status::Custom(
      Status::InternalServerError,
      format!("Failed to serialize names. Reason {e:?}"),
    )
  })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "grid-name-list",
    &names_json,
    "success",
    Some(s),
  )))
}
