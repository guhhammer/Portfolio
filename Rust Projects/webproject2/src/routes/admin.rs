use rocket_dyn_templates::{context, Template};
use std::sync::Arc;

use crate::{db::crud::get_all_users, models::user_fetch::UserFetch, state, utils::make_fetch_user::user_to_userfetch};

pub static PAGE: u32 = 1;

#[get("/admin")]
pub async fn admin(state: &rocket::State<Arc<state::AppState>>) -> Template { 
    
    let x: Vec<UserFetch> = get_all_users(PAGE, 20, state)
    .await
    .0
    .into_iter()
    .map(|x| user_to_userfetch(x))
    .collect();

    Template::render("admin",  context! { ctx_user: false, ctx_all_users: x,}) 

}

// This is just a scratch.
