use crate::core::error::ApplicationError;
use std::sync::LazyLock;

pub static BROADCAST_PRESENCE_ERROR: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN001",
            "Broadcast send failure",
            Some("Could not send message into UDP socket"),
            Some(
                "Check if the socket is bound to the correct interface, broadcast is enabled, and message is valid UTF-8",
            ),
        )
    });

pub static TCP_LISTENER_ACCEPT_ERROR: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN002",
            "TCP listener accept failed",
            Some("Could not accept an incoming TCP connection"),
            Some(
                "Possible causes:\n\
                 - The socket was closed while listening\n\
                 - File descriptor limit reached\n\
                 - Network stack error in the OS\n\
                 - Resource temporarily unavailable",
            ),
        )
    });

pub static TCP_SEND_MESSAGE_ERROR: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN003",
            "TCP message send failed",
            None,
            Some(
                "Possible causes:\n\
                 - Remote peer disconnected (BrokenPipe/ConnectionReset)\n\
                 - TCP connection refused or unreachable\n\
                 - OS-level resource exhaustion (too many open files)\n\
                 - Interrupted I/O or network issues\n\
                 - Non-blocking stream would block",
            ),
        )
    });

pub static TCP_STREAM_READ_ERROR: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN004",
            "TCP stream read failed",
            None,
            Some(
                "Possible causes include:\n\
                 - The remote peer closed the connection (ConnectionReset/UnexpectedEof)\n\
                 - The connection was aborted locally\n\
                 - The stream is in non-blocking mode and no data is available (WouldBlock)\n\
                 - System-level I/O error such as resource exhaustion or broken pipe",
            ),
        )
    });

pub static UDP_SOCKET_CLONE_ERROR_RECV: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN005",
            "UDP socket clone failed for receiver",
            Some("Could not clone UDP socket for receiving"),
            Some("Maybe the OS ran out of file descriptors or the socket is invalid"),
        )
    });

pub static UDP_SOCKET_CLONE_ERROR_SEND: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN006",
            "UDP socket clone failed",
            Some("Could not clone UDP socket"),
            Some("Maybe the OS ran out of file descriptors or the socket is invalid"),
        )
    });

pub static UDP_SOCKET_CREATE_ERROR: LazyLock<ApplicationError<&'static str, &'static str>> =
    LazyLock::new(|| {
        ApplicationError::new(
            "LAN007",
            "UDP socket creation failed",
            Some("Could not create or bind UDP socket"),
            Some("Check if the port is already in use or if the address is valid"),
        )
    });
