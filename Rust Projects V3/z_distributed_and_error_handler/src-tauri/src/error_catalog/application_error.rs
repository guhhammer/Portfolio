use std::{error::Error, fmt::{self, Debug, Display}};
use serde::Serialize;

#[derive(Debug, Clone, Serialize)]
pub struct ApplicationError<T, U> {

    pub error_code: &'static str,
    pub error_info: &'static str,
    pub error_details: Option<T>,
    pub extra: Option<U>,

}

impl<T, U> ApplicationError<T, U> {

    #[allow(dead_code)]
    pub fn new(error_code: &'static str, error_info: &'static str, error_details: Option<T>, extra: Option<U>) -> Self {
        Self {
            error_code, error_info, error_details, extra,
        }
    }

    #[allow(dead_code)]
    pub fn with_details(&mut self, error_details: T) -> &mut Self {

        self.error_details = Some(error_details);

        self
  
    }

    #[allow(dead_code)]
    pub fn with_extra(&mut self, extra: U) -> &mut Self {

        self.extra = Some(extra);
        
        self
    }

}

impl<T: Display, U: Display> Display for ApplicationError<T, U> {
    /// BASIC FORMATTING: [CODE] Info - Details (Extra)
    #[allow(dead_code)]
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "[{}] {}", self.error_code, self.error_info)?;
        if let Some(ref details) = self.error_details {
            write!(f, " - {details}")?;
        }
        if let Some(ref extra) = self.extra {
            write!(f, " ({extra})")?;
        }
        Ok(())
    }
}

impl<T: Debug + Display, U: Debug + Display> Error for ApplicationError<T, U> {}
