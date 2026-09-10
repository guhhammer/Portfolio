# MongoDB cheat sheet

Installing MongoDB on Debian, managing the service, and the shell commands I reach for most.


## Installation

- https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-debian/#std-label-install-mdb-community-debian


## Start and Enable MongoDB Service

sudo systemctl start mongod
sudo systemctl enable mongod

Check status:
sudo systemctl status mongod

## MongoDB CLI Basics

### Start the shell
mongosh

### Basic commands

Show databases:
show dbs

Create/use a database:
use myDatabase

Show collections (tables):
show collections

Insert a document (record):
db.users.insertOne({ name: "Jake", age: 25 })

Find documents:
db.users.find()

Update a document:
db.users.updateOne({ name: "Jake" }, { $set: { age: 26 } })

Delete a document:
db.users.deleteOne({ name: "Jake" })

Exit the shell:
exit

## Optional: Manage MongoDB via systemctl

Stop MongoDB:
sudo systemctl stop mongod

Restart MongoDB:
sudo systemctl restart mongod

Check logs:
sudo journalctl -u mongod -f
