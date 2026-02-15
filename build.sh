#!/bin/bash

# JSON Dataset API - Build and Test Script
# This script provides various commands to build, test, and run the application

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}  JSON Dataset API - Build Script${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

# Function to check if Maven is installed
check_maven() {
    if ! command -v mvn &> /dev/null; then
        echo -e "${RED}Maven is not installed!${NC}"
        echo -e "${YELLOW}Please install Maven 3.6+ to build this project${NC}"
        echo "Installation:"
        echo "  Ubuntu/Debian: sudo apt-get install maven"
        echo "  macOS: brew install maven"
        echo "  Or download from: https://maven.apache.org/download.cgi"
        exit 1
    fi
    echo -e "${GREEN}✓ Maven found:${NC} $(mvn -version | head -1)"
}

# Function to check Java version
check_java() {
    if ! command -v java &> /dev/null; then
        echo -e "${RED}Java is not installed!${NC}"
        exit 1
    fi
    
    java_version=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$java_version" -lt 17 ]; then
        echo -e "${RED}Java 17 or higher is required!${NC}"
        echo -e "${YELLOW}Current version:${NC} $(java -version 2>&1 | head -1)"
        exit 1
    fi
    echo -e "${GREEN}✓ Java found:${NC} $(java -version 2>&1 | head -1)"
}

# Show menu
show_menu() {
    echo ""
    echo "Available commands:"
    echo "  1. build       - Clean and build the project"
    echo "  2. test        - Run all tests"
    echo "  3. run         - Run the application"
    echo "  4. package     - Create executable JAR"
    echo "  5. quick-test  - Run a quick integration test"
    echo "  6. check       - Check prerequisites"
    echo "  7. help        - Show this menu"
    echo ""
}

# Build project
build_project() {
    echo -e "${YELLOW}Building project...${NC}"
    mvn clean install -DskipTests
    echo -e "${GREEN}✓ Build completed successfully!${NC}"
}

# Run tests
run_tests() {
    echo -e "${YELLOW}Running tests...${NC}"
    mvn test
    echo -e "${GREEN}✓ All tests passed!${NC}"
}

# Run application
run_application() {
    echo -e "${YELLOW}Starting application...${NC}"
    echo -e "${GREEN}Application will be available at: http://localhost:8080${NC}"
    echo -e "${YELLOW}Press Ctrl+C to stop${NC}"
    echo ""
    mvn spring-boot:run
}

# Package application
package_application() {
    echo -e "${YELLOW}Creating executable JAR...${NC}"
    mvn clean package -DskipTests
    echo -e "${GREEN}✓ JAR created at: target/json-dataset-api-1.0.0.jar${NC}"
    echo ""
    echo "Run with: java -jar target/json-dataset-api-1.0.0.jar"
}

# Quick integration test
quick_test() {
    echo -e "${YELLOW}Starting application for quick test...${NC}"
    
    # Start application in background
    mvn spring-boot:run &
    APP_PID=$!
    
    # Wait for application to start
    echo "Waiting for application to start..."
    sleep 10
    
    # Test health endpoint
    echo -e "${YELLOW}Testing health endpoint...${NC}"
    curl -s http://localhost:8080/api/dataset/health || echo "Failed to connect"
    
    # Stop application
    kill $APP_PID
    echo -e "${GREEN}✓ Quick test completed${NC}"
}

# Main script
case "${1:-help}" in
    build)
        check_java
        check_maven
        build_project
        ;;
    test)
        check_java
        check_maven
        run_tests
        ;;
    run)
        check_java
        check_maven
        run_application
        ;;
    package)
        check_java
        check_maven
        package_application
        ;;
    quick-test)
        check_java
        check_maven
        quick_test
        ;;
    check)
        check_java
        check_maven
        echo -e "${GREEN}✓ All prerequisites are met!${NC}"
        ;;
    help|*)
        check_java
        show_menu
        ;;
esac
