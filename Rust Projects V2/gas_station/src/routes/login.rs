use crate::EXPIRATION_TIME;
use crate::datastructures::appstate::Grids;
use crate::datastructures::user_related::{Claims, LoginRequest, LoginResponse};
use crate::helpers::hasher::check_to_hash;
use jsonwebtoken::{EncodingKey, Header, encode};
use mongodb::bson::{DateTime, doc};
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use std::time::{SystemTime, UNIX_EPOCH};

#[post("/login", data = "<login>")]
pub async fn login(
  login: Json<LoginRequest>,
  state: &State<Grids>,
) -> Result<Json<LoginResponse>, status::Custom<String>> {
  state
    .users_db
    .find_one(doc! {"username": &login.username})
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("MongoDB query failed: {e:?}"),
      )
    })?
    .ok_or_else(|| status::Custom(Status::NotFound, "Username not found".to_string()))?;

  if !check_to_hash(&state.users_db, &login.username, &login.password).await {
    return Err(status::Custom(
      Status::Unauthorized,
      "Password is wrong".to_string(),
    ));
  }

  state
    .users_db
    .update_one(
      doc! {"username": &login.username},
      doc! { "$set": { "last_login": DateTime::now() } },
    )
    .await
    .map_err(|e| {
      status::Custom(
        Status::InternalServerError,
        format!("MongoDB query failed: {e:?}"),
      )
    })?;

  // Generate JWT token.
  let expiration = SystemTime::now()
    .duration_since(UNIX_EPOCH)
    .unwrap()
    .as_secs()
    + *EXPIRATION_TIME;

  let claims = Claims {
    sub: login.username.clone(),
    exp: expiration as usize,
  };

  let token = encode(
    &Header::default(),
    &claims,
    &EncodingKey::from_secret("supersecret".as_ref()),
  )
  .unwrap();

  Ok(Json(LoginResponse { token }))
}
