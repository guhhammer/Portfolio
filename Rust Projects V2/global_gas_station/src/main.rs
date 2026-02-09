#[macro_use] extern crate rocket;

use rocket::http::Status;
use std::net::SocketAddr;

#[get("/ping")]
fn ping(addr: SocketAddr) -> Status {
    println!("Received ping from: {}", addr.ip());
    Status::Ok
}

#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![ping])
}
