use mac_address::get_mac_address;
use rand::prelude::IndexedRandom;
use rand::rng;

pub const ADJECTIVES: [&str; 16] = [
    "BOLD", "WISE", "ANCIENT", "MIGHTY", "HIDDEN", "NOISY", "CALM", "BRAVE", "SWIFT", "GRAND",
    "TALL", "QUIET", "LOUD", "FIERCE", "PURE", "WILD",
];

pub const CAPITALS: [&str; 16] = [
    "PARIS", "TOKYO", "LONDON", "BERLIN", "OSLO", "LIMA", "CAIRO", "OTTAWA", "ANKARA", "BRASILIA",
    "MOSCOW", "SEOUL", "NAIROBI", "BANGKOK", "MADRID", "ROME",
];

pub fn generate_instance_name() -> String {
    let mut rng = rng();
    let adj = ADJECTIVES.as_slice().choose(&mut rng).unwrap();
    let city = CAPITALS.as_slice().choose(&mut rng).unwrap();

    // Get first non-loopback MAC address
    let mac = get_mac_address()
        .unwrap()
        .map(|m| m.to_string())
        .unwrap_or("UNKNOWN_MAC".to_string());

    format!("{adj}_{city}_{mac}")
}
