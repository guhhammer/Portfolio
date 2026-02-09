#![allow(dead_code)]

use crate::core::interfaces::ApplicationMessageTrait;
use crate::core::interfaces::ApplicationPayloadTrait;
use crate::core::payload::ApplicationPayload;

use crate::core::crypt::decrypt_from_peer;
use crate::core::crypt::encrypt_for_peer;
use crate::core::crypt::static_secret_from_b64;
use crate::core::crypt::x25519_pub_from_b64;

use base64::{Engine as _, engine::general_purpose};
use serde::{Deserialize, Serialize};
use std::collections::HashMap;
use std::io::Read;
use std::net::TcpStream;

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct ApplicationMessage {
    pub instance_name: String,
    pub timestamp: u64,
    pub action: String,
    pub changes: Option<String>,
    pub state: Option<String>,
}

impl ApplicationMessage {
    pub fn empty() -> Self {
        Self {
            instance_name: "".to_string(),
            timestamp: 0u64,
            action: "".to_string(),
            changes: None,
            state: None,
        }
    }

    pub fn bytes_to_message(buf: Vec<u8>) -> ApplicationMessage {
        serde_json::from_slice(&buf).expect("Failed to parse payload")
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

    fn as_json(&self) -> String {
        serde_json::to_string(self).unwrap()
    }

    fn clone_box(&self) -> Box<dyn ApplicationMessageTrait> {
        Box::new(self.clone())
    }

    fn as_payload(
        &self,
        name: &str,
        peer_name: &str,
        sender_priv: &str,
        recipient_pub: &str,
    ) -> Box<dyn ApplicationPayloadTrait> {
        let (nonce, ciphertext) = self.encrypt(sender_priv, recipient_pub);

        let nonce_b64: String = general_purpose::STANDARD.encode(&nonce);
        let ct_b64: String = general_purpose::STANDARD.encode(&ciphertext);

        Box::new(ApplicationPayload::new(
            name.to_string(),
            peer_name.to_string(),
            nonce_b64,
            ct_b64,
        ))
    }

    fn convert_msg(&self, msg: serde_json::Value) -> ApplicationPayload {
        let from = msg["from"].to_string();
        let to = msg["to"].to_string();

        let nonce = msg["nonce_b64"].to_string();
        let ct = msg["ct_b64"].to_string();

        ApplicationPayload::new(from, to, nonce, ct)
    }

    fn encrypt(&self, sender_priv: &str, recipient_pub: &str) -> (Vec<u8>, Vec<u8>) {
        let s_priv = static_secret_from_b64(sender_priv);
        let recv_pub = x25519_pub_from_b64(recipient_pub);
        let msg = self.as_json();

        let (nonce, ciphertext) = encrypt_for_peer(&s_priv, &recv_pub, msg.as_bytes());

        (nonce, ciphertext)
    }

    fn decrypt(
        &self,
        payload: ApplicationPayload,
        recipient_priv: &str,
        sender_pub_hashmap: HashMap<String, String>,
    ) -> Vec<u8> {
        let recp_priv = static_secret_from_b64(recipient_priv);

        let match_s_pub = &sender_pub_hashmap[&payload.get_from()];

        let s_pub = x25519_pub_from_b64(match_s_pub);

        let nonce_bytes = general_purpose::STANDARD
            .decode(payload.get_nonce())
            .expect("invalid base64 in nonce");

        let ct_bytes = general_purpose::STANDARD
            .decode(payload.get_ct())
            .expect("invalid base64 in ciphertext");

        decrypt_from_peer(&recp_priv, &s_pub, &nonce_bytes, &ct_bytes)
    }

    fn send(
        &self,
        sender_name: &str,
        recipient_name: &str,
        sender_priv: &str,
        recipient_pub: &str,
    ) -> Vec<u8> {
        self.as_payload(sender_name, recipient_name, sender_priv, recipient_pub)
            .as_bytes()
    }

    fn receive(
        &self,
        stream: &mut TcpStream,
        recipient_priv: &str,
        sender_pub_hashmap: HashMap<String, String>,
    ) -> ApplicationMessage {
        let mut buf = vec![0u8; 4096];
        let n = stream.read(&mut buf).unwrap();
        let data = &buf[..n];
        let msg: serde_json::Value = serde_json::from_slice(data).unwrap();

        let app_msg_bytes = self.decrypt(self.convert_msg(msg), recipient_priv, sender_pub_hashmap);

        ApplicationMessage::bytes_to_message(app_msg_bytes)
    }
}

impl Clone for Box<dyn ApplicationMessageTrait> {
    fn clone(&self) -> Box<dyn ApplicationMessageTrait> {
        self.clone_box()
    }
}
