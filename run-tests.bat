@echo off
echo ================================
echo Login System - Unit Test Runner
echo ================================
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Ant is available
ant -version >nul 2>&1
if errorlevel 1 (
    echo Error: Apache Ant is not installed or not in PATH
    pause
    exit /b 1
)

echo Running unit tests...
echo.

REM Run the tests
ant test

echo.
echo Test Summary:
echo =============

REM Check if test reports exist
if exist "build\test-reports" (
    echo Test reports available in: build\test-reports\
    echo Check the .txt files for detailed results
) else (
    echo No test reports found.
)

echo.
echo ================================
pause