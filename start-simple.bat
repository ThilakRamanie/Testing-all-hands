@echo off
echo Starting Login Application...
cd backend
mvn clean compile
mvn spring-boot:run
pause