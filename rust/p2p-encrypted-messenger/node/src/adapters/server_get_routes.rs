//! GET requests against the key server.

use crate::core::error::ApplicationError;
use crate::errors::server::{FETCH_PRIV_KEY_FAILED, FETCH_PUB_KEYS_FAILED};
use reqwest::blocking::Client;
use std::collections::HashMap;

/// Shared secret used to fingerprint the machine's MAC address when naming
/// this instance. In a production deployment this would be provisioned by the
/// key server and rotated regularly.
pub fn get_super_secret(_server_url: &str) -> &'static [u8] {
    b"some_super_secret_key"
}

/// Fetch this instance's X25519 private key from the key server.
pub fn priv_key(instance_name: &str, server_url: &str) -> Result<String, ApplicationError> {
    let resp = Client::new()
        .get(format!("{server_url}/get_priv_key"))
        .query(&[("name", instance_name)])
        .send()
        .map_err(|e| FETCH_PRIV_KEY_FAILED.with_details(e.to_string()))?;

    if !resp.status().is_success() {
        return Err(FETCH_PRIV_KEY_FAILED.with_details(format!("status {}", resp.status())));
    }

    resp.text()
        .map_err(|e| FETCH_PRIV_KEY_FAILED.with_details(e.to_string()))
}

/// Fetch the public keys of every registered instance.
pub fn fetch_pub_keys(server_url: &str) -> Result<HashMap<String, String>, ApplicationError> {
    let resp = Client::new()
        .get(format!("{server_url}/get_pub_keys"))
        .send()
        .map_err(|e| FETCH_PUB_KEYS_FAILED.with_details(e.to_string()))?;

    if !resp.status().is_success() {
        return Err(FETCH_PUB_KEYS_FAILED.with_details(format!("status {}", resp.status())));
    }

    resp.json()
        .map_err(|e| FETCH_PUB_KEYS_FAILED.with_details(e.to_string()))
}
