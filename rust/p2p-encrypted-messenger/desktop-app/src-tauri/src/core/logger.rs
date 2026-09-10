use chrono::Local;
use std::env;
use std::fs::OpenOptions;
use std::io::Write;

pub fn log(mode: &str, msg: &str) {
    // Get executable directory
    let binding = env::current_exe().unwrap();
    let exe_dir = binding.parent().unwrap();
    let log_file = exe_dir.join("ApplicationLogs.txt");

    // Open file for append
    let mut file = OpenOptions::new()
        .create(true)
        .append(true)
        .open(log_file)
        .unwrap();

    // Write timestamp + message
    let now = Local::now();
    writeln!(file, "{} {mode} {msg}", now.format("%Y-%m-%d %H:%M:%S")).unwrap();
}

pub fn log_info(msg: &str) {
    log(" [INFO]", msg);
}

#[allow(dead_code)]
pub fn log_warn(msg: &str) {
    log(" [WARN]", msg);
}

pub fn log_error(msg: &str) {
    log("[ERROR]", msg);
}

#[allow(dead_code)]
pub fn log_debug(msg: &str) {
    log("[DEBUG]", msg);
}

#[allow(dead_code)]
pub fn log_trace(msg: &str) {
    log("[TRACE]", msg);
}

pub fn log_start() {
    log("[START]", "#APPLICATION HAS STARTED EXECUTION HERE");
}
