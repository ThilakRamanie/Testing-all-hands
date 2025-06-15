#!/bin/bash

echo "Starting Login System..."
echo "========================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed. Please install Java 8 or higher."
    echo "Download from: https://adoptium.net/"
    exit 1
fi

# Check if Ant is installed
if ! command -v ant &> /dev/null; then
    echo "ERROR: Apache Ant is not installed. Please install Ant."
    echo "Download from: https://ant.apache.org/bindownload.cgi"
    exit 1
fi

echo "Building and starting the application..."
ant run