use rocket_dyn_templates::{context, Template};

#[get("/about")]
pub fn about() -> Template { Template::render("about",  context! { }) }
