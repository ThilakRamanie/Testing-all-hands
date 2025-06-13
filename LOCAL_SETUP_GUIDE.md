# Local Setup Guide for VS Code and Browser Testing

## 📋 Prerequisites

Before you start, make sure you have the following installed on your local machine:

### Required Software
1. **Java 17 or higher**
   - Download from: https://adoptium.net/temurin/releases/
   - Verify installation: `java -version`

2. **Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **VS Code**
   - Download from: https://code.visualstudio.com/

### Recommended VS Code Extensions
- **Extension Pack for Java** (by Microsoft)
- **Spring Boot Extension Pack** (by VMware)
- **REST Client** (by Humao) - For API testing

## 🚀 Step-by-Step Setup

### Step 1: Clone/Download the Project
```bash
# If you have git access to the repository
git clone <repository-url>
cd Testing-all-hands

# OR manually download and extract the project files
```

### Step 2: Open in VS Code
```bash
# Open the project in VS Code
code Testing-all-hands
```

### Step 3: Application Setup

#### Option A: Using VS Code Terminal
1. Open VS Code terminal (`Ctrl+` ` or `View > Terminal`)
2. Navigate to backend directory:
   ```bash
   cd backend
   ```
3. Build and run the Spring Boot application:
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```

#### Option B: Using VS Code Java Extension
1. Open `backend/src/main/java/com/example/login/LoginApplication.java`
2. Click the "Run" button that appears above the `main` method
3. Or press `F5` to debug

#### Option C: Using the provided script
```bash
# Make script executable (Linux/Mac)
chmod +x start-application.sh
./start-application.sh

# Windows
start-application.bat
```

**Note:** The application serves both the frontend and backend from a single Java process on port 8080.

## 🌐 Accessing the Application

### Application URLs
- **Frontend**: http://localhost:8080 (served by Spring Boot)
- **API Base**: http://localhost:8080/api
- **Health Check**: http://localhost:8080/api/health

## 🧪 Testing the Application

### 1. Test Backend API First

#### Using VS Code REST Client Extension
Create a file called `api-tests.http` in your project root:

```http
### Health Check
GET http://localhost:8080/api/health

### Successful Login
POST http://localhost:8080/api/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

### Failed Login
POST http://localhost:8080/api/login
Content-Type: application/json

{
  "username": "invalid",
  "password": "wrong"
}

### Get Profile (replace TOKEN with actual token from login response)
GET http://localhost:8080/api/profile
Authorization: Bearer mock-jwt-token-xxx-admin
```

#### Using curl in Terminal
```bash
# Health check
curl http://localhost:8080/api/health

# Login test
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

#### Using the provided test script
```bash
./test-api.sh
```

### 2. Test Frontend in Browser

1. Open your browser and go to the frontend URL
2. Try logging in with these test credentials:

| Username | Password | Expected Result |
|----------|----------|-----------------|
| admin    | admin123 | ✅ Success |
| user     | password | ✅ Success |
| demo     | demo     | ✅ Success |
| test     | test123  | ✅ Success |
| invalid  | wrong    | ❌ Error message |

### 3. Debug Frontend Issues

#### Check Browser Console
1. Open browser Developer Tools (`F12`)
2. Go to Console tab
3. Look for any JavaScript errors
4. Check Network tab for API call responses

#### Common Issues and Solutions

**CORS Errors:**
- Make sure backend is running on port 8080
- Check that CORS is properly configured in `CorsConfig.java`

**Connection Refused:**
- Verify backend is running: `curl http://localhost:8080/api/health`
- Check if ports are available: `netstat -an | grep 8080`

**Frontend not loading:**
- Verify Python server is running
- Check the correct port in browser URL
- Try a different port if 3000 is occupied

## 🔧 Development Workflow in VS Code

### Backend Development
1. **Edit Java files** in `backend/src/main/java/`
2. **Hot reload**: Spring Boot DevTools will automatically restart the application
3. **Debug**: Set breakpoints and use VS Code debugger (`F5`)

### Frontend Development
1. **Edit HTML/CSS/JS** files in `frontend/`
2. **Auto-refresh**: If using Live Server, changes will auto-refresh
3. **Debug**: Use browser Developer Tools for JavaScript debugging

### Project Structure in VS Code
```
Testing-all-hands/
├── 📁 backend/
│   ├── 📁 src/main/java/com/example/login/
│   │   ├── 📄 LoginApplication.java      # Main Spring Boot app
│   │   ├── 📁 controller/
│   │   │   └── 📄 AuthController.java    # REST endpoints
│   │   ├── 📁 model/                     # Data models
│   │   ├── 📁 service/
│   │   │   └── 📄 MockAuthService.java   # Authentication logic
│   │   └── 📁 config/
│   │       └── 📄 CorsConfig.java        # CORS configuration
│   ├── 📁 src/main/resources/
│   │   └── 📄 application.properties     # Spring Boot config
│   └── 📄 pom.xml                        # Maven dependencies
├── 📁 frontend/
│   ├── 📄 index.html                     # Main login page
│   ├── 📄 styles.css                     # Styling
│   ├── 📄 script.js                      # JavaScript logic
│   └── 📄 server.py                      # Simple HTTP server
└── 📄 README.md                          # Documentation
```

## 🐛 Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find process using port 8080
lsof -i :8080  # Mac/Linux
netstat -ano | findstr :8080  # Windows

# Kill the process or change port in application.properties
server.port=8081
```

**Maven build fails:**
```bash
# Clean and rebuild
mvn clean install
```

**Java version issues:**
```bash
# Check Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/java17  # Linux/Mac
set JAVA_HOME=C:\path\to\java17   # Windows
```

### Frontend Issues

**Python server not starting:**
```bash
# Try different Python command
python server.py 3000
python3 server.py 3000
py server.py 3000  # Windows
```

**Port already in use:**
```bash
# Use a different port
python3 server.py 3001
```

**API calls failing:**
- Check if backend URL in `script.js` matches your backend port
- Verify CORS configuration
- Check browser console for detailed error messages

## 📱 Mobile Testing

To test on mobile devices on the same network:

1. **Find your local IP address:**
   ```bash
   # Linux/Mac
   ifconfig | grep inet
   
   # Windows
   ipconfig
   ```

2. **Update frontend server to bind to all interfaces:**
   ```bash
   # Modify server.py to use 0.0.0.0 instead of localhost
   python3 server.py 3000
   ```

3. **Access from mobile:**
   - Frontend: `http://YOUR_IP:3000`
   - Make sure firewall allows connections

## 🎯 Next Steps

Once you have the basic setup working:

1. **Customize the UI** by editing `frontend/styles.css`
2. **Add new API endpoints** in `backend/src/main/java/com/example/login/controller/`
3. **Extend authentication** by modifying `MockAuthService.java`
4. **Add more frontend features** in `frontend/script.js`

## 📞 Getting Help

If you encounter issues:

1. **Check the logs** in VS Code terminal
2. **Use browser Developer Tools** to debug frontend issues
3. **Verify all prerequisites** are correctly installed
4. **Check port availability** and firewall settings
5. **Review the README.md** for additional configuration options

Happy coding! 🚀