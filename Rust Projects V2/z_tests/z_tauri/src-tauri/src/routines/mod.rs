use std::thread;
use std::time::Duration;

pub mod for_static;

/// All routines should be handled here in a single thread.
pub fn run() {
    let mut thread_remember = false;

    thread::spawn(move || loop {
        crate::routines::for_static::state_tracker_run(!thread_remember);

        if !thread_remember {
            let mut i = crate::INIT_COMPLETE.write().unwrap();
            *i = true;
            thread_remember = true;
        }

        thread::sleep(Duration::from_secs(60));
    });
}
