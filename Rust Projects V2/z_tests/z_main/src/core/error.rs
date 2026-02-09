use crate::core::interfaces::ApplicationErrorTrait;
use serde::Serialize;
use std::fmt::{self, Debug, Display};

#[derive(Debug, Clone, Serialize)]
pub struct ApplicationError<T, U> {
    pub error_code: &'static str,
    pub error_info: &'static str,
    pub error_details: Option<T>,
    pub extra: Option<U>,
}

impl<T, U> ApplicationError<T, U> {
    pub fn new(
        error_code: &'static str,
        error_info: &'static str,
        error_details: Option<T>,
        extra: Option<U>,
    ) -> Self {
        Self {
            error_code,
            error_info,
            error_details,
            extra,
        }
    }

    pub fn with_details(&mut self, error_details: T) -> &mut Self {
        self.error_details = Some(error_details);

        self
    }

    #[allow(dead_code)]
    pub fn with_extra(&mut self, extra: U) -> &mut Self {
        self.extra = Some(extra);

        self
    }

    pub fn throw(&mut self) -> Self
    where
        Self: Clone,
    {
        self.clone()
    }
}

impl<T: Display, U: Display> Display for ApplicationError<T, U> {
    /// BASIC FORMATTING: [CODE] Info - Details (Extra)
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

impl<T: Debug + Display, U: Debug + Display> ApplicationErrorTrait for ApplicationError<T, U> {
    fn get_error_code(&self) -> String {
        self.error_code.to_string()
    }
    fn get_error_info(&self) -> String {
        self.error_info.to_string()
    }
    fn get_error_details(&self) -> String {
        self.error_details
            .as_ref()
            .map(|d| d.to_string())
            .unwrap_or("".to_string())
    }
    fn get_extra(&self) -> String {
        self.extra
            .as_ref()
            .map(|d| d.to_string())
            .unwrap_or("".to_string())
    }
    fn to_string(&self) -> String {
        let mut ret = format!("[{}] {}", self.error_code, self.error_info);
        if let Some(ref details) = self.error_details {
            ret += &format!(" - {details}");
        }
        if let Some(ref extra) = self.extra {
            ret += &format!(" ({extra})");
        }

        ret
    }
}
