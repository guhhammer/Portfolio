use crate::core::interfaces::ApplicationMessageTrait;

pub fn from_message(msg: Box<dyn ApplicationMessageTrait>) {
    let info: String = msg.as_json();

    println!("interpreter: {info}");
}
