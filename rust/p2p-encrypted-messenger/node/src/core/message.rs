//! The application-level message and its encryption into wire payloads.

use crate::core::crypt::{
    decrypt_from_peer, encrypt_for_peer, static_secret_from_b64, x25519_pub_from_b64,
};
use crate::core::error::ApplicationError;
use crate::core::interfaces::{ApplicationMessageTrait, ApplicationPayloadTrait};
use crate::core::payload::ApplicationPayload;
use crate::errors::crypt::{INVALID_PAYLOAD_ENCODING, MALFORMED_MESSAGE, UNKNOWN_SENDER};

use base64::{Engine as _, engine::general_purpose};
use serde::{Deserialize, Serialize};
use std::collections::HashMap;

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct ApplicationMessage {
    pub instance_name: String,
    pub timestamp: u64,
    pub action: String,
    pub changes: Option<String>,
    pub state: Option<String>,
}

impl ApplicationMessage {
    /// Deserialize a message from its JSON byte representation.
    pub fn from_bytes(buf: &[u8]) -> Result<Self, ApplicationError> {
        serde_json::from_slice(buf).map_err(|e| MALFORMED_MESSAGE.with_details(e.to_string()))
    }

    /// Decrypt an incoming payload into a message, looking the sender's public
    /// key up in `sender_pub_keys`.
    pub fn open(
        payload: &ApplicationPayload,
        recipient_priv_b64: &str,
        sender_pub_keys: &HashMap<String, String>,
    ) -> Result<Self, ApplicationError> {
        let recipient_priv = static_secret_from_b64(recipient_priv_b64)?;

        let sender_pub_b64 = sender_pub_keys
            .get(payload.from())
            .ok_or_else(|| UNKNOWN_SENDER.with_details(payload.from().to_string()))?;
        let sender_pub = x25519_pub_from_b64(sender_pub_b64)?;

        let nonce = general_purpose::STANDARD
            .decode(payload.nonce_b64())
            .map_err(|e| INVALID_PAYLOAD_ENCODING.with_details(format!("nonce: {e}")))?;
        let ciphertext = general_purpose::STANDARD
            .decode(payload.ct_b64())
            .map_err(|e| INVALID_PAYLOAD_ENCODING.with_details(format!("ciphertext: {e}")))?;

        let plaintext = decrypt_from_peer(&recipient_priv, &sender_pub, &nonce, &ciphertext)?;

        Self::from_bytes(&plaintext)
    }
}

impl ApplicationMessageTrait for ApplicationMessage {
    fn instance_name(&self) -> &str {
        &self.instance_name
    }

    fn timestamp(&self) -> u64 {
        self.timestamp
    }

    fn action(&self) -> &str {
        &self.action
    }

    fn changes(&self) -> Option<&str> {
        self.changes.as_deref()
    }

    fn state(&self) -> Option<&str> {
        self.state.as_deref()
    }

    fn as_json(&self) -> Result<String, ApplicationError> {
        serde_json::to_string(self).map_err(|e| MALFORMED_MESSAGE.with_details(e.to_string()))
    }

    fn clone_box(&self) -> Box<dyn ApplicationMessageTrait> {
        Box::new(self.clone())
    }

    fn seal(
        &self,
        sender_name: &str,
        recipient_name: &str,
        sender_priv_b64: &str,
        recipient_pub_b64: &str,
    ) -> Result<Vec<u8>, ApplicationError> {
        let sender_priv = static_secret_from_b64(sender_priv_b64)?;
        let recipient_pub = x25519_pub_from_b64(recipient_pub_b64)?;

        let (nonce, ciphertext) =
            encrypt_for_peer(&sender_priv, &recipient_pub, self.as_json()?.as_bytes())?;

        ApplicationPayload::new(
            sender_name.to_string(),
            recipient_name.to_string(),
            general_purpose::STANDARD.encode(nonce),
            general_purpose::STANDARD.encode(ciphertext),
        )
        .as_bytes()
    }
}
