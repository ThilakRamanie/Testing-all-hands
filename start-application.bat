@echo off
setlocal enabledelayedexpansion
REM Start the complete login application (Java backend + frontend)
REM This script serves both the API and static frontend files from a single Java application

echo Starting Login Application...
echo ================================

REM Check if Java is installed
echo Checking Java installation...
java -version >nul 2>&1
set JAVA_CHECK=!errorlevel!
if !JAVA_CHECK! neq 0 (
    echo ERROR: Java is not installed. Please install Java 17 or higher.
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)
echo Java found!

REM Check if Maven is installed
echo Checking Maven installation...
mvn -version >nul 2>&1
set MAVEN_CHECK=!errorlevel!
if !MAVEN_CHECK! neq 0 (
    echo ERROR: Maven is not installed. Please install Maven.
    echo Download from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
echo Maven found!

REM Check if backend directory exists
if not exist backend (
    echo ERROR: backend directory not found!
    echo Make sure you're running this script from the project root directory.
    pause
    exit /b 1
)

REM Navigate to backend directory
echo Navigating to backend directory...
cd backend

REM Check if pom.xml exists
if not exist pom.xml (
    echo ERROR: pom.xml not found in backend directory!
    pause
    exit /b 1
)

echo Building the application...
mvn clean compile
set BUILD_RESULT=!errorlevel!

if !BUILD_RESULT! equ 0 (
    echo Build successful!
    echo.
    echo Starting the application...
    echo    - Frontend: http://localhost:8080
    echo    - API: http://localhost:8080/api
    echo    - Health Check: http://localhost:8080/api/health
    echo.
    echo Test Users:
    echo    - admin / admin123
    echo    - user / password
    echo    - demo / demo
    echo    - test / test123
    echo.
    echo Press Ctrl+C to stop the application
    echo ================================
    echo.
    
    mvn spring-boot:run
) else (
    echo ERROR: Build failed with exit code !BUILD_RESULT!
    echo Please check the error messages above.
    echo.
    echo Common solutions:
    echo 1. Make sure you have internet connection for Maven dependencies
    echo 2. Try running: mvn clean install -U
    echo 3. Check if Java version is 17 or higher
    pause
    exit /b 1
)