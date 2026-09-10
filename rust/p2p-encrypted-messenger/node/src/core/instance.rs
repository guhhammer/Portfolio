//! Instance naming: a stable, human-readable identity per machine.
//!
//! The name combines a random adjective + capital with a short device
//! fingerprint (an HMAC of the machine's MAC address), e.g. `SWIFT_OSLO_a1b2c3d4`.

use crate::config::{INSTANCE_ADJECTIVES, INSTANCE_CAPITALS};
use hmac::{Hmac, Mac};
use mac_address::get_mac_address;
use rand::prelude::*;
use sha2::Sha256;

type HmacSha256 = Hmac<Sha256>;

pub fn naming(secret: &[u8]) -> String {
    let mut rng = thread_rng();

    let adjective = INSTANCE_ADJECTIVES.choose(&mut rng).unwrap();
    let capital = INSTANCE_CAPITALS.choose(&mut rng).unwrap();

    // First non-loopback MAC address, or zeroes when none is available.
    let mac_bytes = get_mac_address()
        .ok()
        .flatten()
        .map(|m| m.bytes())
        .unwrap_or([0u8; 6]);

    // HMAC the MAC address so the raw hardware address never leaves the machine.
    let mut mac_hmac =
        HmacSha256::new_from_slice(secret).expect("HMAC accepts keys of any length");
    mac_hmac.update(&mac_bytes);
    let fingerprint = mac_hmac.finalize().into_bytes();

    let short_id = hex::encode(&fingerprint[..4]);

    format!("{adjective}_{capital}_{short_id}")
}
