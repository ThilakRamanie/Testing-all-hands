@echo off
echo Starting Java Backend...

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed or not in PATH. Please install Java 17 or higher.
    echo Download from: https://adoptium.net/temurin/releases/
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH. Please install Maven.
    echo Download from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

REM Navigate to backend directory
cd backend

REM Build and run the Spring Boot application
echo Building the application...
mvn clean compile

echo Starting the backend server on port 8080...
mvn spring-boot:run