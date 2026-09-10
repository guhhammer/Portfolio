use crate::database_local_adapter::grid_poster;
use crate::datastructures::appstate::Grids;
use crate::datastructures::message::Message;
use crate::helpers::builder::fixed_test_builder;
use chrono::Local;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use serde_json::json;

#[post("/grid-fixed")]
pub async fn post_grid(state: &State<Grids>) -> Result<Json<Message>, status::Custom<String>> {
  let grid_local = fixed_test_builder("TEST_GRID_POST");

  grid_poster::post_grid(&state.all_grids_db, grid_local)
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("Failed to post grid to db. Reason {e:?}"),
      )
    })?;
  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Ok(Json(Message::new(
    "post_grid",
    "Grid appended in db",
    "success",
    Some(s),
  )))
}

/*


#[get("/<user>/grid/<grid_name>?<grid_index>")]
pub async fn full_grid(
    user: String,
    auth: AuthUser,
    grid_name: String,
    grid_index: Option<usize>,
    state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
    let pos = validate_user_and_grid_index(user, auth, grid_name, grid_index, state).await?;

    let schema = state.grid_state_pointer.get(pos).ok_or_else(|| {
        status::Custom(
            Status::NotFound,
            "Grid not found (Clue: Maybe wrong name).".to_string(),
        )
    })?;

    let grid = schema.grid_pointer.lock().map_err(|_| {
        status::Custom(
            Status::InternalServerError,
            "Failed to lock grid".to_string(),
        )
    })?;

    Ok(Json(Message::new(
        "grid",
        &serde_json::to_string(&*grid).map_err(|_| {
            status::Custom(
                Status::InternalServerError,
                "Failed to serialize grid".to_string(),
            )
        })?,
    )))
}


*/
