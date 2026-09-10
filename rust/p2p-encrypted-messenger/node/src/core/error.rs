//! The application's error type.
//!
//! Errors are declared as constants in the `errors` module (one file per
//! domain) and enriched with runtime context via [`ApplicationError::with_details`].

use serde::Serialize;
use std::fmt::{self, Display};

#[derive(Debug, Clone, Serialize)]
pub struct ApplicationError {
    /// Stable machine-readable code, e.g. "LAN003".
    pub code: &'static str,
    /// Short human-readable summary of what went wrong.
    pub summary: &'static str,
    /// Runtime context attached at the failure site.
    pub details: Option<String>,
    /// Static troubleshooting hint.
    pub hint: Option<&'static str>,
}

impl ApplicationError {
    pub const fn new(code: &'static str, summary: &'static str, hint: Option<&'static str>) -> Self {
        Self {
            code,
            summary,
            details: None,
            hint,
        }
    }

    /// Attach runtime context (an underlying error, an address, ...) to a copy
    /// of this error.
    pub fn with_details(mut self, details: impl Into<String>) -> Self {
        self.details = Some(details.into());
        self
    }
}

impl Display for ApplicationError {
    /// Format: `[CODE] Summary - details (hint)`
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "[{}] {}", self.code, self.summary)?;
        if let Some(ref details) = self.details {
            write!(f, " - {details}")?;
        }
        if let Some(hint) = self.hint {
            write!(f, " ({hint})")?;
        }
        Ok(())
    }
}

impl std::error::Error for ApplicationError {}
