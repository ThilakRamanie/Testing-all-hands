#!/bin/bash

echo "=== Testing Login API ==="
echo

echo "1. Testing health endpoint:"
curl -s http://localhost:8080/api/health
echo -e "\n"

echo "2. Testing successful login (admin/admin123):"
RESPONSE=$(curl -s -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}')
echo $RESPONSE | python3 -m json.tool
echo

# Extract token for profile test
TOKEN=$(echo $RESPONSE | python3 -c "import sys, json; print(json.load(sys.stdin)['token'])")

echo "3. Testing failed login (invalid credentials):"
curl -s -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"invalid","password":"wrong"}' | python3 -m json.tool
echo

echo "4. Testing profile endpoint with valid token:"
curl -s -X GET http://localhost:8080/api/profile \
  -H "Authorization: Bearer $TOKEN" | python3 -m json.tool
echo

echo "5. Testing profile endpoint with invalid token:"
curl -s -X GET http://localhost:8080/api/profile \
  -H "Authorization: Bearer invalid-token" | python3 -m json.tool
echo

echo "6. Testing other mock users:"
echo "   - user/password:"
curl -s -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password"}' | python3 -m json.tool
echo

echo "   - demo/demo:"
curl -s -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo","password":"demo"}' | python3 -m json.tool
echo

echo "=== API Testing Complete ==="