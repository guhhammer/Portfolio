//! Synchronizes the global state (instance name and keys) with the key server.

use crate::adapters::server_get_routes::{get_super_secret, priv_key};
use crate::adapters::server_post_routes::register;
use crate::config::SERVER_URL;
use crate::core::error::ApplicationError;
use crate::core::instance::naming;

/// Register with the key server and refresh this node's private key.
///
/// `pick_name` is true on the first run only: the instance keeps its name for
/// the lifetime of the process.
pub fn state_tracker_run(pick_name: bool) -> Result<(), ApplicationError> {
    if pick_name {
        let secret_key = get_super_secret(SERVER_URL);
        let mut name = crate::INSTANCE_NAME.write().unwrap();
        *name = naming(secret_key);
        println!("#APPLICATION-ID: {name}");
    }

    let name = crate::INSTANCE_NAME.read().unwrap().clone();

    register(&name, SERVER_URL)?;

    let key = priv_key(&name, SERVER_URL)?;
    *crate::PRIV_KEY.write().unwrap() = key;

    Ok(())
}
