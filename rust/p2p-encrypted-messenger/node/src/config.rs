//! Application-wide configuration constants.

use std::time::Duration;

// --- Network ports -----------------------------------------------------------

/// UDP port used for peer-discovery broadcasts.
pub const APPLICATION_UDP_PORT: u16 = 15000;

/// TCP port used for encrypted peer-to-peer messages.
pub const APPLICATION_TCP_PORT: u16 = 15001;

// --- Instance naming ---------------------------------------------------------

pub const INSTANCE_ADJECTIVES: [&str; 16] = [
    "BOLD", "WISE", "ANCIENT", "MIGHTY", "HIDDEN", "NOISY", "CALM", "BRAVE", "SWIFT", "GRAND",
    "TALL", "QUIET", "LOUD", "FIERCE", "PURE", "WILD",
];

pub const INSTANCE_CAPITALS: [&str; 16] = [
    "PARIS", "TOKYO", "LONDON", "BERLIN", "OSLO", "LIMA", "CAIRO", "OTTAWA", "ANKARA", "BRASILIA",
    "MOSCOW", "SEOUL", "NAIROBI", "BANGKOK", "MADRID", "ROME",
];

// --- Peer discovery ("self awareness") ---------------------------------------

/// How often this node announces its presence on the LAN.
pub const SELF_AWARENESS_BROADCAST_INTERVAL: Duration = Duration::from_secs(5);

/// Maximum size of a UDP discovery datagram.
pub const SELF_AWARENESS_UDP_BUFFER_SIZE: usize = 1024;

/// Maximum size of a single TCP read while consuming a message.
pub const SELF_AWARENESS_TCP_BUFFER_SIZE: usize = 1024;

/// How often stale peers are pruned from the peer list.
pub const SELF_AWARENESS_CLEANUP_INTERVAL: Duration = Duration::from_secs(10);

/// A peer that stays silent for this long is considered gone.
pub const SELF_AWARENESS_PEER_TIMEOUT: Duration = Duration::from_secs(15);

/// Whether to periodically print the current peer list.
pub const SELF_AWARENESS_DISPLAY_PEERLIST: bool = true;

/// How often the peer list is printed (when enabled).
pub const SELF_AWARENESS_DISPLAY_INTERVAL: Duration = Duration::from_secs(5);

// --- Key server --------------------------------------------------------------

/// Base URL of the key server this node registers with.
/// Point this at another machine's address to join its network,
/// e.g. "http://192.168.0.101:8000".
pub const SERVER_URL: &str = "http://127.0.0.1:8000";

/// How often the node re-syncs its keys with the key server.
pub const KEY_REFRESH_INTERVAL: Duration = Duration::from_secs(60);
