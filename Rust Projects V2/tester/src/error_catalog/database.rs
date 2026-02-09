use crate::error_catalog::application_error::ApplicationError;

#[allow(dead_code)]
pub static DB_CONN_ERROR: ApplicationError<String, String> = ApplicationError {
    error_code: "DB001",
    error_info: "Database connection failed",
    error_details: None,
    extra: None,
};

#[allow(dead_code)]
pub static DB_QUERY_ERROR: ApplicationError<String, String> = ApplicationError {
    error_code: "DB002",
    error_info: "Database query failed",
    error_details: None,
    extra: None,
};

#[allow(dead_code)]
pub static DB_QUERY_ERROR_STATIC: ApplicationError<&str, &str> = ApplicationError {
    error_code: "DB002",
    error_info: "Database query failed",
    error_details: Some("static example"),
    extra: Some("just to show extra"),
};
