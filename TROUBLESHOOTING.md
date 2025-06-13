# Troubleshooting Guide

## Issue: start-application.bat doesn't start the server

### Step 1: Use the Debug Script
Run `start-application-debug.bat` instead of `start-application.bat`. This will show you exactly where the script fails.

### Step 2: Manual Testing
Open Command Prompt and run these commands one by one:

```cmd
# Check Java installation
java -version

# Check Maven installation
mvn -version

# Navigate to project directory
cd path\to\Testing-all-hands

# Navigate to backend
cd backend

# Check if pom.xml exists
dir pom.xml

# Try building manually
mvn clean compile

# If build succeeds, try running
mvn spring-boot:run
```

### Step 3: Common Issues and Solutions

#### Issue 1: Java not found
**Error**: `'java' is not recognized as an internal or external command`
**Solution**: 
- Install Java 17 or higher from https://adoptium.net/
- Add Java to your PATH environment variable

#### Issue 2: Maven not found
**Error**: `'mvn' is not recognized as an internal or external command`
**Solution**:
- Install Maven from https://maven.apache.org/download.cgi
- Add Maven to your PATH environment variable

#### Issue 3: Wrong directory
**Error**: `The system cannot find the file specified` or `pom.xml not found`
**Solution**:
- Make sure you're running the script from the root directory of the project
- The script should be in the same folder as the `backend` directory

#### Issue 4: Port already in use
**Error**: `Port 8080 was already in use`
**Solution**:
- Kill any existing Java processes: `taskkill /f /im java.exe`
- Or use a different port: `mvn spring-boot:run -Dserver.port=8081`

#### Issue 5: Build fails
**Error**: Maven compilation errors
**Solution**:
- Check if you have internet connection (Maven needs to download dependencies)
- Try: `mvn clean install -U` to force update dependencies
- Check Java version compatibility

### Step 4: Alternative Startup Methods

#### Method 1: Use start-simple.bat
```cmd
start-simple.bat
```

#### Method 2: Manual startup
```cmd
cd backend
mvn clean compile
mvn spring-boot:run
```

#### Method 3: Use IDE
- Open the project in VS Code or IntelliJ
- Navigate to `backend/src/main/java/com/example/login/LoginServiceApplication.java`
- Right-click and select "Run"

### Step 5: Verify Application is Running

Once started, test these URLs in your browser:
- http://localhost:8080 (Frontend)
- http://localhost:8080/api/health (API health check)

### Step 6: Check Logs

If the application starts but doesn't work properly:
- Check console output for error messages
- Look for log files in the `backend` directory
- Check browser developer console (F12) for JavaScript errors

### Getting Help

If none of these steps work:
1. Run `start-application-debug.bat`
2. Copy the complete output
3. Note which step fails
4. Check the error message carefully
5. Search for the specific error online or ask for help with the exact error message