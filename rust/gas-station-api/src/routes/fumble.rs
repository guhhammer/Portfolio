use crate::guards::auth_user::AuthUser;
use rocket::response::status;
use rocket::serde::json::Json;

#[get("/<user>/profile")]
pub async fn profile(user: String, auth: AuthUser) -> Result<Json<String>, status::Custom<String>> {
  //validate_user(user.clone(), auth).await?;
  crate::guards::auth_role::access_level_1(&auth)?;

  Ok(Json(format!("Profile data for {user}")))
}

#[get("/ping")]
pub async fn proxy_to_other() -> Json<String> {
  // Make HTTP request to another server
  let _resp = reqwest::get("http://localhost:15000/ping").await;

  Json("ping sent".to_string())
}

/*
┌──(gustavo㉿kali)-[~/Desktop/rust-projects]
└─$ curl -X POST http://127.0.0.1:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret123"}'

{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImV4cCI6MTc1NjMzNjUzNX0.CdIh2zIvEMnalzkzPlVoNiw3wX0ddPGgRCj4zQh5PpE"}

┌──(gustavo㉿kali)-[~/Desktop/rust-projects]
└─$ curl -X GET  http://127.0.0.1:8080/api/alice/profile \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImV4cCI6MTc1NjMzNjUzNX0.CdIh2zIvEMnalzkzPlVoNiw3wX0ddPGgRCj4zQh5PpE"

"Profile data for alice"


┌──(gustavo㉿kali)-[~/Desktop/rust-projects]
└─$ curl -X GET  http://127.0.0.1:8080/api/bob/profile \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImV4cCI6MTc1NjMzNjUzNX0.CdIh2zIvEMnalzkzPlVoNiw3wX0ddPGgRCj4zQh5PpE"

Access denied
*/
