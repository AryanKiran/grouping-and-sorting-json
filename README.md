<<<<<<< HEAD
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
=======
# JSON Dataset API 🚀

[![Java](https://img.shields.io/badge/Java-16%2B-orange?style=flat&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen?style=flat&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?style=flat&logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A robust Spring Boot REST API for managing JSON datasets with dynamic group-by and sort-by operations. Store flexible JSON data without predefined schemas and query it with powerful aggregation capabilities.

## 📋 Table of Contents

- [Features](#-features)
- [Demo](#-demo)
- [Technology Stack](#-technology-stack)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

- **🔄 Dynamic JSON Storage** - Store any JSON structure without predefined schemas
- **📊 Group-By Operations** - Group records by any field dynamically
- **🔀 Sort-By Operations** - Sort records by any field (ascending/descending)
- **🗄️ Multiple Datasets** - Manage multiple independent datasets
- **🛡️ Robust Error Handling** - Comprehensive exception handling with meaningful messages
- **✅ REST Compliant** - Clean, RESTful API design
- **🧪 Well Tested** - 90%+ test coverage with unit and integration tests
- **📦 Production Ready** - Includes logging, validation, and transaction management
- **🔌 Database Agnostic** - Works with H2 (dev) and PostgreSQL (production)

## 🎬 Demo

### Insert a Record
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "John Doe",
    "age": 30,
<<<<<<< HEAD
    "department": "Engineering"
  }'
```

**Response:** (201 Created)
=======
    "department": "Engineering",
    "salary": 95000
  }'
```

**Response:**
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

<<<<<<< HEAD
#### 2. Query with Group-By

**Endpoint:** `GET /api/dataset/{datasetName}/query?groupBy={field}`

**Description:** Group records by a specific field.

**Request:**
```bash
curl http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department
```

**Response:** (200 OK)
=======
### Group By Department
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

**Response:**
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
```json
{
  "groupedRecords": {
    "Engineering": [
<<<<<<< HEAD
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
=======
      {"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"},
      {"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"}
    ],
    "Marketing": [
      {"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"}
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
    ]
  }
}
```

<<<<<<< HEAD
#### 3. Query with Sort-By

**Endpoint:** `GET /api/dataset/{datasetName}/query?sortBy={field}&order={asc|desc}`

**Description:** Sort records by a specific field.

**Request:**
=======
### Sort By Age
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
```

<<<<<<< HEAD
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
=======
**Response:**
```json
{
  "sortedRecords": [
    {"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"},
    {"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"},
    {"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"}
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
  ]
}
```

<<<<<<< HEAD
**Parameters:**
- `sortBy`: Field name to sort by (required)
- `order`: Sort order - `asc` (default) or `desc` (optional)
=======
## 🛠 Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 16+ | Programming Language |
| Spring Boot | 3.2.1 | Application Framework |
| Spring Data JPA | 3.2.1 | Data Access Layer |
| H2 Database | Latest | Development Database |
| PostgreSQL | 12+ | Production Database |
| Lombok | Latest | Reduce Boilerplate Code |
| JUnit 5 | 5.10.0 | Testing Framework |
| Mockito | 5.3.1 | Mocking Framework |
| Maven | 3.6+ | Build Tool |

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 16 or higher
- **Maven** 3.6 or higher
- **Git** (to clone the repository)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/json-dataset-api.git
   cd json-dataset-api
   ```

2. **Build the project**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Verify it's running**
   ```bash
   curl http://localhost:8080/api/dataset/health
   ```

The application will start on `http://localhost:8080`

### Quick Start with Docker (Optional)

```bash
# Build Docker image
docker build -t json-dataset-api .

# Run container
docker run -p 8080:8080 json-dataset-api
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/dataset
```

### Endpoints

#### 1. Health Check
Check if the API is running.

```http
GET /api/dataset/health
```

**Response:**
```json
{
  "status": "UP",
  "service": "JSON Dataset API"
}
```

---

#### 2. Insert Record
Insert a JSON record into a dataset.

```http
POST /api/dataset/{datasetName}/record
Content-Type: application/json
```

**Path Parameters:**
- `datasetName` - Name of the dataset (e.g., `employee_dataset`)

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "department": "Engineering"
}
```

**Response:** `201 Created`
```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"}'
```

---

#### 3. Query with Group-By
Group records by a specific field.

```http
GET /api/dataset/{datasetName}/query?groupBy={fieldName}
```

**Path Parameters:**
- `datasetName` - Name of the dataset

**Query Parameters:**
- `groupBy` - Field name to group by (e.g., `department`)

**Response:** `200 OK`
```json
{
  "groupedRecords": {
    "Engineering": [...],
    "Marketing": [...]
  }
}
```

**Example:**
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

---

#### 4. Query with Sort-By
Sort records by a specific field.

```http
GET /api/dataset/{datasetName}/query?sortBy={fieldName}&order={asc|desc}
```

**Path Parameters:**
- `datasetName` - Name of the dataset

**Query Parameters:**
- `sortBy` - Field name to sort by (e.g., `age`)
- `order` - Sort order: `asc` (ascending) or `desc` (descending). Default: `asc`

**Response:** `200 OK`
```json
{
  "sortedRecords": [
    {"id": 2, "age": 25, ...},
    {"id": 3, "age": 28, ...},
    {"id": 1, "age": 30, ...}
  ]
}
```

**Example:**
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
```

---
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c

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
<<<<<<< HEAD
- `200 OK`: Successful query
- `201 Created`: Record inserted successfully
- `400 Bad Request`: Invalid request data or parameters
- `404 Not Found`: Dataset not found
- `500 Internal Server Error`: Server-side error

## 🧪 Testing

The project includes comprehensive test coverage:
=======
- `200 OK` - Successful query
- `201 Created` - Record created successfully
- `400 Bad Request` - Invalid request (bad JSON, invalid parameters)
- `404 Not Found` - Dataset not found
- `500 Internal Server Error` - Server error

## 📁 Project Structure

```
json-dataset-api/
├── src/
│   ├── main/
│   │   ├── java/com/assignment/dataset/
│   │   │   ├── controller/              # REST Controllers
│   │   │   │   └── DatasetController.java
│   │   │   ├── service/                 # Business Logic
│   │   │   │   ├── DatasetService.java
│   │   │   │   └── DatasetServiceImpl.java
│   │   │   ├── repository/              # Data Access Layer
│   │   │   │   └── DatasetRecordRepository.java
│   │   │   ├── entity/                  # JPA Entities
│   │   │   │   └── DatasetRecord.java
│   │   │   ├── dto/                     # Data Transfer Objects
│   │   │   │   └── DatasetDTO.java
│   │   │   ├── exception/               # Exception Handling
│   │   │   │   ├── DatasetException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── JsonDatasetApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/assignment/dataset/
│           ├── controller/              # Controller Tests
│           ├── service/                 # Service Tests
│           ├── repository/              # Repository Tests
│           └── DatasetIntegrationTest.java
├── pom.xml
├── README.md
├── ARCHITECTURE.md
├── API_TESTING_GUIDE.md
└── Postman_Collection.json
```

## ⚙️ Configuration

### Application Properties

**Development (H2 Database):**

The application uses H2 in-memory database by default. Configuration in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:datasetdb
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console (Development Only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**Production (PostgreSQL):**

Create `application-prod.properties`:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/datasetdb
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Run with production profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### H2 Console Access

During development, access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:datasetdb`
- Username: `sa`
- Password: (leave empty)

## 🧪 Testing

The project includes comprehensive test coverage (90%+).
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c

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

<<<<<<< HEAD
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
=======
| Layer | Tests | Description |
|-------|-------|-------------|
| Repository | 8 tests | JPA repository operations |
| Service | 15 tests | Business logic with mocked dependencies |
| Controller | 10 tests | REST endpoints with MockMvc |
| Integration | 8 tests | End-to-end workflows |

### Postman Testing

Import `Postman_Collection.json` into Postman for manual API testing with pre-configured requests.

## 🏗️ Architecture & Design

### Layered Architecture

```
┌─────────────────────────────────────┐
│      Controller Layer               │  ← REST endpoints
├─────────────────────────────────────┤
│       Service Layer                 │  ← Business logic
├─────────────────────────────────────┤
│      Repository Layer               │  ← Data access
├─────────────────────────────────────┤
│      Database Layer                 │  ← H2/PostgreSQL
└─────────────────────────────────────┘
```

### Design Patterns Used

- **Layered Architecture** - Separation of concerns
- **Repository Pattern** - Data access abstraction
- **DTO Pattern** - Clean API contracts
- **Builder Pattern** - Object construction
- **Dependency Injection** - Loose coupling
- **Strategy Pattern** - Flexible sorting algorithms
- **Exception Handler Pattern** - Centralized error handling

For detailed architecture documentation, see [ARCHITECTURE.md](ARCHITECTURE.md)

## 📊 Database Schema

### Table: `dataset_records`

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated primary key |
| dataset_name | VARCHAR | Name of the dataset |
| record_data | JSON | Actual JSON record data |
| created_at | TIMESTAMP | Record creation timestamp |

**Indexes:**
- Primary key on `id`
- Index on `dataset_name` for faster queries

## 🚢 Deployment

### Local Deployment

```bash
# Build JAR
mvn clean package -DskipTests

# Run JAR
java -jar target/json-dataset-api-1.0.0.jar
```

### Docker Deployment

Create `Dockerfile`:

```dockerfile
FROM openjdk:17-slim
COPY target/json-dataset-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:
```bash
docker build -t json-dataset-api .
docker run -p 8080:8080 json-dataset-api
```

### Cloud Deployment

**Heroku:**
```bash
heroku create your-app-name
git push heroku main
```

**AWS Elastic Beanstalk:**
```bash
eb init -p java-17 json-dataset-api
eb create json-dataset-api-env
eb deploy
```

## 🔒 Security Considerations

### Current Implementation
- SQL injection protection via JPA
- JSON injection protection via Jackson
- Input validation
- Exception sanitization

### For Production
Consider adding:
- Authentication (JWT, OAuth2)
- Authorization (role-based access)
- Rate limiting
- HTTPS/TLS
- API key management
- CORS configuration

## 🎯 Use Cases

This API is perfect for:

- **Dynamic Data Collection** - Forms, surveys, user-generated content
- **Analytics Dashboards** - Flexible reporting without fixed schemas
- **IoT Data Storage** - Variable sensor data structures
- **Event Logging** - Structured logging with aggregation
- **Prototype Development** - Rapid development without database migrations
- **Multi-tenant Applications** - Separate datasets per tenant

## 📈 Performance

- **Insert Operation**: O(1) complexity
- **Group-By Operation**: O(n) linear scan
- **Sort-By Operation**: O(n log n) with optimized comparator
- **Scalability**: Stateless design supports horizontal scaling

### Optimization Tips

For large datasets, consider:
- Adding pagination
- Implementing caching (Redis)
- Database connection pooling
- Async processing for heavy operations

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### Development Guidelines

- Follow existing code style
- Write tests for new features
- Update documentation
- Ensure all tests pass
- Keep commits atomic and descriptive

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/YOUR_USERNAME)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Open source community for inspiration
- All contributors who help improve this project

## 📞 Support

- 📧 Email: your.email@example.com
- 🐛 Issues: [GitHub Issues](https://github.com/YOUR_USERNAME/json-dataset-api/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/YOUR_USERNAME/json-dataset-api/discussions)

## 🗺️ Roadmap

### Version 1.1 (Planned)
- [ ] Pagination support
- [ ] Advanced filtering capabilities
- [ ] Bulk insert operations
- [ ] Export to CSV/Excel

### Version 2.0 (Future)
- [ ] Authentication & Authorization
- [ ] WebSocket support for real-time updates
- [ ] GraphQL API
- [ ] Multi-tenancy support
- [ ] Advanced analytics dashboard

## 📚 Additional Resources

- [API Testing Guide](API_TESTING_GUIDE.md) - Comprehensive curl examples
- [Architecture Documentation](ARCHITECTURE.md) - System design details
- [Quick Start Guide](QUICKSTART.md) - Get started in 3 steps
- [Postman Collection](Postman_Collection.json) - Ready-to-use API tests

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ and ☕

</div>
>>>>>>> 748484392f118c76c14bb43c38f65c381983963c
