use chrono::Local;
use fern::colors::{Color, ColoredLevelConfig};
use std::fs;

pub fn set() -> Result<(), fern::InitError> {
  fs::create_dir_all("log").unwrap();

  fs::create_dir_all("log/display").unwrap();
  fs::create_dir_all("log/fuel-catalog").unwrap();
  fs::create_dir_all("log/fuel-supply").unwrap();
  fs::create_dir_all("log/gaspump-schema").unwrap();
  fs::create_dir_all("log/grid-schema").unwrap();
  fs::create_dir_all("log/incoming-simulation").unwrap();
  fs::create_dir_all("log/price-maker").unwrap();

  let colors = ColoredLevelConfig::new()
    .trace(Color::Cyan)
    .debug(Color::Blue)
    .info(Color::Green)
    .warn(Color::Yellow)
    .error(Color::Red);

  fern::Dispatch::new()
    .format(move |out, message, record| {
      out.finish(format_args!(
        "[{}][{}][{}] {}",
        Local::now().format("%Y-%m-%d %H:%M:%S"),
        colors.color(record.level()),
        record.target(),
        message
      ))
    })
    .level(log::LevelFilter::Trace) // capture all logs
    .chain(std::io::stdout()) // still print to console
    .chain(fern::log_file("output.log").unwrap()) // save to file
    .apply()
    .unwrap();

  log::debug!("#STARTING:MAIN");

  Ok(())
}
