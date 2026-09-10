use crate::database_local_adapter::grid_deleter;
use crate::datastructures::appstate::Grids;
use crate::datastructures::message::Message;
use crate::guards::auth_role::access_level_1;
use crate::guards::auth_user::AuthUser;
use chrono::Local;
use mongodb::bson::doc;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use serde_json::json;

#[delete("/grid/<name_id>")]
pub async fn delete_grid(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_deleter::delete_grid(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to delete grid from db. Reason {e:?}"),
      )
    })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new("delete grid", "ok", "success", Some(s))))
}

#[delete("/grid-supplier/<name_id>")]
pub async fn delete_grid_supplier(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_deleter::delete_supplier(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to delete grid supplier from db. Reason {e:?}"),
      )
    })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "delete grid supplier",
    "ok",
    "success",
    Some(s),
  )))
}

#[delete("/grid-isles/<name_id>")]
pub async fn delete_grid_isles(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_deleter::delete_isles(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to delete grid isles from db. Reason {e:?}"),
      )
    })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "delete grid isles",
    "ok",
    "success",
    Some(s),
  )))
}

#[delete("/grid-price-controller/<name_id>")]
pub async fn delete_grid_price_controller(
  auth: AuthUser,
  name_id: String,
  state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_1(&auth)?;

  grid_deleter::delete_price_controller(&state.all_grids_db, &name_id)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to delete grid price controller from db. Reason {e:?}"),
      )
    })?;

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "delete grid price controller",
    "ok",
    "success",
    Some(s),
  )))
}
