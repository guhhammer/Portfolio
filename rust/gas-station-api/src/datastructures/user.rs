use mongodb::bson::DateTime;
use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
pub struct User {
  username: String,
  email: String,
  password_hash: String,
  roles: Vec<String>,
  created_at: DateTime,
  last_login: DateTime,
}

impl User {
  pub fn new(
    username: String,
    email: String,
    password_hash: String,
    roles: Vec<String>,
    created_at: DateTime,
    last_login: DateTime,
  ) -> Self {
    Self {
      username,
      email,
      password_hash,
      roles,
      created_at,
      last_login,
    }
  }
}
