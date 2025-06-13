#!/bin/bash

echo "Starting Frontend Server..."

# Navigate to frontend directory
cd frontend

# Start the Python HTTP server on port 12000
echo "Starting frontend server on port 12000..."
echo "Frontend will be available at: https://work-1-agondegmivxspeyw.prod-runtime.all-hands.dev"
python3 server.py 12000