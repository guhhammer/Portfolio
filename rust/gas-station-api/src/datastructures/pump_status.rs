use std::fmt;

#[derive(Debug, Copy, Clone, PartialEq, serde::Serialize, serde::Deserialize)]
pub enum PumpStatus {
  Active,
  Pumping,
  Unactive,
}

impl PumpStatus {
  pub fn api_return_format(&self) -> String {
    match self {
      PumpStatus::Active | PumpStatus::Pumping | PumpStatus::Unactive => format!("{self:?}"),
    }
  }
}

impl fmt::Display for PumpStatus {
  fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
    match self {
      PumpStatus::Active => write!(f, "\x1b[32mActive\x1b[0m"),
      PumpStatus::Pumping => write!(f, "\x1b[33mPumping\x1b[0m"),
      PumpStatus::Unactive => write!(f, "\x1b[31mUnactive\x1b[0m"),
    }
  }
}
