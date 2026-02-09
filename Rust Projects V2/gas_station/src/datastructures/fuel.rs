use std::fmt;

#[derive(Debug, Copy, Clone, Hash, Eq, PartialEq, serde::Serialize, serde::Deserialize)]
#[serde(rename_all = "PascalCase")]
pub enum Fuel {
  Gasoline,
  Gasoline70,
  Gasoline80,
  Gasoline85,
  Gasoline95,

  Ethanol,
  Ethanol70,

  Diesel50,
  Diesel500,

  Undefined,
}

impl Fuel {
  pub fn api_return_format(&self) -> String {
    match self {
      Fuel::Gasoline
      | Fuel::Gasoline70
      | Fuel::Gasoline80
      | Fuel::Gasoline85
      | Fuel::Gasoline95
      | Fuel::Ethanol
      | Fuel::Ethanol70
      | Fuel::Diesel50
      | Fuel::Diesel500
      | Fuel::Undefined => format!("{self:?}"),
    }
  }

  pub fn from_api(s: &str) -> Result<Fuel, String> {
    match s {
      "Gasoline" => Ok(Fuel::Gasoline),
      "Gasoline70" => Ok(Fuel::Gasoline70),
      "Gasoline80" => Ok(Fuel::Gasoline80),
      "Gasoline85" => Ok(Fuel::Gasoline85),
      "Gasoline95" => Ok(Fuel::Gasoline95),
      "Ethanol" => Ok(Fuel::Ethanol),
      "Ethanol70" => Ok(Fuel::Ethanol70),
      "Diesel50" => Ok(Fuel::Diesel50),
      "Diesel500" => Ok(Fuel::Diesel500),
      "Undefined" => Ok(Fuel::Undefined),
      _ => Err(format!("Unknown fuel type: {s}")),
    }
  }
}

impl fmt::Display for Fuel {
  fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
    // ANSI escape codes
    const RED_BOLD: &str = "\x1b[1;31m";
    const GREEN_BOLD: &str = "\x1b[1;32m";
    const ORANGE_BOLD: &str = "\x1b[1;38;5;208m"; // 256-color orange
    const GRAY_BOLD: &str = "\x1b[1;90m";
    const RESET: &str = "\x1b[0m";

    let (color, name) = match self {
      Fuel::Gasoline
      | Fuel::Gasoline70
      | Fuel::Gasoline80
      | Fuel::Gasoline85
      | Fuel::Gasoline95 => (RED_BOLD, format!("{self:?}")),
      Fuel::Ethanol | Fuel::Ethanol70 => (GREEN_BOLD, format!("{self:?}")),
      Fuel::Diesel50 | Fuel::Diesel500 => (ORANGE_BOLD, format!("{self:?}")),
      Fuel::Undefined => (GRAY_BOLD, "Undefined".to_string()),
    };

    write!(f, "{color}{name}{RESET}")
  }
}
