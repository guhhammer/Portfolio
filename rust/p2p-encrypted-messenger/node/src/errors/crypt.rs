//! Cryptography errors.

use crate::core::error::ApplicationError;

pub const INVALID_KEY_ENCODING: ApplicationError = ApplicationError::new(
    "CRYPT001",
    "Key is not valid base64",
    Some("Keys are exchanged as base64-encoded 32-byte values"),
);

pub const INVALID_KEY_LENGTH: ApplicationError = ApplicationError::new(
    "CRYPT002",
    "Key has the wrong length",
    Some("X25519 keys must decode to exactly 32 bytes"),
);

pub const ENCRYPTION_FAILED: ApplicationError =
    ApplicationError::new("CRYPT003", "Message encryption failed", None);

pub const DECRYPTION_FAILED: ApplicationError = ApplicationError::new(
    "CRYPT004",
    "Message decryption failed",
    Some("The ciphertext may be corrupted, or the peers' keys do not match"),
);

pub const INVALID_PAYLOAD_ENCODING: ApplicationError = ApplicationError::new(
    "CRYPT005",
    "Payload fields are not valid base64",
    None,
);

pub const UNKNOWN_SENDER: ApplicationError = ApplicationError::new(
    "CRYPT006",
    "No public key known for the sender",
    Some("The sender may not be registered with the key server yet"),
);

pub const MALFORMED_MESSAGE: ApplicationError = ApplicationError::new(
    "CRYPT007",
    "Decrypted payload is not a valid message",
    None,
);
