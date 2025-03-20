#!/bin/bash

set -e  # Exit on error

echo "🚀 Docker Automation Script"

# Ensure the user is in the Docker group
if ! groups | grep -q '\bdocker\b'; then
  echo "❌ You are not in the docker group. Run: sudo usermod -aG docker \$USER"
  exit 1
fi

# Menu options
echo "Choose an option:"
echo "1) Build & Run (docker-compose up --build -d)"
echo "2) Stop Containers (docker-compose down)"
echo "3) Restart (docker-compose down && docker-compose up --build -d)"
echo "4) Show Running Containers (docker ps)"
echo "5) Clean Unused Images & Containers (docker system prune -a)"
echo "6) Show Logs (docker-compose logs -f)"
echo "7) Enter Container Shell (interactive)"
read -p "Enter your choice: " choice

case $choice in
  1) echo "🔨 Building & Running..." && docker-compose up --build -d ;;
  2) echo "🛑 Stopping Containers..." && docker-compose down ;;
  3) echo "🔄 Restarting..." && docker-compose down && docker-compose up --build -d ;;
  4) echo "📋 Showing Running Containers..." && docker ps ;;
  5) echo "🧹 Cleaning Up..." && docker system prune -a -f ;;
  6) echo "📜 Showing Logs..." && docker-compose logs -f ;;
  7) 
     echo "🐚 Available Containers:"
     docker ps --format "table {{.ID}}\t{{.Names}}"
     read -p "Enter container name or ID: " container
     docker exec -it "$container" /bin/sh
     ;;
  *) echo "❌ Invalid option" ;;
esac
