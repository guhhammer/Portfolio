use crate::datastructures::role::Role;
use crate::guards::auth_user::AuthUser;
use rocket::http::Status;
use rocket::response::status;

const ACCESS_LEVEL: [Role; 8] = [
  // Access Level 0:
  Role::User,
  Role::SimpleUser,
  Role::Salesman,
  // Access Level 1:
  Role::LocalManager,
  // Access Level 2:
  Role::SpecialManager,
  Role::Owner,
  // Access Level 3:
  Role::Dev,
  // Access Level 4:
  Role::Hammer,
];

pub fn require_any_role(
  user: &AuthUser,
  roles: &[Role],
  least: &Role,
) -> Result<(), status::Custom<String>> {
  if roles.iter().any(|r| user.roles.contains(r)) {
    Ok(())
  } else {
    Err(status::Custom(
      Status::Forbidden,
      format!("Unauthorized for {user:?}. (needs to be: {least:?})"),
    ))
  }
}

pub fn access_level_0(
  user: &AuthUser,
  name_id: &str,
  local_grid: &str,
) -> Result<(), status::Custom<String>> {
  if name_id != local_grid && !&ACCESS_LEVEL[3..].iter().any(|r| user.roles.contains(r)) {
    return Err(status::Custom(
      Status::Forbidden,
      format!(
        "Unauthorized for {user:?}. (needs to be: {:?})",
        &ACCESS_LEVEL[3]
      ),
    ));
  }

  require_any_role(user, &ACCESS_LEVEL, &ACCESS_LEVEL[0])
}

pub fn access_level_1(user: &AuthUser) -> Result<(), status::Custom<String>> {
  require_any_role(user, &ACCESS_LEVEL[3..], &ACCESS_LEVEL[3])
}

pub fn access_level_2(user: &AuthUser) -> Result<(), status::Custom<String>> {
  require_any_role(user, &ACCESS_LEVEL[4..], &ACCESS_LEVEL[4])
}
