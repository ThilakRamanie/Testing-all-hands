@echo off
echo Starting Frontend Server...

REM Navigate to frontend directory
cd frontend

REM Start the Python HTTP server on port 3000
echo Starting frontend server on port 3000...
echo Frontend will be available at: http://localhost:3000
python server.py 3000