#[macro_use]
extern crate rocket;

use rocket::tokio::time::{sleep, Duration};

#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}

#[get("/route1")]
fn route1() -> &'static str {
    "1st route"
}

#[get("/delay/<seconds>")]
async fn delay(seconds: u64) -> String {
    sleep(Duration::from_secs(seconds)).await;
    format!("Delayed for {} seconds", seconds)
}

#[rocket::main]
async fn main() -> Result<(), rocket::Error> {
    let _rocket = rocket::build()
        .mount("/", routes![index, route1, delay])
        .launch()
        .await?;

    Ok(())
}

// rocket guide link: https://rocket.rs/guide/v0.5/