use rocket_dyn_templates::{context, Template};
use rocket::serde::json::Json;
use mongodb::bson::doc;
use std::sync::Arc;

use crate::{db::crud::db_create, models, state};

#[post("/create", data="<user>")]
pub async fn post_create(user: Json<models::user::User>, state: &rocket::State<Arc<state::AppState>>) -> () {

    let _ = db_create(user, state).await;

}

#[get("/create")]
pub fn get_create() -> Template { Template::render("create", context!{} ) }