use rocket_dyn_templates::{context, Template};

#[get("/terms")]
pub fn terms() -> Template { Template::render("terms",  context!{} ) }
