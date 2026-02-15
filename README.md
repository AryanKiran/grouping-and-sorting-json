# JSON Dataset API

A Spring Boot REST API application for managing JSON dataset records with dynamic group-by and sort-by operations.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Database Configuration](#database-configuration)
- [Project Structure](#project-structure)
- [Design Patterns & Best Practices](#design-patterns--best-practices)

## ✨ Features

- **Insert JSON Records**: Store flexible JSON data in datasets
- **Group-By Operations**: Group records by any field dynamically
- **Sort-By Operations**: Sort records by any field in ascending or descending order
- **Multiple Datasets**: Support for managing multiple independent datasets
- **Flexible Schema**: No predefined schema required for JSON records
- **REST Compliant**: Clean, RESTful API design
- **Comprehensive Testing**: Unit, integration, and end-to-end tests
- **Error Handling**: Robust error handling with meaningful error messages
- **H2 & PostgreSQL Support**: Works with H2 (development) and PostgreSQL (production)

## 🛠 Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **H2 Database** (Development/Testing)
- **PostgreSQL** (Production-ready)
- **Lombok** (Boilerplate reduction)
- **JUnit 5** & **Mockito** (Testing)
- **Maven** (Build tool)

## 📦 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- (Optional) PostgreSQL 12 or higher for production use

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd json-dataset-api
```

### 2. Build the Project

```bash
mvn clean install
```

This will:
- Compile the code
- Run all tests
- Package the application as a JAR file

### 3. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/json-dataset-api-1.0.0.jar
```

The application will start on `http://localhost:8080`

### 4. Verify the Application

Check the health endpoint:

```bash
curl http://localhost:8080/api/dataset/health
```

Expected response:
```json
{
  "status": "UP",
  "service": "JSON Dataset API"
}
```

### 5. Access H2 Console (Development Only)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:datasetdb`
- Username: `sa`
- Password: (leave empty)

## 📚 API Documentation

### Base URL

```
http://localhost:8080/api/dataset
```

### Endpoints

#### 1. Insert Record

**Endpoint:** `POST /api/dataset/{datasetName}/record`

**Description:** Insert a JSON record into a specific dataset.

**Request:**
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "John Doe",
    "age": 30,
    "department": "Engineering"
  }'
```

**Response:** (201 Created)
```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

#### 2. Query with Group-By

**Endpoint:** `GET /api/dataset/{datasetName}/query?groupBy={field}`

**Description:** Group records by a specific field.

**Request:**
```bash
curl http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department
```

**Response:** (200 OK)
```json
{
  "groupedRecords": {
    "Engineering": [
      {
        "id": 1,
        "name": "John Doe",
        "age": 30,
        "department": "Engineering"
      },
      {
        "id": 2,
        "name": "Jane Smith",
        "age": 25,
        "department": "Engineering"
      }
    ],
    "Marketing": [
      {
        "id": 3,
        "name": "Alice Brown",
        "age": 28,
        "department": "Marketing"
      }
    ]
  }
}
```

#### 3. Query with Sort-By

**Endpoint:** `GET /api/dataset/{datasetName}/query?sortBy={field}&order={asc|desc}`

**Description:** Sort records by a specific field.

**Request:**
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
```

**Response:** (200 OK)
```json
{
  "sortedRecords": [
    {
      "id": 2,
      "name": "Jane Smith",
      "age": 25,
      "department": "Engineering"
    },
    {
      "id": 3,
      "name": "Alice Brown",
      "age": 28,
      "department": "Marketing"
    },
    {
      "id": 1,
      "name": "John Doe",
      "age": 30,
      "department": "Engineering"
    }
  ]
}
```

**Parameters:**
- `sortBy`: Field name to sort by (required)
- `order`: Sort order - `asc` (default) or `desc` (optional)

### Error Responses

All errors follow a consistent format:

```json
{
  "error": "Error Type",
  "message": "Detailed error message",
  "status": 400,
  "timestamp": "2025-02-15T10:30:00"
}
```

**Common HTTP Status Codes:**
- `200 OK`: Successful query
- `201 Created`: Record inserted successfully
- `400 Bad Request`: Invalid request data or parameters
- `404 Not Found`: Dataset not found
- `500 Internal Server Error`: Server-side error

## 🧪 Testing

The project includes comprehensive test coverage:

### Run All Tests

```bash
mvn test
```

### Run Specific Test Classes

```bash
# Repository tests
mvn test -Dtest=DatasetRecordRepositoryTest

# Service tests
mvn test -Dtest=DatasetServiceImplTest

# Controller tests
mvn test -Dtest=DatasetControllerTest

# Integration tests
mvn test -Dtest=DatasetIntegrationTest
```

### Test Coverage

- **Repository Tests**: JPA repository operations
- **Service Tests**: Business logic with mocked dependencies
- **Controller Tests**: REST endpoints with MockMvc
- **Integration Tests**: End-to-end scenarios

### Example Postman Collection

Import the following scenarios into Postman:

**1. Insert Employee Records**
```
POST http://localhost:8080/api/dataset/employee_dataset/record
Content-Type: application/json

{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "department": "Engineering",
  "salary": 95000
}
```

**2. Group by Department**
```
GET http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department
```

**3. Sort by Age (Ascending)**
```
GET http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc
```

**4. Sort by Name (Descending)**
```
GET http://localhost:8080/api/dataset/employee_dataset/query?sortBy=name&order=desc
```

## 🗄️ Database Configuration

### Development (H2)

The application uses H2 in-memory database by default (already configured in `application.properties`).

### Production (PostgreSQL)

To use PostgreSQL, create `application-prod.properties`:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/datasetdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Run with production profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📁 Project Structure

```
json-dataset-api/
├── src/
│   ├── main/
│   │   ├── java/com/assignment/dataset/
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   └── DatasetController.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── DatasetService.java
│   │   │   │   └── DatasetServiceImpl.java
│   │   │   ├── repository/          # Data Access
│   │   │   │   └── DatasetRecordRepository.java
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   └── DatasetRecord.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   └── DatasetDTO.java
│   │   │   ├── exception/           # Custom Exceptions
│   │   │   │   ├── DatasetException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── JsonDatasetApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/assignment/dataset/
│       │   ├── controller/
│       │   │   └── DatasetControllerTest.java
│       │   ├── service/
│       │   │   └── DatasetServiceImplTest.java
│       │   ├── repository/
│       │   │   └── DatasetRecordRepositoryTest.java
│       │   └── DatasetIntegrationTest.java
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

## 🏗️ Design Patterns & Best Practices

### 1. **Layered Architecture**
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Data access abstraction
- **Entity Layer**: Database models

### 2. **Dependency Injection**
- Constructor-based injection using Lombok's `@RequiredArgsConstructor`
- Promotes testability and loose coupling

### 3. **Exception Handling**
- Global exception handler using `@RestControllerAdvice`
- Custom exception hierarchy for specific error scenarios
- Consistent error response format

### 4. **Builder Pattern**
- Used in DTOs and entities for clean object construction
- Implemented via Lombok's `@Builder`

### 5. **Repository Pattern**
- Spring Data JPA repositories for data access
- Clean separation of concerns

### 6. **DTO Pattern**
- Separate request/response objects from entities
- API contract independence from domain model

### 7. **Test-Driven Development (TDD)**
- Comprehensive unit tests
- Integration tests for complete workflows
- Mocking for isolated testing

### 8. **SOLID Principles**
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible through interfaces
- **Liskov Substitution**: Service interface implementation
- **Interface Segregation**: Focused interfaces
- **Dependency Inversion**: Depends on abstractions

### 9. **RESTful Best Practices**
- Resource-based URLs
- Appropriate HTTP methods and status codes
- Meaningful error messages
- Consistent response structure

### 10. **Code Quality**
- Lombok for reduced boilerplate
- SLF4J logging for observability
- Validation and error handling
- Clear naming conventions

## 🔧 Configuration Options

### Application Properties

Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:datasetdb
spring.jpa.hibernate.ddl-auto=create-drop

# JSON Configuration
spring.jackson.serialization.indent-output=true

# Logging Levels
logging.level.com.assignment.dataset=INFO
```

## 📝 Notes

### Supported Field Types for Sorting
- **Numeric**: Integer, Long, Double, Float
- **String**: Alphabetical sorting
- **Date/Time**: If stored as comparable types
- **Boolean**: false < true

### Group-By Behavior
- Records without the specified field are grouped under "null"
- Grouping preserves record order within groups

### Performance Considerations
- For large datasets, consider pagination
- Add database indexes for frequently queried fields
- Use caching for repeated queries

## 🤝 Contributing

1. Follow existing code style and patterns
2. Write tests for new features
3. Update documentation as needed
4. Ensure all tests pass before committing

## 📄 License

This project is created for assignment purposes.

## 👥 Author

Created as a backend assignment demonstrating Spring Boot best practices.

---

**Need help?** Check the logs in the console or enable DEBUG logging for more details.
