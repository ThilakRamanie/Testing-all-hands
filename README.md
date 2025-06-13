# Testing-all-hands
A repository to test the all hands ai agent

## 🚀 Login System with JavaScript Frontend and Java Backend

A complete login system featuring a modern JavaScript frontend and a robust Java Spring Boot backend with mock authentication service. The entire application runs as a single Java application serving both the API and static frontend files.

### Features

- **Modern Frontend**: Responsive HTML5/CSS3/JavaScript interface with gradient styling and smooth animations
- **Java Backend**: Spring Boot REST API with mock authentication service
- **Single Application**: Frontend and backend served from the same Java application (no separate servers needed)
- **Mock Authentication**: Predefined test users with role-based access
- **CORS Support**: Configured for cross-origin requests
- **Token-based Auth**: JWT-like token simulation for session management
- **Error Handling**: Comprehensive client and server-side error handling
- **Development Tools**: VS Code configuration, REST API testing, and startup scripts

### Technology Stack

- **Frontend**: HTML5, CSS3, JavaScript (ES6+)
- **Backend**: Java 17, Spring Boot 3.1.0, Maven
- **Development**: VS Code, REST Client
- **Authentication**: Mock service with token simulation
- **Deployment**: Single JAR file with embedded frontend

### Project Structure

```
├── backend/                 # Java Spring Boot application
│   ├── src/main/java/       # Java source code
│   │   └── com/example/login/
│   │       ├── LoginApplication.java     # Main Spring Boot application
│   │       ├── config/CorsConfig.java    # CORS configuration
│   │       ├── controller/AuthController.java  # REST API endpoints
│   │       ├── model/                    # Data models
│   │       └── service/MockAuthService.java    # Mock authentication service
│   ├── src/main/resources/
│   │   ├── static/                      # Frontend files (HTML, CSS, JS)
│   │   │   ├── index.html              # Main login page
│   │   │   ├── styles.css              # CSS styling
│   │   │   └── script.js               # JavaScript logic
│   │   └── application.properties       # Spring Boot configuration
│   └── pom.xml                         # Maven dependencies
├── start-application.sh     # Script to start the complete application
└── start-application.bat    # Windows script to start the application
```

### Features

#### Frontend (JavaScript)
- Modern, responsive login form with gradient styling
- Form validation and error handling
- Loading states and user feedback
- CORS-enabled API communication
- Token storage in localStorage
- Mobile-friendly responsive design

#### Backend (Java Spring Boot)
- RESTful API endpoints for authentication
- Mock user database with predefined users
- JWT-like token generation and validation
- CORS configuration for cross-origin requests
- Input validation and error handling
- Health check endpoint

### Mock Users

The application comes with the following test users:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user     | password | USER  |
| test     | test123  | USER  |
| demo     | demo     | USER  |

### API Endpoints

- `POST /api/login` - Authenticate user and return token
- `POST /api/logout` - Logout user (invalidate token)
- `GET /api/profile` - Get user profile (requires authentication)
- `GET /api/health` - Health check endpoint

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## 🚀 Quick Start

### For Local Development in VS Code

📖 **See detailed guides:**
- **[QUICK_START.md](QUICK_START.md)** - 5-minute setup guide
- **[LOCAL_SETUP_GUIDE.md](LOCAL_SETUP_GUIDE.md)** - Complete VS Code development guide

### Installation and Setup

#### Option 1: Using the provided scripts (Recommended)

**Windows:**
```bash
start-application.bat
```

**Mac/Linux:**
```bash
./start-application.sh
```

#### Option 2: Manual setup

```bash
cd backend
mvn clean compile
mvn spring-boot:run
```

The application will start on port 8080 and serve both the API and frontend files.

### Accessing the Application

**Local Development:**
- **Application:** http://localhost:8080 (serves both frontend and API)
- **API Endpoints:** http://localhost:8080/api
- **Health Check:** http://localhost:8080/api/health

**Production Demo:**
- **Application:** https://work-1-agondegmivxspeyw.prod-runtime.all-hands.dev

### VS Code Development Setup

**Recommended Extensions:**
- Extension Pack for Java (Microsoft)
- Spring Boot Extension Pack (VMware)
- REST Client (Humao)

**Quick Testing:**
- Open `api-tests.http` in VS Code for API testing
- Debug Java application with F5
- Frontend is served automatically by Spring Boot

### Usage

1. Open the frontend URL in your browser
2. Use any of the mock user credentials to log in
3. The application will authenticate against the Java backend
4. Upon successful login, you'll receive a token and user information
5. The token is stored in localStorage for subsequent requests

### Development Notes

- The backend uses a mock authentication service with hardcoded users
- CORS is configured to allow requests from any origin
- The frontend includes error handling and loading states
- All API responses include appropriate HTTP status codes
- The application is designed to be easily extensible

### Testing the API

You can test the API endpoints using curl:

```bash
# Health check
curl http://localhost:8080/api/health

# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Get profile (replace TOKEN with actual token from login response)
curl -X GET http://localhost:8080/api/profile \
  -H "Authorization: Bearer TOKEN"
```

### Customization

- **Adding new users:** Modify the `MockAuthService.java` file
- **Styling:** Update `styles.css` for frontend appearance
- **API endpoints:** Add new controllers in the `controller` package
- **Frontend behavior:** Modify `script.js` for additional functionality

### Security Notes

This is a mock implementation for demonstration purposes. In a production environment, you should:
- Use a real database for user storage
- Implement proper password hashing
- Use real JWT tokens with proper signing
- Add rate limiting and other security measures
- Use HTTPS for all communications
- Implement proper session management
