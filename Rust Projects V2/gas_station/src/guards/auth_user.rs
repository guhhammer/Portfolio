use crate::datastructures::appstate::Grids;
use crate::datastructures::role::Role;
use crate::datastructures::user_related::Claims;
use jsonwebtoken::{Algorithm, DecodingKey, Validation, decode};
use mongodb::bson::doc;
use rocket::http::Status;
use rocket::request::{FromRequest, Outcome, Request};

#[derive(Debug)]
pub struct AuthUser {
  #[allow(dead_code)]
  pub username: String,
  pub roles: Vec<Role>,
}

#[rocket::async_trait]
impl<'r> FromRequest<'r> for AuthUser {
  type Error = Status;

  async fn from_request(req: &'r Request<'_>) -> Outcome<Self, Self::Error> {
    // 1️⃣ Get the Authorization header
    let token = match req.headers().get_one("Authorization") {
      Some(h) => h.strip_prefix("Bearer "),
      None => None,
    };

    let token = match token {
      Some(t) => t,
      None => return Outcome::Error((Status::Unauthorized, Status::Unauthorized)),
    };

    // 2️⃣ Decode JWT
    let key = DecodingKey::from_secret("supersecret".as_ref());
    let validation = Validation::new(Algorithm::HS256);

    let gconn = req.rocket().state::<Grids>().unwrap();

    let data = match decode::<Claims>(token, &key, &validation) {
      Ok(data) => data,
      Err(_) => return Outcome::Error((Status::Unauthorized, Status::Unauthorized)),
    };

    let user_doc_result = gconn
      .users_db
      .find_one(doc! {"username": &data.claims.sub})
      .await;

    let user_doc = match user_doc_result {
      Ok(Some(doc)) => doc,
      Ok(None) => return Outcome::Error((Status::NotFound, Status::NotFound)),
      Err(_) => {
        return Outcome::Error((Status::InternalServerError, Status::InternalServerError));
      }
    };

    let roles: Vec<Role> = user_doc
      .get_array("roles")
      .unwrap_or(&vec![])
      .iter()
      .filter_map(|r| r.as_str())
      .map(Role::from_str)
      .collect();

    Outcome::Success(AuthUser {
      username: data.claims.sub,
      roles,
    })
  }
}
