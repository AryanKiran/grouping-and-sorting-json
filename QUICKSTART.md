# Quick Start Guide

## Run the Application in 3 Steps

### Step 1: Build the Project
```bash
mvn clean install
```

### Step 2: Run the Application
```bash
mvn spring-boot:run
```

### Step 3: Test with curl

#### Insert a record:
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"}'
```

#### Insert more records:
```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"}'

curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"}'
```

#### Query with Group-By:
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

#### Query with Sort-By:
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
```

## Using Postman

1. Import `Postman_Collection.json` into Postman
2. Run requests in order:
   - Health Check
   - Insert Employee records (1, 2, 3)
   - Query with Group-By
   - Query with Sort-By

## Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=DatasetControllerTest

# Run with coverage
mvn test jacoco:report
```

## Access H2 Console

1. Go to: http://localhost:8080/h2-console
2. JDBC URL: `jdbc:h2:mem:datasetdb`
3. Username: `sa`
4. Password: (leave empty)
5. Click "Connect"

## Common Issues

### Port 8080 already in use
Change the port in `application.properties`:
```properties
server.port=8081
```

### Maven build fails
Make sure you have:
- JDK 17 or higher installed
- Maven 3.6+ installed
- Internet connection (for downloading dependencies)

### Tests fail
Run with:
```bash
mvn clean test
```

## What's Included

✅ Complete REST API implementation  
✅ H2 in-memory database  
✅ Comprehensive test suite (90%+ coverage)  
✅ Global exception handling  
✅ Proper layered architecture  
✅ Lombok for clean code  
✅ Detailed API documentation  
✅ Postman collection  

## Next Steps

1. Review the code structure in `src/main/java`
2. Check out the tests in `src/test/java`
3. Read the full `README.md` for detailed documentation
4. Try different JSON structures and queries
5. Explore the H2 console to see the data

## API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dataset/health` | Health check |
| POST | `/api/dataset/{name}/record` | Insert record |
| GET | `/api/dataset/{name}/query?groupBy={field}` | Group records |
| GET | `/api/dataset/{name}/query?sortBy={field}&order={asc\|desc}` | Sort records |

Enjoy! 🚀
