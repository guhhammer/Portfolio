# MongoDB Installation and Usage on Linux

## Installing MongoDB on Linux

### 1. Import the MongoDB GPG Key

```bash
wget -qO - https://www.mongodb.org/static/pgp/server-7.0.asc | sudo gpg --dearmor -o /usr/share/keyrings/mongodb-keyring.gpg
```

This downloads and adds MongoDB's official GPG key.

### 2. Add MongoDB Repository

```bash
echo "deb [signed-by=/usr/share/keyrings/mongodb-keyring.gpg] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list
```

Replace `jammy` with your Ubuntu version (e.g., `focal` for 20.04).

### 3. Update Package List

```bash
sudo apt update
```

### 4. Install MongoDB

```bash
sudo apt install -y mongodb-org
```

This installs MongoDB along with necessary utilities.

### 5. Start MongoDB

```bash
sudo systemctl start mongod
```

### 6. Enable MongoDB to Start on Boot

```bash
sudo systemctl enable mongod
```

## Managing MongoDB Service

### Start MongoDB

```bash
sudo systemctl start mongod
```

### Check MongoDB Status

```bash
sudo systemctl status mongod
```

### Stop MongoDB

```bash
sudo systemctl stop mongod
```

### Restart MongoDB

```bash
sudo systemctl restart mongod
```

## Using MongoDB

### Open MongoDB Shell

```bash
mongosh
```

### Show Databases

```js
show dbs
```

### Use a Database

```js
use mydatabase
```

If `mydatabase` does not exist, it will be created when data is inserted.

### Show Collections

```js
show collections
```

### Insert a Document

```js
db.users.insertOne({ name: "Alice", age: 25 })
```

### Insert Multiple Documents

```js
db.users.insertMany([
  { name: "Bob", age: 30 },
  { name: "Charlie", age: 28 }
])
```

### Find Documents

```js
db.users.find().pretty()
```

### Find Specific Document

```js
db.users.findOne({ name: "Alice" })
```

### Update a Document

```js
db.users.updateOne({ name: "Alice" }, { $set: { age: 26 } })
```

### Delete a Document

```js
db.users.deleteOne({ name: "Charlie" })
```

### Delete a Collection

```js
db.users.drop()
```

### Delete a Database

```js
db.dropDatabase()
```

## Populating Database with Migration Files

Your migration files are stored as `.txt` files and contain JSON data and image references. You can use a Rust script to read these files and insert their contents into MongoDB.

### Example Migration File (`data.txt`)

```json
{
  "name": "Project A",
  "description": "A sample project",
  "image": "image1.png"
}
```

### Rust Script to Read and Insert Data

```rust
use mongodb::{bson::doc, Client};
use std::fs;

#[tokio::main]
async fn main() -> mongodb::error::Result<()> {
    let client = Client::with_uri_str("mongodb://localhost:27017").await?;
    let db = client.database("mydatabase");
    let collection = db.collection("projects");
    
    let data = fs::read_to_string("data.txt")?;
    let json_data: serde_json::Value = serde_json::from_str(&data)?;
    
    collection.insert_one(json_data, None).await?;
    println!("Data inserted successfully");
    
    Ok(())
}
```

This script reads `data.txt`, parses its JSON content, and inserts it into MongoDB.

Now, your database is set up and populated using migration files!
