//! POST requests against the key server.

use crate::core::error::ApplicationError;
use crate::errors::server::REGISTER_FAILED;
use reqwest::blocking::Client;

/// Register this instance with the key server, which generates its keypairs.
pub fn register(instance_name: &str, server_url: &str) -> Result<(), ApplicationError> {
    let resp = Client::new()
        .post(format!("{server_url}/register"))
        .json(&serde_json::json!({ "name": instance_name }))
        .send()
        .map_err(|e| REGISTER_FAILED.with_details(e.to_string()))?;

    if !resp.status().is_success() {
        return Err(REGISTER_FAILED.with_details(format!("status {}", resp.status())));
    }

    Ok(())
}
