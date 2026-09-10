//! Key-server communication errors.

use crate::core::error::ApplicationError;

pub const REGISTER_FAILED: ApplicationError = ApplicationError::new(
    "SRV001",
    "Could not register with the key server",
    Some("Check that the key server is running and SERVER_URL points at it"),
);

pub const FETCH_PRIV_KEY_FAILED: ApplicationError = ApplicationError::new(
    "SRV002",
    "Could not fetch this node's private key from the key server",
    None,
);

pub const FETCH_PUB_KEYS_FAILED: ApplicationError = ApplicationError::new(
    "SRV003",
    "Could not fetch peer public keys from the key server",
    None,
);
