use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Deserialize)]
pub struct LoginRequest {
  pub username: String,
  pub password: String,
}

#[derive(Debug, Clone, Serialize)]
pub struct LoginResponse {
  pub token: String,
}

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct Claims {
  pub sub: String, // username
  pub exp: usize,  // expiry timestamp
}
