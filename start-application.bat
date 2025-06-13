@echo off
REM Start the complete login application (Java backend + frontend)
REM This script serves both the API and static frontend files from a single Java application

echo 🚀 Starting Login Application...
echo ================================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java 17 or higher.
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven is not installed. Please install Maven.
    echo Download from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

REM Navigate to backend directory
cd backend

echo 📦 Building the application...
mvn clean compile

if %errorlevel% equ 0 (
    echo ✅ Build successful!
    echo.
    echo 🌐 Starting the application...
    echo    - Frontend: http://localhost:8080
    echo    - API: http://localhost:8080/api
    echo    - Health Check: http://localhost:8080/api/health
    echo.
    echo 📝 Test Users:
    echo    - admin / admin123
    echo    - user / password
    echo    - demo / demo
    echo    - test / test123
    echo.
    echo Press Ctrl+C to stop the application
    echo ================================
    
    mvn spring-boot:run
) else (
    echo ❌ Build failed. Please check the error messages above.
    pause
    exit /b 1
)