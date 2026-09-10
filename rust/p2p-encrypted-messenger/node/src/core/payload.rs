//! The encrypted wire format exchanged between peers.

use crate::core::error::ApplicationError;
use crate::core::interfaces::ApplicationPayloadTrait;
use crate::errors::crypt::MALFORMED_MESSAGE;
use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct ApplicationPayload {
    from: String,
    to: String,
    nonce_b64: String,
    ct_b64: String,
}

impl ApplicationPayload {
    pub fn new(from: String, to: String, nonce_b64: String, ct_b64: String) -> Self {
        Self {
            from,
            to,
            nonce_b64,
            ct_b64,
        }
    }

    pub fn from(&self) -> &str {
        &self.from
    }

    pub fn nonce_b64(&self) -> &str {
        &self.nonce_b64
    }

    pub fn ct_b64(&self) -> &str {
        &self.ct_b64
    }
}

impl ApplicationPayloadTrait for ApplicationPayload {
    fn as_bytes(&self) -> Result<Vec<u8>, ApplicationError> {
        serde_json::to_vec(self).map_err(|e| MALFORMED_MESSAGE.with_details(e.to_string()))
    }
}
