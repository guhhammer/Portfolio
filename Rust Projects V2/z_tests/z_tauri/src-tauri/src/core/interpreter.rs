use crate::core::interfaces::ApplicationMessageTrait;
use crate::core::logger::log_debug;
use crate::ApplicationMessage;

pub fn from_message(msg: Box<dyn ApplicationMessageTrait>) {
    let info: String = msg.as_json();

    if let Some(real) = msg.as_any().downcast_ref::<ApplicationMessage>() {
        let info: ApplicationMessage = real.clone();

        let state_guard = crate::APP_STATE.read().unwrap();

        // Step 2: lock peerlist's RwLock
        let mut msgvec = state_guard.messages.write().unwrap();

        // Step 4: push
        msgvec.push(info.clone());
    }

    log_debug(&format!("interpreter: {info}"));
}
