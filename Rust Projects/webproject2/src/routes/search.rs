use rocket_dyn_templates::{context, Template};
use std::sync::Arc;
use crate::{db::crud::{get_all_users, get_users_search}, state};

static PAGE: u32 = 1;

#[get("/search")]
pub fn empty_search() -> Template {

    Template::render("search", context! { ctx_cat: false, ctx_empty: true  })

}

#[get("/search?<query>&<category>")]
pub async fn search(query: &str, category: bool, state: &rocket::State<Arc<state::AppState>>) -> Template {
    
    let put = get_users_search(query, category, PAGE, 20, state).await.0;

    let _admin_only = get_all_users(PAGE, 20, state);

    let context = context! {

        ctx_cat: false, ctx_empty: put,

    }; 

    // make it refresh with more 20 each roll down.

    Template::render("search", &context)

}

