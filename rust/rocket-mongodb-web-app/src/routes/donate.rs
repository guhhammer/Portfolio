use rocket_dyn_templates::{context, Template};
use rocket::{http::Status, serde::json::Json};
use std::sync::Arc;
use crate::{db::crud::{create_donation, get_user_by_donate_address}, models::donation::Donation, state};
use crate::utils::make_fetch_user::user_to_userfetch;

#[post("/d", format = "json", data = "<data>")]
pub async fn post_donate(data: Json<Donation>,  state: &rocket::State<Arc<state::AppState>>) -> () {

    create_donation(data, state).await; 

} 

#[get("/d/<donate_address>")]
pub async fn get_donate(donate_address: &str,  state: &rocket::State<Arc<state::AppState>>) -> Result<Template, Status> {

    let d_user = match get_user_by_donate_address(donate_address.to_string(), state).await {

        Some(u) => u.0, None => return Err(Status::NotFound),
    
    }; 

    Ok(Template::render("d", context! { ctx_user: user_to_userfetch(d_user) } ))

} 

