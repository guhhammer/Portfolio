use crate::config::{INSTANCE_ADJECTIVES, INSTANCE_CAPITALS};
use hmac::{Hmac, Mac};
use mac_address::get_mac_address;
use rand::prelude::*;
use sha2::Sha256;

// Type alias for convenience
type HmacSha256 = Hmac<Sha256>;

pub fn naming(secret: &[u8]) -> String {
    let mut rng = thread_rng();

    let adj = INSTANCE_ADJECTIVES.choose(&mut rng).unwrap();
    let city = INSTANCE_CAPITALS.choose(&mut rng).unwrap();

    // Get first non-loopback MAC address
    let mac_bytes = get_mac_address()
        .unwrap()
        .map(|m| m.bytes()) // get raw bytes
        .unwrap_or([0u8; 6]); // fallback

    // HMAC the MAC with a secret key
    let mut mac_hmac = HmacSha256::new_from_slice(secret).unwrap();
    mac_hmac.update(&mac_bytes);
    let result = mac_hmac.finalize().into_bytes();

    // Take first 6-8 hex chars for a short ID
    let short_id = hex::encode(&result[..4]);

    format!("{adj}_{city}_{short_id}")
}
