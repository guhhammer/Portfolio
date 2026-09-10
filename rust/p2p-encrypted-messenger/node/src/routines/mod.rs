//! Recurring background routines.

use crate::core::error::ApplicationError;
use std::sync::mpsc::{self, Receiver};
use std::thread;

pub mod for_static;

/// Start the background key-sync routine.
///
/// Returns a channel that yields exactly one value: the result of the first
/// sync. Startup blocks on it because the node cannot operate without its
/// keys; later refreshes only log failures and keep the previous keys.
pub fn run() -> Receiver<Result<(), ApplicationError>> {
    let (init_tx, init_rx) = mpsc::channel();

    thread::spawn(move || {
        let first = for_static::state_tracker_run(true);
        let _ = init_tx.send(first);

        loop {
            thread::sleep(crate::config::KEY_REFRESH_INTERVAL);

            if let Err(e) = for_static::state_tracker_run(false) {
                eprintln!("key refresh failed, keeping previous keys: {e}");
            }
        }
    });

    init_rx
}
