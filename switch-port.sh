#!/bin/bash

if [ "$1" = "12000" ]; then
    echo "Switching to port 12000 (work-1 URL)..."
    sed -i 's/private static final int PORT = 12001;/private static final int PORT = 12000;/' src/main/java/com/example/login/LoginServer.java
    echo "✅ Switched to port 12000"
    echo "URL: https://work-1-agondegmivxspeyw.prod-runtime.all-hands.dev"
elif [ "$1" = "12001" ]; then
    echo "Switching to port 12001 (work-2 URL)..."
    sed -i 's/private static final int PORT = 12000;/private static final int PORT = 12001;/' src/main/java/com/example/login/LoginServer.java
    echo "✅ Switched to port 12001"
    echo "URL: https://work-2-agondegmivxspeyw.prod-runtime.all-hands.dev"
else
    echo "Usage: ./switch-port.sh [12000|12001]"
    echo "Current port: $(grep 'private static final int PORT' src/main/java/com/example/login/LoginServer.java | grep -o '[0-9]*')"
fi

echo ""
echo "To restart server: pkill -f LoginServer && ant run"