//! Global gas-station server.
//!
//! The central aggregator every local station server reports to. For now it
//! only answers pings; it will grow into the company-wide registry of
//! stations, their grids, and enabled features.

#[macro_use]
extern crate rocket;

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
