#![allow(dead_code)]
use crate::core::message::ApplicationMessage;
use crate::core::payload::ApplicationPayload;
use std::any::Any;
use std::collections::HashMap;
use std::fmt::{Debug, Display};
use std::net::TcpStream;

pub trait ApplicationErrorTrait: Debug + Display {
    fn get_error_code(&self) -> String;
    fn get_error_info(&self) -> String;
    fn get_error_details(&self) -> String;
    fn get_extra(&self) -> String;
    fn to_string(&self) -> String;
}

pub trait ApplicationPayloadTrait {
    fn as_bytes(&self) -> Vec<u8>;
}

pub trait ApplicationCommunicationLayerTrait {
    /// Prepare payload to send.
    fn produce(&self) -> Vec<u8>;

    /// Prepare payload to read.
    fn consume(&self) -> Box<dyn ApplicationPayloadTrait>;
}

pub trait ApplicationMessageTrait: Send + Sync + Any {
    fn instance_name(&self) -> &str;
    fn timestamp(&self) -> u64;
    fn action(&self) -> &str;
    fn changes(&self) -> Option<&str>;
    fn state(&self) -> Option<&str>;
    fn as_json(&self) -> String;
    fn as_any(&self) -> &dyn Any;
    fn clone_box(&self) -> Box<dyn ApplicationMessageTrait>;
    fn as_payload(
        &self,
        name: &str,
        peer_name: &str,
        sender_priv: &str,
        recipient_pub: &str,
    ) -> Box<dyn ApplicationPayloadTrait>;
    fn convert_msg(&self, msg: serde_json::Value) -> ApplicationPayload;
    fn encrypt(&self, sender_priv: &str, recipient_pub: &str) -> (Vec<u8>, Vec<u8>);
    fn decrypt(
        &self,
        payload: ApplicationPayload,
        recipient_priv: &str,
        sender_pub_hashmap: HashMap<String, String>,
    ) -> Vec<u8>;
    fn send(
        &self,
        sender_name: &str,
        recipient_name: &str,
        sender_priv: &str,
        recipient_pub: &str,
    ) -> Vec<u8>;
    fn receive(
        &self,
        stream: &mut TcpStream,
        recipient_priv: &str,
        sender_pub_hashmap: HashMap<String, String>,
    ) -> ApplicationMessage;
}