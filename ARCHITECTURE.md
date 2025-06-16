# Login System Architecture

## Overview

This document describes the architecture of the Login System, a full-stack web application with a JavaScript frontend and Java backend. The system implements a mock authentication service with comprehensive testing and manual testing capabilities.

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                             │
├─────────────────────────────────────────────────────────────────┤
│  Web Browser (JavaScript + HTML + CSS)                         │
│  ├── login.html (UI Structure)                                 │
│  ├── login.js (Client Logic)                                   │
│  └── login.css (Styling)                                       │
└─────────────────────────────────────────────────────────────────┘
                                │
                                │ HTTP/AJAX Requests
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                      SERVER LAYER                               │
├─────────────────────────────────────────────────────────────────┤
│  Java HTTP Server (Port 12001)                                 │
│  ├── LoginServer.java (Main Server)                            │
│  ├── LoginController.java (HTTP Handler)                       │
│  └── CORS & Static File Serving                                │
└─────────────────────────────────────────────────────────────────┘
                                │
                                │ Method Calls
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                               │
├─────────────────────────────────────────────────────────────────┤
│  Authentication Service                                         │
│  └── MockAuthService.java (Business Logic)                     │
└─────────────────────────────────────────────────────────────────┘
                                │
                                │ Data Access
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                      DATA LAYER                                 │
├─────────────────────────────────────────────────────────────────┤
│  In-Memory Data Store                                           │
│  ├── User.java (User Model)                                    │
│  ├── LoginRequest.java (Request Model)                         │
│  └── LoginResponse.java (Response Model)                       │
└─────────────────────────────────────────────────────────────────┘
```

## Component Architecture

### 1. Frontend Components

#### 1.1 User Interface Layer
```
frontend/
├── login.html          # Main login page structure
├── login.css           # Responsive styling and animations
└── login.js            # Client-side logic and API communication
```

**Key Features:**
- Responsive design (mobile-first approach)
- Form validation and user feedback
- AJAX communication with backend
- Session management
- Error handling and user notifications

#### 1.2 Frontend Technologies
- **HTML5**: Semantic markup and form structure
- **CSS3**: Flexbox layout, animations, responsive design
- **Vanilla JavaScript**: No external dependencies
- **Fetch API**: Modern HTTP client for API calls

### 2. Backend Components

#### 2.1 Server Layer
```
src/main/java/com/example/login/
├── LoginServer.java     # HTTP server and routing
└── controller/
    └── LoginController.java  # Request handling and response formatting
```

**LoginServer.java:**
- HTTP server using `com.sun.net.httpserver`
- Static file serving for frontend assets
- CORS configuration for cross-origin requests
- Port configuration (12001 for All Hands environment)

**LoginController.java:**
- RESTful API endpoints
- Request/response processing
- JSON serialization/deserialization
- Error handling and HTTP status codes

#### 2.2 Service Layer
```
src/main/java/com/example/login/service/
└── MockAuthService.java  # Authentication business logic
```

**MockAuthService.java:**
- User authentication logic
- Token generation and validation
- Password verification
- Role-based access control
- Defensive programming practices

#### 2.3 Model Layer
```
src/main/java/com/example/login/model/
├── User.java           # User entity model
├── LoginRequest.java   # Authentication request DTO
└── LoginResponse.java  # Authentication response DTO
```

**Data Models:**
- **User**: Username, password, role
- **LoginRequest**: Username, password
- **LoginResponse**: Success status, message, token, role

## API Architecture

### REST Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| POST | `/api/login` | Authenticate user | `{username, password}` | `{success, message, token, role}` |
| POST | `/api/validate` | Validate token | `{token}` | `{valid}` |
| GET | `/api/users` | Get all users (dev) | None | `{users: [...]}` |
| GET | `/` | Serve login page | None | HTML |
| GET | `/login.js` | Serve JavaScript | None | JavaScript |
| GET | `/login.css` | Serve CSS | None | CSS |

### Request/Response Flow

```
1. User submits login form
   ↓
2. Frontend validates input
   ↓
3. AJAX POST to /api/login
   ↓
4. LoginController receives request
   ↓
5. Controller calls MockAuthService
   ↓
6. Service validates credentials
   ↓
7. Service generates token (if valid)
   ↓
8. Response sent back to frontend
   ↓
9. Frontend handles success/error
   ↓
10. User redirected or shown error
```

## Security Architecture

### Authentication Flow
```
┌─────────────┐    ┌──────────────┐    ┌─────────────┐
│   Client    │    │  Controller  │    │   Service   │
└─────────────┘    └──────────────┘    └─────────────┘
       │                   │                   │
       │ POST /api/login   │                   │
       ├──────────────────►│                   │
       │                   │ authenticate()    │
       │                   ├──────────────────►│
       │                   │                   │ validate credentials
       │                   │                   │ generate token
       │                   │ LoginResponse     │
       │                   │◄──────────────────┤
       │ JSON Response     │                   │
       │◄──────────────────┤                   │
       │                   │                   │
```

### Security Features
- **Input Validation**: Null, empty, and whitespace checks
- **Token-based Authentication**: UUID-based tokens with prefix validation
- **CORS Protection**: Configured for specific origins
- **Defensive Copying**: Immutable user data access
- **Role-based Access**: ADMIN, MANAGER, USER roles
- **Session Management**: Client-side token storage

## Data Architecture

### User Data Store
```
In-Memory HashMap<String, User>
├── "admin"   → User("admin", "admin123", "ADMIN")
├── "demo"    → User("demo", "demo", "USER")
├── "user"    → User("user", "password", "USER")
├── "test"    → User("test", "test123", "USER")
└── "manager" → User("manager", "manager123", "MANAGER")
```

### Token Format
```
Pattern: "token_" + 16-character hexadecimal string
Example: "token_a1b2c3d4e5f67890"
Validation: Prefix check + minimum length (11 characters)
```

## Testing Architecture

### Test Structure
```
src/test/java/com/example/login/
├── AllTestSuite.java                 # Test runner (78 tests)
├── controller/
│   └── LoginControllerTest.java      # HTTP layer tests (10 tests)
├── service/
│   └── MockAuthServiceTest.java      # Business logic tests (40 tests)
└── model/
    ├── UserTest.java                 # User model tests (8 tests)
    ├── LoginRequestTest.java         # Request DTO tests (9 tests)
    └── LoginResponseTest.java        # Response DTO tests (11 tests)
```

### Test Categories
- **Unit Tests**: Individual component testing
- **Integration Tests**: Component interaction testing
- **Performance Tests**: Load and concurrency testing
- **Security Tests**: Input validation and edge cases
- **Manual Tests**: End-to-end user workflow testing

## Deployment Architecture

### Development Environment
```
Local Development:
├── Java 8+ Runtime
├── Apache Ant Build System
├── JUnit 4.13.2 Testing Framework
└── Built-in HTTP Server (no external dependencies)
```

### All Hands Environment
```
Production URLs:
├── Port 12000: https://work-1-agondegmivxspeyw.prod-runtime.all-hands.dev
└── Port 12001: https://work-2-agondegmivxspeyw.prod-runtime.all-hands.dev (current)

Configuration:
├── CORS enabled for iframe embedding
├── Host binding: 0.0.0.0 (all interfaces)
└── Static file serving enabled
```

## Build Architecture

### Build System (Apache Ant)
```
build.xml targets:
├── init          # Create build directories
├── compile       # Compile Java source
├── compile-tests # Compile test source
├── test          # Run individual test classes
├── test-suite    # Run complete test suite
├── run           # Start the server
└── clean         # Clean build artifacts
```

### Directory Structure
```
project-root/
├── src/
│   ├── main/java/           # Application source code
│   └── test/java/           # Test source code
├── frontend/                # Frontend assets
├── build/
│   ├── classes/             # Compiled application classes
│   ├── test-classes/        # Compiled test classes
│   └── test-reports/        # Test execution reports
├── lib/
│   └── test/                # Test dependencies (auto-downloaded)
├── manual-test.sh           # Manual testing helper script
├── switch-port.sh           # Port switching utility
└── build.xml                # Ant build configuration
```

## Performance Characteristics

### Benchmarks
- **Authentication Speed**: 1000 authentications < 1 second
- **Concurrent Users**: Supports multiple simultaneous logins
- **Memory Usage**: Minimal (in-memory user store)
- **Startup Time**: < 2 seconds
- **Response Time**: < 50ms for authentication requests

### Scalability Considerations
- **Current**: Single-threaded HTTP server
- **Limitations**: In-memory storage, no persistence
- **Future**: Could be extended with database, clustering

## Technology Stack

### Backend
- **Language**: Java 8+
- **HTTP Server**: `com.sun.net.httpserver.HttpServer`
- **JSON Processing**: Manual parsing (no external dependencies)
- **Build Tool**: Apache Ant
- **Testing**: JUnit 4.13.2 + Hamcrest

### Frontend
- **Languages**: HTML5, CSS3, JavaScript (ES6+)
- **HTTP Client**: Fetch API
- **Styling**: CSS Grid/Flexbox
- **No external frameworks or libraries**

### Development Tools
- **Version Control**: Git
- **Testing**: Comprehensive unit and integration tests
- **Documentation**: Markdown-based
- **Manual Testing**: Automated helper scripts

## Extension Points

### Future Enhancements
1. **Database Integration**: Replace in-memory storage
2. **JWT Tokens**: More secure token implementation
3. **Password Hashing**: BCrypt or similar
4. **Session Management**: Server-side session storage
5. **Rate Limiting**: Prevent brute force attacks
6. **Logging**: Structured logging with levels
7. **Configuration**: External configuration files
8. **Monitoring**: Health checks and metrics

### Plugin Architecture
The modular design allows for easy extension:
- **AuthService Interface**: Pluggable authentication providers
- **Controller Pattern**: Additional endpoints
- **Model Validation**: Custom validation rules
- **Response Formatters**: Different output formats

---

**Architecture Version**: 1.0  
**Last Updated**: 2025-06-13  
**Total Components**: 12 main classes + frontend assets  
**Test Coverage**: 78 comprehensive test cases  
**Documentation**: Complete with manual testing guides