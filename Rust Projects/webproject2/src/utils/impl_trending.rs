use std::sync::{Arc, OnceLock};
use tokio::sync::RwLock;
use chrono::Utc;

use crate::config::db_config;
use crate::db::crud::get_trending_users;
use crate::logger::log_simple_info;
use crate::models::trending::{SingletonTrending, Trending};
use crate::state;

impl Trending {
    
    #[allow(dead_code)]
    pub fn new() -> Self {
   
        Trending {
   
            profile_image: "".to_string(),
   
            profile_name: "".to_string(),
   
            donate_address: "".to_string(),
            
            number_donations_24h: "".to_string(),
            
            amount_donated_24h: "".to_string(),
            
            categories: "".to_string(),
   
            tier: "".to_string(),
   
        }
   
    }

}

impl SingletonTrending {

    pub fn instance() -> Arc<RwLock<SingletonTrending>> {

        static INSTANCE: OnceLock<Arc<RwLock<SingletonTrending>>> = OnceLock::new();

        log_simple_info("Singleton Instance Created.".to_string());

        INSTANCE.get_or_init(|| {
            Arc::new(RwLock::new(SingletonTrending {

                counter: 0, 

                starter: true,

                vec_t: Vec::new(),
    
                last_updated_time: Utc::now().timestamp(),

            }))
            
        }).clone()
   
    }

    pub async fn signal_and_update(&mut self, thread_time: i64, state: &rocket::State<Arc<state::AppState>>) {

        if thread_time > self.last_updated_time + db_config::TREND_REFRESH_TIMER as i64 || self.starter {

            self.starter = false;

            self.counter += 1;
            
            let aux = get_trending_users(state).await.0;

            self.vec_t = aux;
            
            self.last_updated_time = thread_time;            
            
            log_simple_info("Trending Users Updated.".to_string());

        }
        
    }

    pub fn get_state(&self) -> Vec<Trending> { self.vec_t.clone() }

    pub fn get_counter(&self) -> u32 { self.counter } 

}


