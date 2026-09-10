#[derive(Debug, Clone, PartialEq, Eq)]
pub enum Role {
  //
  // - Relevance
  User,
  SimpleUser,
  Salesman, // SimpleUser := User
  //
  //
  LocalManager, // LocalManager or Company Manager
  //
  //
  SpecialManager,
  Owner, // Business Owner
  //
  //
  Dev,
  Hammer, // ME OWNER OF EVERYTHING.
          // + Relevance
          //
}

impl Role {
  pub fn from_str(s: &str) -> Self {
    match s.to_lowercase().as_str() {
      "hammer" => Role::Hammer,
      "dev" => Role::Dev,

      "owner" => Role::Owner,
      "specialmanager" => Role::SpecialManager,
      "localmanager" => Role::LocalManager,

      "salesman" => Role::Salesman,
      "simpleuser" => Role::SimpleUser,
      "user" => Role::User,

      _ => Role::User,
    }
  }
}
