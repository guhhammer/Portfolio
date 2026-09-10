//! LAN discovery and transport errors.

use crate::core::error::ApplicationError;

pub const BROADCAST_PRESENCE_ERROR: ApplicationError = ApplicationError::new(
    "LAN001",
    "Broadcast send failure",
    Some("Check that the socket is bound to the correct interface and broadcast is enabled"),
);

pub const TCP_LISTENER_ACCEPT_ERROR: ApplicationError = ApplicationError::new(
    "LAN002",
    "TCP listener accept failed",
    Some("The socket may have been closed, or the OS hit its file-descriptor limit"),
);

pub const TCP_SEND_MESSAGE_ERROR: ApplicationError = ApplicationError::new(
    "LAN003",
    "TCP message send failed",
    Some("The remote peer may have disconnected or be unreachable"),
);

pub const TCP_STREAM_READ_ERROR: ApplicationError = ApplicationError::new(
    "LAN004",
    "TCP stream read failed",
    Some("The remote peer may have closed the connection mid-message"),
);

pub const UDP_SOCKET_CLONE_ERROR: ApplicationError = ApplicationError::new(
    "LAN005",
    "UDP socket clone failed",
    Some("The OS may have run out of file descriptors"),
);

pub const UDP_SOCKET_CREATE_ERROR: ApplicationError = ApplicationError::new(
    "LAN006",
    "UDP socket creation failed",
    Some("Check whether the port is already in use"),
);
