# 🚀 Quick Start Guide - 5 Minutes to Running

## 📥 Download and Setup

### Option 1: Download ZIP (Easiest)
1. Download the project as ZIP file
2. Extract to your desired folder
3. Open the folder in VS Code: `File > Open Folder`

### Option 2: Git Clone
```bash
git clone <repository-url>
cd Testing-all-hands
code .
```

## ⚡ Super Quick Start (3 Steps)

### Step 1: Install Prerequisites
- **Java 17+**: https://adoptium.net/temurin/releases/
- **Maven**: https://maven.apache.org/download.cgi
- **Python 3**: https://python.org/downloads/

### Step 2: Start Backend
```bash
# Windows
start-backend.bat

# Mac/Linux
./start-backend.sh
```

### Step 3: Start Frontend
```bash
# Windows
start-frontend.bat

# Mac/Linux  
./start-frontend.sh
```

## 🌐 Access the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api/health

## 🔑 Test Credentials

| Username | Password | Role |
|----------|----------|------|
| admin    | admin123 | ADMIN |
| user     | password | USER |
| demo     | demo     | USER |
| test     | test123  | USER |

## 🎯 What You'll See

1. **Beautiful Login Form**: Modern gradient design with animations
2. **Real-time Validation**: Form validation and error messages
3. **API Integration**: Frontend communicates with Java backend
4. **Success/Error Handling**: Green success, red error messages
5. **Token Management**: JWT-like tokens stored in localStorage

## 🔧 VS Code Setup (Optional but Recommended)

### Install Extensions
1. **Extension Pack for Java** (Microsoft)
2. **Spring Boot Extension Pack** (VMware)
3. **Live Server** (Ritwick Dey)
4. **REST Client** (Humao)

### Quick Test in VS Code
1. Open `api-tests.http` file
2. Click "Send Request" above any API call
3. See responses directly in VS Code

## 🐛 Troubleshooting

### Backend Won't Start
```bash
# Check Java version
java -version

# Should show Java 17 or higher
```

### Frontend Won't Load
```bash
# Try different Python command
python3 server.py 3000
python server.py 3000
py server.py 3000
```

### Port Already in Use
```bash
# Backend: Change port in backend/src/main/resources/application.properties
server.port=8081

# Frontend: Use different port
python3 server.py 3001
```

## 📱 Mobile Testing

1. Find your IP address: `ipconfig` (Windows) or `ifconfig` (Mac/Linux)
2. Access from phone: `http://YOUR_IP:3000`

## 🎉 Success Indicators

✅ Backend running: Visit http://localhost:8080/api/health shows "Login service is running"

✅ Frontend running: Visit http://localhost:3000 shows login form

✅ Integration working: Login with admin/admin123 shows green success message

## 📞 Need Help?

1. Check `LOCAL_SETUP_GUIDE.md` for detailed instructions
2. Review `README.md` for complete documentation
3. Use `test-api.sh` to verify backend functionality
4. Check browser console (F12) for frontend errors

## 🚀 Next Steps

- Customize the UI in `frontend/styles.css`
- Add new API endpoints in `backend/src/main/java/`
- Modify authentication logic in `MockAuthService.java`
- Add more frontend features in `frontend/script.js`

**Happy coding!** 🎯