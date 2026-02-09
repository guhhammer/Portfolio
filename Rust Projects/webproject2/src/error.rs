use rocket_dyn_templates::{context, Template};
use rocket::{catch, Request};

use crate::logger::log_simple_warn;

#[catch(default)]
pub fn default_catcher(status: rocket::http::Status, req: &Request) -> Template {

    log_simple_warn(format!("default catcher code: {}", status.code));

    let ans = (status.code, status.reason().unwrap_or("Unknown Error"), req.uri());

    eprintln!("Error {}: {} at {}", ans.0, ans.1, ans.2);

    Template::render("error", context! { ctx_code: ans.0, ctx_reason: ans.1, ctx_uri: ans.2})

}

// Function to register catchers (optional helper)
pub fn catchers() -> Vec<rocket::Catcher> {
    
    rocket::catchers![default_catcher]

}
