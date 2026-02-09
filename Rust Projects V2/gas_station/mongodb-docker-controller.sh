#!/bin/bash

set -e # Exit on error.

echo "MongoDB Docker Image Bash Script."

# Ensure the user is in the Docker group
if ! groups | grep -q '\bdocker\b'; then
  echo "❌ You are not in the docker group. Run: sudo usermod -aG docker \$USER"
  exit 1
fi

# Menu options:
echo "Choose an option:"
echo "1) Start the MongoDB docker image instance "
echo "2) Stop the instance"
echo "3) Check instance status"
read -r choice

case $choice in
  1)
    echo "Starting MongoDB..."
    if [ "$(docker ps -aq -f name=gasstation-local-mongodb)" ]; then
        # Container exists
        docker start gasstation-local-mongodb
    else
        # Container doesn’t exist, create it
        docker run -d \
            --name gasstation-local-mongodb \
            -v ~/Desktop/rust-projects/gas_station/local_persistent_storage/gasstation-local-mongodb:/data/db \
            -e MONGO_INITDB_ROOT_USERNAME=admin \
            -e MONGO_INITDB_ROOT_PASSWORD=example \
            -p 27017:27017 \
            mongo:6.0
    fi
    ;;
  2)
    echo "Stopping MongoDB..."
    if [ "$(docker ps -q -f name=gasstation-local-mongodb)" ]; then
        docker stop gasstation-local-mongodb
    else
        echo "⚠️ MongoDB is not running."
    fi
    ;;
  3)
    echo "Checking status..."
    docker ps -a | grep gasstation-local-mongodb || echo "❌ No container found."
    ;;
  *)
    echo "❌ Invalid option"
    ;;
esac
