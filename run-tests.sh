#!/bin/bash

echo "================================"
echo "Login System - Unit Test Runner"
echo "================================"
echo ""

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Ant is available
if ! command -v ant &> /dev/null; then
    echo "Error: Apache Ant is not installed or not in PATH"
    exit 1
fi

echo "Running unit tests..."
echo ""

# Run the tests
ant test

echo ""
echo "Test Summary:"
echo "============="

# Count test results from reports
if [ -d "build/test-reports" ]; then
    # Simple count from the console output above
    total_tests=$(grep -h "Tests run:" build/test-reports/*.txt | awk -F',' '{tests += substr($1, index($1, ":")+1)} END {print tests}')
    total_failures=$(grep -h "Failures:" build/test-reports/*.txt | awk -F',' '{failures += substr($2, index($2, ":")+1)} END {print failures}')
    total_errors=$(grep -h "Errors:" build/test-reports/*.txt | awk -F',' '{errors += substr($3, index($3, ":")+1)} END {print errors}')
    
    # Set defaults if empty
    total_tests=${total_tests:-0}
    total_failures=${total_failures:-0}
    total_errors=${total_errors:-0}
    
    echo "Total Tests: $total_tests"
    echo "Failures: $total_failures"
    echo "Errors: $total_errors"
    
    if [ "$total_failures" -eq 0 ] && [ "$total_errors" -eq 0 ] && [ "$total_tests" -gt 0 ]; then
        echo ""
        echo "✅ All tests passed!"
    elif [ "$total_tests" -eq 0 ]; then
        echo ""
        echo "⚠️ No tests found or executed."
    else
        echo ""
        echo "❌ Some tests failed. Check reports in build/test-reports/"
    fi
else
    echo "No test reports found."
fi

echo ""
echo "Test reports available in: build/test-reports/"
echo "================================"