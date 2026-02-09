#[macro_use]
extern crate rocket;

use base64::{Engine as _, engine::general_purpose};
use ed25519_dalek::Keypair;
use rand::rngs::OsRng;
use rocket::State;
use rocket::serde::{Deserialize, json::Json};
use std::collections::HashMap;
use std::sync::RwLock;
use x25519_dalek::{PublicKey as X25519Public, StaticSecret};

pub struct LanInstance {
    pub name: String,
    pub ed25519: Keypair,     // signing key
    pub x25519: StaticSecret, // encryption private key
    pub x25519_pub: X25519Public,
}

impl LanInstance {
    pub fn new(name: String) -> Self {
        let mut csprng = OsRng {};

        // 1️⃣ Ed25519 keypair (signing)
        let ed25519: Keypair = Keypair::generate(&mut csprng);

        // 2️⃣ X25519 keypair (encryption)
        let x25519 = StaticSecret::new(&mut csprng);
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

#[get("/get_pub_keys")]
fn get_pub_keys(lan_i: &State<LanInstances>) -> Json<HashMap<String, String>> {
    let lan_i_r = lan_i.read().unwrap();

    let mut h: HashMap<String, String> = HashMap::new();

    let pub_keys = lan_i_r
        .values()
        .for_each(|li|
           { h.insert(li.name.clone(), general_purpose::STANDARD.encode(li.x25519_pub.as_bytes())); });

    Json(h)
}

#[derive(Deserialize)]
struct RegisterReq {
    name: String,
}
#[post("/register", data = "<body>")]
fn register(body: Json<RegisterReq>, lan_i: &State<LanInstances>) -> &'static str {
    let mut lan_i_w = lan_i.write().unwrap();

    lan_i_w.insert(body.name.clone(), LanInstance::new(body.name.clone()));

    "updated register"
}

#[get("/")]
fn ping() -> &'static str {
    ":8000 localserver"
}

#[get("/get_priv_key", data = "<body>")]
fn get_priv_key(body: Json<RegisterReq>, lan_i: &State<LanInstances>) -> String {

    let lan_i_r = lan_i.read().unwrap();

    let secret: &StaticSecret = &lan_i_r[&body.name].x25519;

// Convert to bytes
let secret_bytes = secret.to_bytes();

// Encode as Base64
let secret_b64 = general_purpose::STANDARD.encode(secret_bytes);

// Send it in JSON
format!("{}", secret_b64)
}


#[rocket::main]
async fn main() -> Result<(), rocket::Error> {
    let lan_instances: LanInstances = RwLock::new(HashMap::new());
    rocket::build()
        .manage(lan_instances)
        .mount("/", routes![register, get_pub_keys, ping, get_priv_key])
        .launch()
        .await?;

    Ok(())
}
