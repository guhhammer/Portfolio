//! Turns received messages into application actions.
//!
//! For now every message is just logged; this is the extension point where
//! real message handling plugs in.

use crate::core::interfaces::ApplicationMessageTrait;

pub fn from_message(msg: Box<dyn ApplicationMessageTrait>) {
    match msg.as_json() {
        Ok(info) => println!("interpreter: {info}"),
        Err(e) => eprintln!("interpreter: unreadable message: {e}"),
    }
}
