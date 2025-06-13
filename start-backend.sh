#!/bin/bash

echo "Starting Java Backend..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Installing OpenJDK 17..."
    sudo apt-get update
    sudo apt-get install -y openjdk-17-jdk
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Installing Maven..."
    sudo apt-get install -y maven
fi

# Navigate to backend directory
cd backend

# Build and run the Spring Boot application
echo "Building the application..."
mvn clean compile

echo "Starting the backend server on port 8080..."
mvn spring-boot:run