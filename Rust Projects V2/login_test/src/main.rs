use argon2::{
    Argon2, PasswordHasher,
    password_hash::{SaltString, rand_core::OsRng},
};
use mongodb::{
    Client, IndexModel,
    bson::{DateTime, Document, doc, to_document},
};
use serde::{Deserialize, Serialize};
use mongodb::Collection;

#[derive(Debug, Serialize, Deserialize)]
struct User {
    username: String,
    email: String,
    password_hash: String,
    roles: Vec<String>,
    created_at: DateTime,
    last_login: DateTime,
}

fn user_to_doc(user: &User) -> Document {
    to_document(user).expect("Failed to convert user to document")
}

fn string_to_hash(s: String) -> String {
    let salt = SaltString::generate(&mut OsRng);
    let argon2 = Argon2::default();

    argon2
        .hash_password(s.as_bytes(), &salt)
        .expect("Failed to hash string")
        .to_string()
}
use mongodb::error::Error;

async fn create_user(
    users: &Collection<Document>,
    username: String,
    email: String,
    password: String,
    roles: Vec<String>,
) -> Result<(), Error>  {

   // 1. Check if username or email exists
    if check_user_exists(users, Some(&username), Some(&email)).await? {
        return Err(Error::from(std::io::Error::new(
            std::io::ErrorKind::Other,
            "Username or email is already in use",
        )));
    }

    // 2. Hash password
    let pwd = string_to_hash(password);

    // 3. Create user struct
    let user = User {
        username: username.to_string(),
        email: email.to_string(),
        password_hash: pwd,
        roles,
        created_at: DateTime::now(),
        last_login: DateTime::now(),
    };

    // 4. Convert to Document and insert
    let doc = user_to_doc(&user);

    users.insert_one(doc, None).await?;
    println!("✅ User inserted!");
    Ok(())
}


async fn check_user_exists(
    users: &Collection<Document>,
    username: Option<&str>,
    email: Option<&str>,
) -> mongodb::error::Result<bool> {
    if let Some(u) = username {
        if users.find_one(doc! { "username": u }, None).await?.is_some() {
            return Ok(true)
        }
    }

    if let Some(e) = email {
        if users.find_one(doc! { "email": e }, None).await?.is_some() {
            return Ok(true)
        }
    }

    Ok(false)
}
use argon2::{ PasswordHash, PasswordVerifier};

async fn login(users: &Collection<mongodb::bson::Document>, username: &str, password: &str) -> bool {
    // 1. Fetch user by username
    let user_doc = match users.find_one(doc! { "username": username }, None).await {
        Ok(Some(doc)) => doc,
        _ => return false, // user not found or error
    };

    // 2. Get stored hash from the document
    let stored_hash = match user_doc.get_str("password_hash") {
        Ok(h) => h,
        Err(_) => return false,
    };

    // 3. Verify password
    let parsed_hash = match PasswordHash::new(stored_hash) {
        Ok(ph) => ph,
        Err(_) => return false,
    };

    Argon2::default()
        .verify_password(password.as_bytes(), &parsed_hash)
        .is_ok()
}
#[tokio::main]
async fn main() -> mongodb::error::Result<()> {
    // 1. Connect
    let client = Client::with_uri_str("mongodb://localhost:27017").await?;
    let db = client.database("login-test");
    let users = db.collection::<Document>("users");

    // 2. Make indexed fields

    // unique index on username
    users
        .create_index(
            IndexModel::builder()
                .keys(doc! { "username": 1 })
                .options(Some(
                    mongodb::options::IndexOptions::builder()
                        .unique(true)
                        .build(),
                ))
                .build(),
            None,
        )
        .await?;

    // unique index on email
    users
        .create_index(
            IndexModel::builder()
                .keys(doc! { "email": 1 })
                .options(Some(
                    mongodb::options::IndexOptions::builder()
                        .unique(true)
                        .build(),
                ))
                .build(),
            None,
        )
        .await?;

    match create_user(&users, "johndoess".to_string(), "john@example.comdd".to_string(), "l".to_string(), vec!["user".to_string()]).await {
        Ok(_) => println!("User created!"),
        Err(e) => println!("Error: {:?}", e),
    }

    let x =  login(&users, "johndoess", "l").await;

    println!("{x}");
    Ok(())
}
