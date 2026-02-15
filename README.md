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
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "John Doe",
    "age": 30,
    "department": "Engineering",
    "salary": 95000
  }'
```

**Response:**
```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

### Group By Department
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

**Response:**
```json
{
  "groupedRecords": {
    "Engineering": [
      {"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"},
      {"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"}
    ],
    "Marketing": [
      {"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"}
    ]
  }
}
```

### Sort By Age
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
```

**Response:**
```json
{
  "sortedRecords": [
    {"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"},
    {"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"},
    {"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"}
  ]
}
```

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
