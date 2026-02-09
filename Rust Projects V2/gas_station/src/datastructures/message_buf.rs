use chrono::Local;
use std::fs::OpenOptions;
use std::io::Write;

#[derive(Debug, Clone, serde::Serialize, serde::Deserialize)]
pub struct MessageBuffer {
  msg: Vec<String>,
}

impl MessageBuffer {
  pub fn new() -> Self {
    Self { msg: Vec::new() }
  }

  pub fn push(&mut self, content: &str) {
    self.msg.push(content.to_string());
  }

  pub fn print(&self) {
    self.msg.iter().for_each(|x| print!("{x}"));
  }

  pub fn output_as_string(&self) -> String {
    self.msg.join("")
  }

  pub fn log(&self, filepath: &str, filename: &str) {
    let file_name = format!(
      "{filepath}{filename}_{}.log",
      Local::now().format("%Y%m%d-%H%M%S")
    );

    let mut file = OpenOptions::new()
      .create(true)
      .append(true)
      .open(&file_name)
      .expect("Cannot open log file");

    for m in &self.msg {
      writeln!(file, "{m}").expect("Failed to write to log file");
    }
  }

  pub fn ref_file(&self, filepath: &str, filename: &str) -> String {
    format!(
      "{filepath}{filename}_{}.log",
      Local::now().format("%Y%m%d-%H%M%S")
    )
  }

  pub fn log_step(&mut self, filename: &str, msg: &str) {
    let mut file = OpenOptions::new()
      .create(true)
      .append(true)
      .open(filename)
      .expect("Cannot open log file");

    self.push(msg);

    writeln!(file, "{msg}").expect("Failed to write to log file");
  }
}
