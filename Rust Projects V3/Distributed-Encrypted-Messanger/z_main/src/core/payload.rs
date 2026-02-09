use crate::core::interfaces::{ApplicationCommunicationLayerTrait, ApplicationPayloadTrait};
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

    pub fn get_from(&self) -> String {
        self.from.clone()
    }

    pub fn get_nonce(&self) -> String {
        self.nonce_b64.clone()
    }

    pub fn get_ct(&self) -> String {
        self.ct_b64.clone()
    }
}

impl ApplicationPayloadTrait for ApplicationPayload {
    fn as_bytes(&self) -> Vec<u8> {
        serde_json::to_vec(self).unwrap()
    }
}

impl ApplicationCommunicationLayerTrait for ApplicationPayload {
    fn produce(&self) -> Vec<u8> {
        serde_json::to_vec(self).unwrap()
    }

    fn consume(&self) -> Box<dyn ApplicationPayloadTrait> {
        Box::new(ApplicationPayload::new(
            self.from.clone(),
            self.to.clone(),
            self.nonce_b64.clone(),
            self.ct_b64.clone(),
        ))
    }
}
