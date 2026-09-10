use mongodb::{Client, bson::doc, options::ClientOptions};
use futures::TryStreamExt;

#[tokio::main]
async fn main() -> mongodb::error::Result<()> {
    // Connect to MongoDB
    let client_uri = "mongodb://localhost:27017";
    let mut client_options = ClientOptions::parse(client_uri).await?;
    client_options.app_name = Some("rust-test".to_string());
    let client = Client::with_options(client_options)?;

    // Access database and collection
    let database = client.database("my-test");
    let collection = database.collection("users");

    // Insert a document
    let new_user = doc! {"name": "Jake", "age": 25 };
    collection.insert_one(new_user, None).await?;
    println!("Inserted user: Jake");

    // Find documents
    let mut cursor = collection.find(None, None).await?;
    println!("Current users in collection:");
    while let Ok(Some(doc)) = cursor.try_next().await {
        println!("{:?}", doc); // <-- no '?' needed here
    }

    // Update a document
    collection
        .update_one(doc! {"name": "Jake"}, doc! {"$set": { "age": 26} }, None)
        .await?;
    println!("Updated Jake's age to 26");

    // Delete a document
    collection.delete_one(doc! {"name": "Jake"}, None).await?;
    println!("Deleted user: Jake");

    Ok(())
}
