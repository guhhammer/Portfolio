use crate::datastructures::user::User;
use mongodb::bson::{Document, to_document};

pub fn user_to_doc(user: &User) -> Document {
  to_document(user).expect("Failed to convert user to document")
}
