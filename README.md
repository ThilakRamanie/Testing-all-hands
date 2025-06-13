# Testing-all-hands
A repository to test the all hands ai agent

## Login Page Application

This project contains a complete login system with a JavaScript frontend and Java Spring Boot backend using mock authentication.

### Project Structure

```
├── frontend/                 # JavaScript frontend
│   ├── index.html           # Main login page
│   ├── styles.css           # CSS styling
│   ├── script.js            # JavaScript logic
│   └── server.py            # Simple HTTP server for frontend
├── backend/                 # Java Spring Boot backend
│   ├── src/main/java/       # Java source code
│   │   └── com/example/login/
│   │       ├── LoginApplication.java     # Main Spring Boot application
│   │       ├── config/CorsConfig.java    # CORS configuration
│   │       ├── controller/AuthController.java  # REST API endpoints
│   │       ├── model/                    # Data models
│   │       └── service/MockAuthService.java    # Mock authentication service
│   ├── src/main/resources/
│   │   └── application.properties       # Spring Boot configuration
│   └── pom.xml                         # Maven dependencies
├── start-backend.sh         # Script to start Java backend
└── start-frontend.sh        # Script to start frontend server
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
- Python 3.x (for frontend server)

## 🚀 Quick Start

### For Local Development in VS Code

📖 **See detailed guides:**
- **[QUICK_START.md](QUICK_START.md)** - 5-minute setup guide
- **[LOCAL_SETUP_GUIDE.md](LOCAL_SETUP_GUIDE.md)** - Complete VS Code development guide

### Installation and Setup

#### Option 1: Using the provided scripts (Recommended)

**Windows:**
```bash
start-backend.bat    # Start backend
start-frontend.bat   # Start frontend (in new terminal)
```

**Mac/Linux:**
```bash
./start-backend.sh   # Start backend
./start-frontend.sh  # Start frontend (in new terminal)
```

#### Option 2: Manual setup

1. **Backend Setup:**
   ```bash
   cd backend
   mvn clean compile
   mvn spring-boot:run
   ```

2. **Frontend Setup:**
   ```bash
   cd frontend
   python3 server.py 12000
   ```

### Accessing the Application

**For Local Development:**
- **Frontend:** http://localhost:3000 (or port specified)
- **Backend API:** http://localhost:8080/api
- **Health Check:** http://localhost:8080/api/health

**Production Demo:**
- **Frontend:** https://work-1-agondegmivxspeyw.prod-runtime.all-hands.dev

### VS Code Development Setup

**Recommended Extensions:**
- Extension Pack for Java (Microsoft)
- Spring Boot Extension Pack (VMware)
- Live Server (Ritwick Dey)
- REST Client (Humao)

**Quick Testing:**
- Open `api-tests.http` in VS Code for API testing
- Use Live Server extension for frontend development
- Debug Java backend with F5

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
