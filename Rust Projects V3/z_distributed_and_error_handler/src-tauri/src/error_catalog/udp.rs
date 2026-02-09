
use crate::error_catalog::application_error::ApplicationError;

#[allow(dead_code)]
pub static BROADCAST_ERROR_SEND: ApplicationError<&str, &str> = ApplicationError {
    error_code: "UDP001",
    error_info: "Broadcast send failure",
    error_details: None,
    extra: None,
};
