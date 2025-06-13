#!/bin/bash

# Start the complete login application (Java backend + frontend)
# This script serves both the API and static frontend files from a single Java application

echo "🚀 Starting Login Application..."
echo "================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Installing Java 17..."
    
    # Detect OS and install Java accordingly
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Linux
        sudo apt update
        sudo apt install -y openjdk-17-jdk
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        if command -v brew &> /dev/null; then
            brew install openjdk@17
        else
            echo "Please install Homebrew first or install Java 17 manually"
            exit 1
        fi
    else
        echo "Please install Java 17 manually for your operating system"
        exit 1
    fi
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Installing Maven..."
    
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        sudo apt install -y maven
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        if command -v brew &> /dev/null; then
            brew install maven
        else
            echo "Please install Maven manually"
            exit 1
        fi
    fi
fi

# Navigate to backend directory
cd backend

echo "📦 Building the application..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🌐 Starting the application..."
    echo "   - Frontend: http://localhost:8080"
    echo "   - API: http://localhost:8080/api"
    echo "   - Health Check: http://localhost:8080/api/health"
    echo ""
    echo "📝 Test Users:"
    echo "   - admin / admin123"
    echo "   - user / password"
    echo "   - demo / demo"
    echo "   - test / test123"
    echo ""
    echo "Press Ctrl+C to stop the application"
    echo "================================"
    
    mvn spring-boot:run
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi