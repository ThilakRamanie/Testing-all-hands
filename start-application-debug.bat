@echo off
REM Debug version of start-application.bat with detailed logging

echo Starting Login Application (Debug Mode)...
echo ================================

REM Enable command echoing for debugging
echo on

REM Check if Java is installed
echo Checking Java installation...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java check failed with error level: %errorlevel%
    pause
    exit /b 1
) else (
    echo Java check passed!
)

REM Check if Maven is installed
echo Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven check failed with error level: %errorlevel%
    pause
    exit /b 1
) else (
    echo Maven check passed!
)

REM Navigate to backend directory
echo Current directory: %CD%
echo Changing to backend directory...
cd backend
echo New directory: %CD%

REM Check if pom.xml exists
if not exist pom.xml (
    echo ERROR: pom.xml not found in backend directory!
    echo Current directory contents:
    dir
    pause
    exit /b 1
) else (
    echo pom.xml found!
)

echo Building the application...
mvn clean compile
echo Build command completed with error level: %errorlevel%

if %errorlevel% equ 0 (
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
    
    echo Running: mvn spring-boot:run
    mvn spring-boot:run
    echo Spring Boot command completed with error level: %errorlevel%
) else (
    echo ERROR: Build failed with error level: %errorlevel%
    echo.
    echo Maven output above should show the error details.
    pause
    exit /b 1
)

echo Script completed.
pause