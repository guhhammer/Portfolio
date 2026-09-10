use std::time::Duration;
//
//
pub const APPLICATION_UDP_PORT: u64 = 15000;

pub const APPLICATION_TCP_PORT: u64 = 15001;

pub const APPLICATION_TCP_ADDR: &str = "0.0.0.0:15001"; // MAKE CHANGES TO BOTH.
                                                        //
                                                        //
pub const INSTANCE_ADJECTIVES: [&str; 16] = [
    "BOLD", "WISE", "ANCIENT", "MIGHTY", "HIDDEN", "NOISY", "CALM", "BRAVE", "SWIFT", "GRAND",
    "TALL", "QUIET", "LOUD", "FIERCE", "PURE", "WILD",
];

pub const INSTANCE_CAPITALS: [&str; 16] = [
    "PARIS", "TOKYO", "LONDON", "BERLIN", "OSLO", "LIMA", "CAIRO", "OTTAWA", "ANKARA", "BRASILIA",
    "MOSCOW", "SEOUL", "NAIROBI", "BANGKOK", "MADRID", "ROME",
];
//
//
pub const SELF_AWARENESS_BROADCAST_ALIVE: u64 = 5;

pub const SELF_AWARENESS_BUFFER_SIZE: [u8; 1024] = [0u8; 1024];

pub const SELF_AWARENESS_CLEANUP_REFRESH_RATE: u64 = 10;

pub const SELF_AWARENESS_DISPLAY_PEERLIST_SHOW: bool = true;

pub const SELF_AWARENESS_DISPLAY_PEERLIST_TIME: Duration = Duration::from_millis(5000);

pub const SELF_AWARENESS_TCP_BUFFER: [u8; 1024] = [0u8; 1024];

pub const SELF_AWARENESS_TIMEOUT: u64 = 15;
//
//
pub const SERVER_URL: &str = "http://127.0.0.1:8000";
//    //for instance 2 connect to this machine server.
//    //let server_url = "http://192.168.0.101:8000";
