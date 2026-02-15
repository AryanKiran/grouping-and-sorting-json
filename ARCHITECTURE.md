# Architecture Documentation

## System Overview

The JSON Dataset API is a RESTful web service built using Spring Boot that allows dynamic storage, retrieval, and querying of JSON data with flexible group-by and sort-by operations.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    Client Layer                          │
│  (Postman, cURL, Web Browser, Mobile App, etc.)         │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP/REST
                     ▼
┌─────────────────────────────────────────────────────────┐
│               Controller Layer                           │
│  - DatasetController (REST endpoints)                    │
│  - Request validation                                    │
│  - Response formatting                                   │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                Service Layer                             │
│  - DatasetServiceImpl (Business logic)                   │
│  - Data transformation                                   │
│  - Sorting & grouping algorithms                         │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│              Repository Layer                            │
│  - DatasetRecordRepository (Data access)                 │
│  - JPA/Hibernate abstraction                             │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│               Database Layer                             │
│  - H2 (Development/Testing)                              │
│  - PostgreSQL (Production)                               │
│  - JSON column type support                              │
└─────────────────────────────────────────────────────────┘
```

## Component Details

### 1. Controller Layer

**Responsibilities:**
- Handle HTTP requests and responses
- Route requests to appropriate service methods
- Validate input parameters
- Format responses according to API contract

**Key Classes:**
- `DatasetController`: Main REST controller

**Design Patterns:**
- REST Controller pattern
- Dependency Injection

### 2. Service Layer

**Responsibilities:**
- Implement business logic
- Perform data transformations
- Execute grouping and sorting algorithms
- Handle validation

**Key Classes:**
- `DatasetService`: Service interface
- `DatasetServiceImpl`: Service implementation

**Design Patterns:**
- Service pattern
- Strategy pattern (for sorting different data types)
- Template method (for record processing)

### 3. Repository Layer

**Responsibilities:**
- Abstract database operations
- Provide CRUD operations
- Custom query methods

**Key Classes:**
- `DatasetRecordRepository`: JPA repository interface

**Design Patterns:**
- Repository pattern
- DAO (Data Access Object) pattern

### 4. Entity Layer

**Responsibilities:**
- Map database tables to Java objects
- Define relationships
- Handle JSON serialization

**Key Classes:**
- `DatasetRecord`: Main entity representing a JSON record

**Features:**
- JSON column type for flexible schema
- Automatic timestamp management
- Indexing for performance

### 5. Exception Handling

**Responsibilities:**
- Centralized error handling
- Consistent error responses
- Meaningful error messages

**Key Classes:**
- `GlobalExceptionHandler`: Central exception handler
- Custom exception hierarchy

**Design Patterns:**
- Chain of Responsibility
- Aspect-Oriented Programming (AOP)

## Data Flow

### Insert Record Flow

```
1. Client → POST /api/dataset/{name}/record + JSON body
                ↓
2. DatasetController.insertRecord()
                ↓
3. Validate request body
                ↓
4. DatasetService.insertRecord()
                ↓
5. Create DatasetRecord entity
                ↓
6. DatasetRecordRepository.save()
                ↓
7. Database persists record with JSON column
                ↓
8. Return InsertRecordResponse to client
```

### Query with Group-By Flow

```
1. Client → GET /api/dataset/{name}/query?groupBy=field
                ↓
2. DatasetController.queryDataset()
                ↓
3. Extract groupBy parameter
                ↓
4. DatasetService.queryWithGroupBy()
                ↓
5. Fetch all records from repository
                ↓
6. Group records by specified field
                ↓
7. Transform to GroupedRecordsResponse
                ↓
8. Return grouped data to client
```

### Query with Sort-By Flow

```
1. Client → GET /api/dataset/{name}/query?sortBy=field&order=asc
                ↓
2. DatasetController.queryDataset()
                ↓
3. Extract sortBy and order parameters
                ↓
4. DatasetService.queryWithSortBy()
                ↓
5. Fetch all records from repository
                ↓
6. Apply custom comparator based on field type
                ↓
7. Sort records (ascending/descending)
                ↓
8. Return sorted data to client
```

## Database Schema

### Table: dataset_records

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated primary key |
| dataset_name | VARCHAR | Name of the dataset |
| record_data | JSON | Actual JSON record data |
| created_at | TIMESTAMP | Record creation timestamp |

**Indexes:**
- Primary key on `id`
- Index on `dataset_name` for faster queries

**JSON Column:**
The `record_data` column uses the database's native JSON type:
- H2: JSON type (stored as text)
- PostgreSQL: JSONB type (binary format, faster queries)

## Design Decisions

### 1. Why JSON Column Type?

**Pros:**
- Flexible schema - no migrations needed for new fields
- Supports varying record structures
- Native JSON operations in PostgreSQL
- Easy to query and index

**Cons:**
- Less type safety
- Potential performance impact for complex queries
- Requires more careful validation

### 2. Why In-Memory Sorting/Grouping?

**Current Approach:**
- Fetch all records and process in application

**Pros:**
- Database-agnostic (works with any SQL database)
- Flexible sorting logic (handles any data type)
- Simple to implement and test

**Cons:**
- Not suitable for very large datasets
- Memory intensive for large result sets

**Future Enhancement:**
Could push sorting to database using SQL:
```sql
SELECT * FROM dataset_records 
WHERE dataset_name = ? 
ORDER BY JSON_EXTRACT(record_data, '$.age')
```

### 3. Separate Datasets vs Single Table

**Chosen Approach:** Single table with `dataset_name` column

**Pros:**
- Simpler schema management
- Easy to query across datasets
- Single codebase for all datasets

**Cons:**
- All datasets share same table
- Potential for large table size

### 4. RESTful URL Design

**Chosen Pattern:**
```
POST   /api/dataset/{datasetName}/record
GET    /api/dataset/{datasetName}/query
```

**Rationale:**
- Resource-based URLs
- Clear hierarchy (dataset → records)
- RESTful conventions
- Easy to understand and use

## Security Considerations

### Current Implementation

- No authentication/authorization (assignment requirement)
- Input validation through Spring Validation
- SQL injection protection through JPA
- JSON injection protection through Jackson

### Production Enhancements

For production deployment, consider:

1. **Authentication:**
   - JWT tokens
   - OAuth2
   - API keys

2. **Authorization:**
   - Role-based access control (RBAC)
   - Dataset-level permissions
   - Rate limiting

3. **Data Protection:**
   - HTTPS/TLS encryption
   - Database encryption at rest
   - Field-level encryption for sensitive data

4. **Input Validation:**
   - Size limits on JSON payloads
   - Field whitelisting
   - SQL injection prevention

## Performance Considerations

### Current Performance

- **Insert:** O(1) - Direct database insert
- **Group-By:** O(n) - Linear scan of records
- **Sort-By:** O(n log n) - Java sort algorithm

### Optimization Strategies

1. **Caching:**
   - Cache frequently accessed datasets
   - Use Redis for distributed caching
   - Implement cache invalidation

2. **Pagination:**
   - Add pagination to query endpoints
   - Reduce memory usage for large datasets
   - Improve response times

3. **Database Indexing:**
   - Add JSON indexes for frequently queried fields
   - Composite indexes for dataset_name + field
   - Regular index maintenance

4. **Async Processing:**
   - Queue large inserts
   - Background processing for heavy queries
   - Non-blocking I/O

## Testing Strategy

### Test Pyramid

```
        ┌────────────────┐
        │   E2E Tests    │  (1-2 tests)
        │  Integration   │
        ├────────────────┤
        │  Controller    │  (5-10 tests)
        │  Service Tests │
        ├────────────────┤
        │   Unit Tests   │  (20-30 tests)
        │  Repository    │
        └────────────────┘
```

### Test Coverage

- **Repository:** Tests JPA operations and queries
- **Service:** Tests business logic with mocked dependencies
- **Controller:** Tests HTTP endpoints with MockMvc
- **Integration:** Tests complete workflows end-to-end

## Scalability

### Horizontal Scaling

The application is designed to be stateless and can scale horizontally:

1. **Multiple Instances:**
   - Run multiple instances behind a load balancer
   - No session state required
   - Shared database

2. **Database Scaling:**
   - Read replicas for query operations
   - Write master for inserts
   - Connection pooling

### Vertical Scaling

- Increase JVM heap size for larger datasets
- Optimize garbage collection
- Use connection pooling

## Monitoring & Observability

### Recommended Tools

1. **Logging:**
   - SLF4J with Logback
   - Structured logging (JSON format)
   - Centralized log aggregation (ELK stack)

2. **Metrics:**
   - Spring Boot Actuator
   - Micrometer
   - Prometheus + Grafana

3. **Tracing:**
   - Spring Cloud Sleuth
   - Zipkin
   - Distributed tracing

## Future Enhancements

### Short Term (1-3 months)

1. Pagination support
2. Search/filter capabilities
3. Bulk insert operations
4. Export to CSV/Excel
5. API documentation (Swagger/OpenAPI)

### Medium Term (3-6 months)

1. Authentication & authorization
2. Multi-tenancy support
3. Data validation rules per dataset
4. Webhooks for data changes
5. GraphQL API

### Long Term (6+ months)

1. Real-time data streaming
2. Analytics dashboard
3. Machine learning integration
4. Advanced query language
5. Time-series data support

## Deployment

### Development
```bash
mvn spring-boot:run
```

### Production (Docker)
```dockerfile
FROM openjdk:17-slim
COPY target/json-dataset-api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Production (Kubernetes)
- Horizontal Pod Autoscaling
- ConfigMaps for configuration
- Secrets for sensitive data
- Persistent volumes for database

## Conclusion

This architecture provides a solid foundation for a flexible JSON dataset management system. The layered architecture ensures separation of concerns, while the design patterns employed make the code maintainable and extensible. The use of JSON columns provides schema flexibility, and the Spring Boot framework ensures production-ready features out of the box.
