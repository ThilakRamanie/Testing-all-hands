@echo off
echo Starting Login System...
echo ========================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed. Please install Java 8 or higher.
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

REM Check if Ant is installed
ant -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Apache Ant is not installed. Please install Ant.
    echo Download from: https://ant.apache.org/bindownload.cgi
    pause
    exit /b 1
)

echo Building and starting the application...
ant run

pause