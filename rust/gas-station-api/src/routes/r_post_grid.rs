use crate::database_local_adapter::grid_poster;
use crate::datastructures::appstate::Grids;
use crate::datastructures::grid::Grid;
use crate::datastructures::message::Message;
use crate::guards::auth_role::access_level_2;
use crate::guards::auth_user::AuthUser;
use crate::helpers::builder::grid_builder;
use chrono::Local;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use serde_json::json;

#[post("/grid", data = "<grid_req>")]
pub async fn post_grid(
  auth: AuthUser,
  state: &State<Grids>,
  grid_req: Json<Grid>,
) -> Result<Json<Message>, status::Custom<String>> {
  access_level_2(&auth)?;

  let g = grid_builder(
    grid_req.read_name(),
    grid_req.get_supplier().clone(),
    grid_req
      .read_prices()
      .clone()
      .iter()
      .map(|(&fuel, &amount)| (fuel, amount))
      .collect(),
    grid_req.get_isles().clone(),
  );

  grid_poster::post_grid(&state.all_grids_db, g)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to post grid to db. Reason {e:?}"),
      )
    })?;

  {
    let mut ids = state.grid_name_ids.write().unwrap();
    ids.push(grid_req.read_name().clone());
  }

  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "post_grid",
    "Grid appended in db",
    "success",
    Some(s),
  )))
}
