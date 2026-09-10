//! Key server for the distributed encrypted messenger.
//!
//! A small trusted bootstrap service for the LAN: nodes register on startup,
//! the server generates their keypairs, and peers fetch each other's public
//! keys from it. Handing out private keys over plain HTTP is acceptable only
//! because this runs inside the trusted demo LAN — a production deployment
//! would generate keys on-device and serve this API over TLS.

#[macro_use]
extern crate rocket;

use base64::{Engine as _, engine::general_purpose};
use ed25519_dalek::Keypair;
use rand::rngs::OsRng;
use rocket::State;
use rocket::http::Status;
use rocket::serde::{Deserialize, json::Json};
use std::collections::HashMap;
use std::sync::RwLock;
use x25519_dalek::{PublicKey as X25519Public, StaticSecret};

/// A registered node and its key material.
pub struct LanInstance {
    pub name: String,
    /// Signing identity.
    pub ed25519: Keypair,
    /// Encryption private key.
    pub x25519: StaticSecret,
    pub x25519_pub: X25519Public,
}

impl LanInstance {
    pub fn new(name: String) -> Self {
        let mut csprng = OsRng {};

        let ed25519 = Keypair::generate(&mut csprng);

        let x25519 = StaticSecret::new(csprng);
        let x25519_pub = X25519Public::from(&x25519);

        Self {
            name,
            ed25519,
            x25519,
            x25519_pub,
        }
    }
}

type LanInstances = RwLock<HashMap<String, LanInstance>>;

#[get("/")]
fn ping() -> &'static str {
    ":8000 localserver"
}

#[derive(Deserialize)]
struct RegisterReq {
    name: String,
}

/// Register a node, generating fresh keypairs for it.
#[post("/register", data = "<body>")]
fn register(body: Json<RegisterReq>, instances: &State<LanInstances>) -> &'static str {
    let mut instances = instances.write().unwrap();

    instances.insert(body.name.clone(), LanInstance::new(body.name.clone()));

    "updated register"
}

/// Every registered node's X25519 public key (base64), keyed by name.
#[get("/get_pub_keys")]
fn get_pub_keys(instances: &State<LanInstances>) -> Json<HashMap<String, String>> {
    let instances = instances.read().unwrap();

    let keys = instances
        .values()
        .map(|i| {
            (
                i.name.clone(),
                general_purpose::STANDARD.encode(i.x25519_pub.as_bytes()),
            )
        })
        .collect();

    Json(keys)
}

/// A node's own X25519 private key (base64).
#[get("/get_priv_key?<name>")]
fn get_priv_key(name: &str, instances: &State<LanInstances>) -> Result<String, Status> {
    let instances = instances.read().unwrap();

    let instance = instances.get(name).ok_or(Status::NotFound)?;

    Ok(general_purpose::STANDARD.encode(instance.x25519.to_bytes()))
}

#[rocket::main]
async fn main() -> Result<(), Box<rocket::Error>> {
    let instances: LanInstances = RwLock::new(HashMap::new());

    rocket::build()
        .manage(instances)
        .mount("/", routes![ping, register, get_pub_keys, get_priv_key])
        .launch()
        .await?;

    Ok(())
}
