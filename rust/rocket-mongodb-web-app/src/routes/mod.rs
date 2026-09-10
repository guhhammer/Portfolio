pub mod about;
pub mod admin;
pub mod create;
pub mod donate;
pub mod index;
pub mod search;
pub mod terms;
pub mod trends;
pub mod user_dashboard;

pub fn mount_routes() -> Vec<rocket::Route> {
 
    routes![ 

        about::about,
        admin::admin,

        create::get_create,
        create::post_create, 
        
        donate::get_donate,
        donate::post_donate,

        index::index,
        index::catch_all,

        search::empty_search,
        search::search,
        
        trends::t,
        terms::terms,
        
        user_dashboard::get_u,
        user_dashboard::delete_u,
        user_dashboard::patch_u,
    
    ]

}