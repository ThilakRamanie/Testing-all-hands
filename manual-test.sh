#!/bin/bash

# Manual Testing Script for Login System
# This script helps automate some manual testing steps

echo "🧪 Login System Manual Testing Helper"
echo "====================================="

# Check if server is running
PORT=$(grep 'private static final int PORT' src/main/java/com/example/login/LoginServer.java | grep -o '[0-9]*')
echo "📡 Checking server on port $PORT..."

if curl -s "http://localhost:$PORT/api/health" > /dev/null; then
    echo "✅ Server is running on port $PORT"
else
    echo "❌ Server is not running. Start it with: ant run"
    exit 1
fi

echo ""
echo "🔍 Testing API Endpoints:"
echo "========================"

# Test health endpoint
echo "1. Health Check:"
curl -s "http://localhost:$PORT/api/health" | python3 -m json.tool 2>/dev/null || echo "Failed to get health status"

echo ""
echo "2. Valid Login Test (demo/demo):"
curl -s -X POST "http://localhost:$PORT/api/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo"}' | python3 -m json.tool 2>/dev/null || echo "Failed to test valid login"

echo ""
echo "3. Invalid Login Test (invalid/wrong):"
curl -s -X POST "http://localhost:$PORT/api/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"invalid","password":"wrong"}' | python3 -m json.tool 2>/dev/null || echo "Failed to test invalid login"

echo ""
echo "4. Admin Login Test (admin/admin123):"
curl -s -X POST "http://localhost:$PORT/api/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | python3 -m json.tool 2>/dev/null || echo "Failed to test admin login"

echo ""
echo "5. Manager Login Test (manager/manager123):"
curl -s -X POST "http://localhost:$PORT/api/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"manager","password":"manager123"}' | python3 -m json.tool 2>/dev/null || echo "Failed to test manager login"

echo ""
echo "🌐 Browser Testing URLs:"
echo "======================="
echo "Local: http://localhost:$PORT"

if [ "$PORT" = "12000" ]; then
    echo "All Hands: https://work-1-agondegmivxspeyw.prod-runtime.all-hands.dev"
elif [ "$PORT" = "12001" ]; then
    echo "All Hands: https://work-2-agondegmivxspeyw.prod-runtime.all-hands.dev"
fi

echo ""
echo "📋 Manual Testing Checklist:"
echo "============================"
echo "□ Open browser and navigate to the URL above"
echo "□ Test demo user buttons (Admin, Demo, Test, Manager)"
echo "□ Test valid login: admin/admin123"
echo "□ Test invalid login: invalid/wrong"
echo "□ Test empty fields validation"
echo "□ Test logout functionality (Escape key)"
echo "□ Test responsive design (resize browser)"
echo "□ Check browser console for errors (F12)"
echo "□ Test keyboard shortcuts (Ctrl+L, Enter, Escape)"
echo "□ Test session persistence (refresh page after login)"

echo ""
echo "🔧 Test Users Available:"
echo "======================="
echo "• admin / admin123 (ADMIN)"
echo "• demo / demo (USER)"
echo "• test / test123 (USER)"
echo "• user / password (USER)"
echo "• manager / manager123 (MANAGER)"

echo ""
echo "✅ API tests completed. Now test the UI manually in your browser!"