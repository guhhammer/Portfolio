use rocket::http::Status;
use rocket_dyn_templates::{context, Template};
use rocket::serde::json::Json;
use rocket::serde::json::Value;
use mongodb::{bson, bson::doc};
use std::sync::Arc;
use serde_json::json;
use crate::db::crud::get_user_by_donate_address;
use crate::utils::make_fetch_user::user_to_userfetch;
use crate::{db::crud::{delete_user, update_user}, models::user::User, state};


// Route to delete a user by name
#[delete("/u/<user_address>")]
pub async fn delete_u(user_address: Option<String>, state: &rocket::State<Arc<state::AppState>>) -> () {
  
    let _ = match user_address { 

        Some(address) => delete_user(address, state).await, 
        None => 3, 

    };

}

#[patch("/u/<user_address>", format = "json", data = "<data>")]
pub async fn patch_u(user_address: Option<&str>, data: Json<User>, state: &rocket::State<Arc<state::AppState>>) -> Json<Value> {

    let user_address = user_address.unwrap_or_default().to_string();

    let doc_data = bson::to_document(&data.into_inner()).unwrap_or_else(|_| doc! {});

    let result = update_user(user_address, Json(doc_data), state).await; 

    Json(json!({ "message": result }))

}

#[get("/u/<user_address>")]
pub async fn get_u(user_address: &str, state: &rocket::State<Arc<state::AppState>>) -> Result<Template, Status> {

    // make validation to check if user is user 

    let u_user = match get_user_by_donate_address(user_address.to_string(), state).await {

        Some(u) => u.0, None => return Err(Status::NotFound),

    };

    Ok(Template::render("u", context! { ctx_user: user_to_userfetch(u_user) }))

}