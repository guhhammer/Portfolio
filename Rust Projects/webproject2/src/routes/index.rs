use rocket_dyn_templates::{context, Template};
use rocket::response::Redirect;
use std::sync::Arc;
use chrono::Utc;

use crate::{models::trending::{SingletonTrending, Trending}, state};

#[get("/<path>", rank = 2)]
pub fn catch_all(path: String) -> Redirect {
    
    if path == "u" || path == "d" {
        
        Redirect::to("/")
    
    } else {
     
        Redirect::to(format!("/{}", path)) // Keeps other paths working
    
    }

} 

#[get("/")]
pub async fn index(state: &rocket::State<Arc<state::AppState>>) -> Template {

    let s_trending: Arc<tokio::sync::RwLock<SingletonTrending>> = SingletonTrending::instance();

    {

        let mut s = s_trending.write().await;

        let curr_time: i64 = Utc::now().timestamp();

        s.signal_and_update(curr_time, &state).await;

    }

    let s = s_trending.read().await;

    let trends: Vec<Trending> = s.get_state();

    let _state_counter = s.get_counter();

    Template::render("index",  context! { ctx_trending: trends })

}

