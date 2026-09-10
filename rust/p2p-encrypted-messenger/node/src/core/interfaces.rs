//! Core traits — the ports of the hexagonal architecture.

use crate::core::error::ApplicationError;

/// A wire-format payload that can be serialized for transport.
pub trait ApplicationPayloadTrait {
    fn as_bytes(&self) -> Result<Vec<u8>, ApplicationError>;
}

/// A message the application can act on.
///
/// Actions follow the convention `"do-<x>"`, `"done-<x>"`, `"update-<x>"`,
/// `"remove-<x>"`.
pub trait ApplicationMessageTrait: Send + Sync {
    fn instance_name(&self) -> &str;
    fn timestamp(&self) -> u64;
    fn action(&self) -> &str;
    fn changes(&self) -> Option<&str>;
    fn state(&self) -> Option<&str>;

    fn as_json(&self) -> Result<String, ApplicationError>;
    fn clone_box(&self) -> Box<dyn ApplicationMessageTrait>;

    /// Encrypt this message for one recipient and serialize it for transport.
    fn seal(
        &self,
        sender_name: &str,
        recipient_name: &str,
        sender_priv_b64: &str,
        recipient_pub_b64: &str,
    ) -> Result<Vec<u8>, ApplicationError>;
}

impl Clone for Box<dyn ApplicationMessageTrait> {
    fn clone(&self) -> Box<dyn ApplicationMessageTrait> {
        self.clone_box()
    }
}
