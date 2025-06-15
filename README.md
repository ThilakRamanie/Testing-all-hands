# Login System - JavaScript Frontend + Java Backend

A complete login system with JavaScript frontend and Java backend using Apache Ant build system.

## 🚀 Features

- **Frontend**: Modern JavaScript with responsive design
- **Backend**: Java HTTP server with mock authentication service
- **Build System**: Apache Ant (no Maven or Gradle)
- **Mock Service**: Pre-configured test users with different roles
- **Unit Tests**: Comprehensive test suite with 59+ test cases
- **CORS Support**: Cross-origin requests enabled
- **Session Management**: Local storage for user sessions
- **Responsive Design**: Works on desktop and mobile devices

## 📋 Prerequisites

### Required Software

1. **Java 8 or higher**
   - Download: https://adoptium.net/
   - Verify: `java -version`

2. **Apache Ant**
   - Download: https://ant.apache.org/bindownload.cgi
   - Verify: `ant -version`

### Installation Steps

1. **Install Java**:
   ```bash
   # Download and install Java from https://adoptium.net/
   java -version  # Should show Java version
   ```

2. **Install Apache Ant**:
   ```bash
   # Download Ant from https://ant.apache.org/bindownload.cgi
   # Extract and add to PATH
   ant -version   # Should show Ant version
   ```

## 🏃‍♂️ Quick Start

### Option 1: Using Startup Scripts

**Windows:**
```cmd
start.bat
```

**Linux/Mac:**
```bash
./start.sh
```

### Option 2: Using Ant Directly

```bash
# Build and run
ant run

# Or step by step
ant clean
ant compile
ant run
```

### Option 3: Manual Java Execution

```bash
# Compile
ant compile

# Run manually
java -cp build/classes com.example.login.LoginServer
```

## 🌐 Access the Application

Once started, open your browser and navigate to:

- **Frontend**: http://localhost:8080
- **API Health Check**: http://localhost:8080/api/health
- **Login API**: http://localhost:8080/api/login (POST)

## 👥 Test Users

The system comes with pre-configured test users:

| Username | Password   | Role    |
|----------|------------|---------|
| admin    | admin123   | ADMIN   |
| user     | password   | USER    |
| demo     | demo       | USER    |
| test     | test123    | USER    |
| manager  | manager123 | MANAGER |

## 🏗️ Project Structure

```
Testing-all-hands/
├── src/
│   ├── main/java/com/example/login/
│   │   ├── LoginServer.java              # Main server class
│   │   ├── controller/
│   │   │   ├── LoginController.java      # Login API handler
│   │   │   └── StaticFileHandler.java    # Static file server
│   │   ├── service/
│   │   │   └── MockAuthService.java      # Authentication service
│   │   └── model/
│   │       ├── User.java                 # User model
│   │       ├── LoginRequest.java         # Login request model
│   │       └── LoginResponse.java        # Login response model
│   └── test/java/com/example/login/
│       ├── AllTestSuite.java             # Test suite runner
│       ├── controller/
│       │   └── LoginControllerTest.java  # Controller tests
│       ├── service/
│       │   └── MockAuthServiceTest.java  # Service tests
│       └── model/
│           ├── UserTest.java             # User model tests
│           ├── LoginRequestTest.java     # Request model tests
│           └── LoginResponseTest.java    # Response model tests
├── web/
│   ├── index.html                        # Frontend HTML
│   ├── styles.css                        # CSS styling
│   └── script.js                         # JavaScript logic
├── lib/
│   └── test/                             # Test dependencies (JUnit, Hamcrest)
├── build.xml                             # Ant build configuration
├── start.bat                             # Windows startup script
├── start.sh                              # Unix/Linux startup script
├── run-tests.bat                         # Windows test runner
├── run-tests.sh                          # Unix/Linux test runner
└── README.md                             # This file
```

## 🔧 Ant Build Targets

```bash
ant help          # Show all available targets
ant clean         # Clean build directory
ant compile       # Compile Java source files
ant compile-tests # Compile test source files
ant build         # Build the project
ant run           # Build and run the server (default)
ant test          # Run unit tests
ant test-suite    # Run all tests as a suite
ant package       # Create JAR file
ant install       # Install the application
```

## 🧪 Testing

### Running Unit Tests

The project includes a comprehensive test suite with 59+ test cases covering all major components.

**Option 1: Using Test Scripts**

Windows:
```cmd
run-tests.bat
```

Linux/Mac:
```bash
./run-tests.sh
```

**Option 2: Using Ant Directly**

```bash
# Run all tests
ant test

# Run test suite
ant test-suite

# Compile tests only
ant compile-tests
```

### Test Coverage

The test suite includes:

- **Model Tests** (28 tests):
  - `UserTest.java` - User model validation
  - `LoginRequestTest.java` - Login request validation
  - `LoginResponseTest.java` - Login response validation

- **Service Tests** (21 tests):
  - `MockAuthServiceTest.java` - Authentication logic testing

- **Controller Tests** (10 tests):
  - `LoginControllerTest.java` - HTTP request/response handling

### Test Reports

After running tests, detailed reports are available in:
- `build/test-reports/` - XML and text format reports
- Console output shows summary of passed/failed tests

### Test Dependencies

- **JUnit 4.13.2** - Testing framework
- **Hamcrest Core 1.3** - Assertion library

Dependencies are automatically downloaded to `lib/test/` directory.

## 🖥️ Development

### Frontend Development

The frontend files are located in the `web/` directory:

- `index.html` - Main HTML structure
- `styles.css` - CSS styling with modern design
- `script.js` - JavaScript functionality

Changes to frontend files are reflected immediately (no restart required).

### Backend Development

The backend is pure Java using built-in HTTP server:

- **LoginServer.java** - Main server setup
- **LoginController.java** - Handles login API requests
- **StaticFileHandler.java** - Serves static files
- **MockAuthService.java** - Mock authentication logic

After making changes to Java files:
```bash
ant compile  # Recompile
ant run      # Restart server
```

## 🔌 API Endpoints

### Health Check
```http
GET /api/health
```

Response:
```json
{
  "status": "OK",
  "message": "Login service is running"
}
```

### Login
```http
POST /api/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Success Response:
```json
{
  "success": true,
  "message": "Login successful",
  "token": "token_abc123...",
  "role": "ADMIN"
}
```

Error Response:
```json
{
  "success": false,
  "message": "Invalid username or password"
}
```

## 🎨 Frontend Features

- **Responsive Design**: Works on all screen sizes
- **Modern UI**: Gradient backgrounds, smooth animations
- **Interactive Elements**: Hover effects, loading states
- **Demo Users**: Click to auto-fill credentials
- **Session Management**: Remembers login state
- **Error Handling**: User-friendly error messages
- **Keyboard Shortcuts**: 
  - `Ctrl+L` - Focus username field
  - `Escape` - Logout (when logged in)
  - `Enter` - Submit form

## 🔒 Security Features

- **Input Validation**: Username and password validation
- **CORS Headers**: Proper cross-origin configuration
- **Token-based Auth**: Mock JWT-style tokens
- **Session Storage**: Client-side session management
- **Error Messages**: Generic error messages for security

## 🐛 Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   ```bash
   # Kill existing Java processes
   pkill java
   # Or use different port (modify LoginServer.java)
   ```

2. **Java not found**
   ```bash
   # Install Java and add to PATH
   export JAVA_HOME=/path/to/java
   export PATH=$JAVA_HOME/bin:$PATH
   ```

3. **Ant not found**
   ```bash
   # Install Ant and add to PATH
   export ANT_HOME=/path/to/ant
   export PATH=$ANT_HOME/bin:$PATH
   ```

4. **Compilation errors**
   ```bash
   # Clean and rebuild
   ant clean
   ant compile
   ```

5. **Test failures**
   ```bash
   # Clean and recompile tests
   ant clean
   ant compile-tests
   ant test
   
   # Check test reports
   cat build/test-reports/*.txt
   ```

### Debug Mode

To run with debug output:
```bash
ant -v run  # Verbose mode
```

### Testing API

Open browser console and run:
```javascript
// Test health endpoint
fetch('/api/health').then(r => r.json()).then(console.log);

// Test login
fetch('/api/login', {
  method: 'POST',
  headers: {'Content-Type': 'application/json'},
  body: JSON.stringify({username: 'demo', password: 'demo'})
}).then(r => r.json()).then(console.log);
```

## 📝 Customization

### Adding New Users

Edit `MockAuthService.java`:
```java
private void initializeUsers() {
    users = new HashMap<>();
    users.put("newuser", new User("newuser", "newpass", "USER"));
    // ... existing users
}
```

### Changing Port

Edit `LoginServer.java`:
```java
private static final int PORT = 8081; // Change port
```

### Styling Changes

Edit `web/styles.css` to customize the appearance.

### Adding New API Endpoints

1. Add handler in `LoginController.java`
2. Update routing in `LoginServer.java`

## 🚀 Deployment

### Create JAR file
```bash
ant package
```

### Run JAR file
```bash
java -jar build/login-system.jar
```

## 📄 License

This project is for demonstration purposes. Feel free to use and modify as needed.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Verify all prerequisites are installed
3. Check console output for error messages
4. Ensure port 8080 is available

---

**Built with ❤️ using JavaScript and Java**