use argon2::{
  Argon2, PasswordHash, PasswordHasher, PasswordVerifier,
  password_hash::{SaltString, rand_core::OsRng},
};
use mongodb::Collection;
use mongodb::bson::doc;

pub fn string_to_hash(s: String) -> String {
  let salt = SaltString::generate(&mut OsRng);
  let argon2 = Argon2::default();

  argon2
    .hash_password(s.as_bytes(), &salt)
    .expect("Failed to hash string")
    .to_string()
}

pub async fn check_to_hash(
  users: &Collection<mongodb::bson::Document>,
  username: &str,
  password: &str,
) -> bool {
  // 1. Fetch user by username
  let user_doc = match users.find_one(doc! { "username": username }).await {
    Ok(Some(doc)) => doc,
    _ => return false, // user not found or error
  };

  // 2. Get stored hash from the document
  let stored_hash = match user_doc.get_str("password_hash") {
    Ok(h) => h,
    Err(_) => return false,
  };

  // 3. Verify password
  let parsed_hash = match PasswordHash::new(stored_hash) {
    Ok(ph) => ph,
    Err(_) => return false,
  };

  Argon2::default()
    .verify_password(password.as_bytes(), &parsed_hash)
    .is_ok()
}
