use rocket::fairing::AdHoc;

pub mod log_env;

pub fn log_fairing_running(f: &str) {
  info!("{:#?} Fairing Running...", f);
}

pub fn run() -> AdHoc {
  AdHoc::on_ignite("Donation Redefiner", move |rocket| async move {
    log_fairing_running("Log Config");

    log_env::log_config();

    rocket
  })
}
