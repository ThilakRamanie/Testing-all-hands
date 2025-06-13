@echo off
echo Starting Login Application (Simple Fixed Version)...
echo ================================

REM Skip version checks and go straight to building
echo Navigating to backend directory...
cd backend

echo Building the application...
mvn clean compile

echo Build completed. Starting the application...
echo.
echo Application URLs:
echo - Frontend: http://localhost:8080
echo - API: http://localhost:8080/api/health
echo.
echo Test credentials: admin / admin123
echo.
echo Starting server...
mvn spring-boot:run

pause