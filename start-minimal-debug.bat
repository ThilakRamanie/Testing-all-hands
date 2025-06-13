@echo off
echo === Minimal Debug Script ===

echo Step 1: Check Maven exit code
mvn -version
echo Maven exit code: %errorlevel%
echo.

echo Step 2: Check if backend directory exists
if exist backend (
    echo Backend directory found!
) else (
    echo ERROR: Backend directory not found!
    pause
    exit /b 1
)

echo Step 3: Navigate to backend
cd backend
echo Current directory: %CD%

echo Step 4: Check if pom.xml exists
if exist pom.xml (
    echo pom.xml found!
) else (
    echo ERROR: pom.xml not found!
    pause
    exit /b 1
)

echo Step 5: Try Maven compile
echo Running: mvn clean compile
mvn clean compile
echo Maven compile exit code: %errorlevel%

echo Step 6: Try Maven run (if compile succeeded)
if %errorlevel% equ 0 (
    echo Compile successful, starting application...
    mvn spring-boot:run
) else (
    echo Compile failed!
    pause
)

echo Script completed.
pause