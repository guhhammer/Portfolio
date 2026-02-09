#[derive(Debug, Clone, serde::Serialize, serde::Deserialize)]
pub struct Message<T = String> {
  pub property: String,
  pub value: String,
  pub status: String,
  pub details: Option<T>,
}

impl Message {
  pub fn new(property: &str, value: &str, status: &str, details: Option<String>) -> Self {
    Self {
      property: property.to_string(),
      value: value.to_string(),
      status: status.to_string(),
      details,
    }
  }
}
