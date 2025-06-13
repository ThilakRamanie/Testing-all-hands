# VS Code Local Development Setup

This guide will help you set up the login application in your local VS Code environment.

## Prerequisites

### 1. Install Java 17 or Higher
- **Download:** https://adoptium.net/
- **Verify installation:** Open Command Prompt/Terminal and run:
  ```bash
  java -version
  ```
  You should see something like: `openjdk version "17.0.x"`

### 2. Install Maven
- **Download:** https://maven.apache.org/download.cgi
- **Installation Guide:** https://maven.apache.org/install.html
- **Verify installation:** Open Command Prompt/Terminal and run:
  ```bash
  mvn -version
  ```

### 3. Install Git
- **Download:** https://git-scm.com/downloads
- **Verify installation:**
  ```bash
  git --version
  ```

## VS Code Setup

### 1. Install VS Code Extensions
Open VS Code and install these recommended extensions:

**Essential Extensions:**
- `Extension Pack for Java` (Microsoft) - Includes Java language support, debugging, testing
- `Spring Boot Extension Pack` (VMware) - Spring Boot support and tools
- `REST Client` (Huachao Mao) - For testing API endpoints

**Optional but Helpful:**
- `GitLens` (GitKraken) - Enhanced Git capabilities
- `Bracket Pair Colorizer` - Better code readability
- `Auto Rename Tag` - HTML/XML tag editing
- `Live Server` - For frontend development (though not needed for this project)

### 2. Clone the Repository
```bash
git clone https://github.com/ThilakRamanie/Testing-all-hands.git
cd Testing-all-hands
```

### 3. Open in VS Code
```bash
code .
```
Or open VS Code and use `File > Open Folder` to open the `Testing-all-hands` directory.

## Running the Application

### Method 1: Using the Batch File (Windows)
1. Open Command Prompt in the project root directory
2. Run:
   ```cmd
   start-application.bat
   ```

### Method 2: Using PowerShell (Windows)
1. Open PowerShell in the project root directory
2. Run:
   ```powershell
   .\start-application.sh
   ```

### Method 3: Manual Steps (All Platforms)
1. Open Terminal in VS Code (`Terminal > New Terminal`)
2. Navigate to backend directory:
   ```bash
   cd backend
   ```
3. Build the application:
   ```bash
   mvn clean compile
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Method 4: Using VS Code Java Extension
1. Open the `backend` folder in VS Code
2. Open `src/main/java/com/example/login/LoginServiceApplication.java`
3. Click the "Run" button that appears above the `main` method
4. Or press `F5` to start debugging

## Troubleshooting

### Issue: "Java is not installed" or "mvn command not found"
**Solution:**
1. Ensure Java 17+ and Maven are properly installed
2. Add them to your system PATH environment variable
3. Restart VS Code and Command Prompt/Terminal
4. Verify with `java -version` and `mvn -version`

### Issue: Port 8080 already in use
**Solution:**
1. Stop any other applications using port 8080
2. Or modify `backend/src/main/resources/application.properties`:
   ```properties
   server.port=8081
   ```
3. Then access the application at http://localhost:8081

### Issue: Build fails with dependency errors
**Solution:**
1. Delete the `backend/target` directory
2. Run:
   ```bash
   cd backend
   mvn clean install
   ```

### Issue: Frontend not loading
**Solution:**
1. Ensure you're accessing http://localhost:8080 (not 8080/index.html)
2. Check that static files are in `backend/src/main/resources/static/`
3. Restart the application

### Issue: CORS errors in browser console
**Solution:**
The application is configured to handle CORS automatically. If you still see errors:
1. Ensure frontend and backend are served from the same port (8080)
2. Check browser developer tools for specific error messages

## Development Workflow

### 1. Making Frontend Changes
- Edit files in `backend/src/main/resources/static/`
- Changes to HTML, CSS, JS are reflected immediately (no restart needed)
- Refresh browser to see changes

### 2. Making Backend Changes
- Edit Java files in `backend/src/main/java/`
- Stop the application (Ctrl+C)
- Restart using any of the methods above
- Or use VS Code's hot reload feature with the Java extension

### 3. Testing the Application
**Frontend:** http://localhost:8080
**API Health Check:** http://localhost:8080/api/health
**API Login:** POST to http://localhost:8080/api/login

**Test Credentials:**
- Username: `admin`, Password: `admin123`
- Username: `user`, Password: `password`
- Username: `demo`, Password: `demo`
- Username: `test`, Password: `test123`

## VS Code Configuration

### 1. Java Configuration
Create `.vscode/settings.json` in your project root:
```json
{
    "java.home": "C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.x-hotspot",
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-17",
            "path": "C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.x-hotspot"
        }
    ],
    "spring-boot.ls.java.home": "C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.x-hotspot"
}
```
*Note: Adjust the path to match your Java installation*

### 2. Launch Configuration
Create `.vscode/launch.json`:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch LoginServiceApplication",
            "request": "launch",
            "mainClass": "com.example.login.LoginServiceApplication",
            "projectName": "login-service",
            "cwd": "${workspaceFolder}/backend"
        }
    ]
}
```

### 3. Tasks Configuration
Create `.vscode/tasks.json`:
```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "maven-compile",
            "type": "shell",
            "command": "mvn",
            "args": ["clean", "compile"],
            "options": {
                "cwd": "${workspaceFolder}/backend"
            },
            "group": "build"
        },
        {
            "label": "maven-run",
            "type": "shell",
            "command": "mvn",
            "args": ["spring-boot:run"],
            "options": {
                "cwd": "${workspaceFolder}/backend"
            },
            "group": "build"
        }
    ]
}
```

## Project Structure
```
Testing-all-hands/
├── backend/
│   ├── src/main/java/com/example/login/
│   │   ├── LoginServiceApplication.java
│   │   ├── controller/
│   │   ├── model/
│   │   └── service/
│   ├── src/main/resources/
│   │   ├── static/          # Frontend files (HTML, CSS, JS)
│   │   └── application.properties
│   └── pom.xml
├── start-application.bat    # Windows startup script
├── start-application.sh     # Unix/Mac startup script
└── README.md
```

## Next Steps
1. Follow this setup guide
2. Run the application using any of the methods above
3. Open http://localhost:8080 in your browser
4. Test login with the provided credentials
5. Start developing!

## Support
If you encounter any issues:
1. Check the troubleshooting section above
2. Ensure all prerequisites are properly installed
3. Check VS Code's Java extension logs
4. Verify the application logs in the terminal