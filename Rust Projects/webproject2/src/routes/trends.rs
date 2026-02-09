use std::sync::Arc;

use chrono::Utc;
use rocket_dyn_templates::{context, Template};

use crate::{models::trending::{SingletonTrending, Trending}, state};

#[get("/t")]
pub async fn t(state: &rocket::State<Arc<state::AppState>>) -> Template { 
    
    let s_trending: Arc<tokio::sync::RwLock<SingletonTrending>> = SingletonTrending::instance();

    {

        let mut s = s_trending.write().await;

        let curr_time: i64 = Utc::now().timestamp();

        s.signal_and_update(curr_time, &state).await;

    }

    let s = s_trending.read().await;

    let trends: Vec<Trending> = s.get_state();

    let _state_counter = s.get_counter();

    let tt = &trends.get(0).unwrap().profile_image;

    println!("tt: {:?}", tt);

    Template::render("t",  context! { ctx_trends: tt } )  

}
