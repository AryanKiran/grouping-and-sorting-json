# API Testing Guide - curl Commands

This guide provides ready-to-use curl commands to test all API endpoints.

## Prerequisites

1. Start the application:
```bash
mvn spring-boot:run
```

2. Wait for the application to start (look for "Started JsonDatasetApplication")

## 1. Health Check

Test if the API is running:

```bash
curl http://localhost:8080/api/dataset/health
```

**Expected Response:**
```json
{
  "status": "UP",
  "service": "JSON Dataset API"
}
```

---

## 2. Insert Records - Employee Dataset

### Insert Employee 1 (John Doe)

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

**Expected Response:**
```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

### Insert Employee 2 (Jane Smith)

```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 2,
    "name": "Jane Smith",
    "age": 25,
    "department": "Engineering"
  }'
```

### Insert Employee 3 (Alice Brown)

```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 3,
    "name": "Alice Brown",
    "age": 28,
    "department": "Marketing"
  }'
```

### Insert Employee 4 (Bob Wilson)

```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 4,
    "name": "Bob Wilson",
    "age": 35,
    "department": "Marketing"
  }'
```

### Insert Employee 5 (Carol Davis)

```bash
curl -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 5,
    "name": "Carol Davis",
    "age": 32,
    "department": "Engineering"
  }'
```

---

## 3. Query with Group-By

### Group by Department

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

**Expected Response:**
```json
{
  "groupedRecords": {
    "Engineering": [
      {"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"},
      {"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"},
      {"id": 5, "name": "Carol Davis", "age": 32, "department": "Engineering"}
    ],
    "Marketing": [
      {"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"},
      {"id": 4, "name": "Bob Wilson", "age": 35, "department": "Marketing"}
    ]
  }
}
```

### Pretty Print (for readability)

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department" | python -m json.tool
```

Or with jq (if installed):
```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department" | jq
```

---

## 4. Query with Sort-By

### Sort by Age (Ascending)

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
```

**Expected Response:**
```json
{
  "sortedRecords": [
    {"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"},
    {"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"},
    {"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"},
    {"id": 5, "name": "Carol Davis", "age": 32, "department": "Engineering"},
    {"id": 4, "name": "Bob Wilson", "age": 35, "department": "Marketing"}
  ]
}
```

### Sort by Age (Descending)

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=desc"
```

### Sort by Name (Ascending - Alphabetical)

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=name&order=asc"
```

**Expected Response:**
```json
{
  "sortedRecords": [
    {"id": 3, "name": "Alice Brown", ...},
    {"id": 4, "name": "Bob Wilson", ...},
    {"id": 5, "name": "Carol Davis", ...},
    {"id": 2, "name": "Jane Smith", ...},
    {"id": 1, "name": "John Doe", ...}
  ]
}
```

### Sort by Name (Descending)

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=name&order=desc"
```

---

## 5. Multiple Datasets

### Create Product Dataset

```bash
curl -X POST http://localhost:8080/api/dataset/products/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "Laptop",
    "category": "Electronics",
    "price": 1200,
    "inStock": true
  }'
```

```bash
curl -X POST http://localhost:8080/api/dataset/products/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 2,
    "name": "Mouse",
    "category": "Electronics",
    "price": 25,
    "inStock": true
  }'
```

```bash
curl -X POST http://localhost:8080/api/dataset/products/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 3,
    "name": "Desk Chair",
    "category": "Furniture",
    "price": 300,
    "inStock": false
  }'
```

### Query Products by Category

```bash
curl "http://localhost:8080/api/dataset/products/query?groupBy=category"
```

### Sort Products by Price

```bash
curl "http://localhost:8080/api/dataset/products/query?sortBy=price&order=asc"
```

---

## 6. Complex JSON Structures

### Insert Record with Nested Objects

```bash
curl -X POST http://localhost:8080/api/dataset/users/record \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "address": {
      "street": "123 Main St",
      "city": "New York",
      "zipcode": "10001"
    },
    "preferences": {
      "theme": "dark",
      "notifications": true
    }
  }'
```

### Insert Record with Arrays

```bash
curl -X POST http://localhost:8080/api/dataset/orders/record \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-001",
    "customer": "John Doe",
    "items": [
      {"product": "Laptop", "quantity": 1, "price": 1200},
      {"product": "Mouse", "quantity": 2, "price": 25}
    ],
    "total": 1250,
    "status": "shipped"
  }'
```

---

## 7. Error Handling Tests

### Test Invalid JSON

```bash
curl -X POST http://localhost:8080/api/dataset/test/record \
  -H "Content-Type: application/json" \
  -d '{invalid json}'
```

**Expected Response:** 400 Bad Request

### Test Empty JSON

```bash
curl -X POST http://localhost:8080/api/dataset/test/record \
  -H "Content-Type: application/json" \
  -d '{}'
```

**Expected Response:** 400 Bad Request with error message

### Test Invalid Sort Order

```bash
curl "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=invalid"
```

**Expected Response:**
```json
{
  "error": "Invalid Query",
  "message": "Invalid order parameter. Must be 'asc' or 'desc'",
  "status": 400,
  "timestamp": "..."
}
```

---

## 8. Complete Test Workflow

Run this complete workflow to test all features:

```bash
#!/bin/bash

echo "=== JSON Dataset API Test Workflow ==="
echo ""

echo "1. Health Check..."
curl -s http://localhost:8080/api/dataset/health
echo -e "\n"

echo "2. Inserting Employee Records..."
curl -s -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "name": "John Doe", "age": 30, "department": "Engineering"}'
echo -e "\n"

curl -s -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering"}'
echo -e "\n"

curl -s -X POST http://localhost:8080/api/dataset/employee_dataset/record \
  -H "Content-Type: application/json" \
  -d '{"id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing"}'
echo -e "\n"

echo "3. Group by Department..."
curl -s "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
echo -e "\n"

echo "4. Sort by Age (Ascending)..."
curl -s "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc"
echo -e "\n"

echo "5. Sort by Age (Descending)..."
curl -s "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=desc"
echo -e "\n"

echo "=== Test Complete ==="
```

Save this as `test-api.sh`, make it executable (`chmod +x test-api.sh`), and run it.

---

## 9. Windows PowerShell Commands

For Windows users, here are PowerShell equivalents:

### Insert Record
```powershell
$body = @{
    id = 1
    name = "John Doe"
    age = 30
    department = "Engineering"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/dataset/employee_dataset/record" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

### Query with Group-By
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/dataset/employee_dataset/query?groupBy=department" `
  -Method Get
```

### Query with Sort-By
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/dataset/employee_dataset/query?sortBy=age&order=asc" `
  -Method Get
```

---

## 10. Using HTTPie (Alternative to curl)

If you prefer HTTPie over curl:

### Install HTTPie
```bash
pip install httpie
```

### Insert Record
```bash
http POST localhost:8080/api/dataset/employee_dataset/record \
  id:=1 \
  name="John Doe" \
  age:=30 \
  department="Engineering"
```

### Query
```bash
http GET "localhost:8080/api/dataset/employee_dataset/query?groupBy=department"
```

---

## Tips

1. **Pretty Print JSON**: Use `| jq` at the end of curl commands
2. **Save Response**: Use `-o output.json` to save response to file
3. **Verbose Mode**: Use `-v` flag to see headers and request details
4. **Silent Mode**: Use `-s` flag to hide progress bar
5. **Include Headers**: Use `-i` flag to see response headers

## Next Steps

After testing the API:
1. Try creating your own datasets
2. Experiment with different JSON structures
3. Test edge cases (null values, empty fields)
4. Import the Postman collection for GUI testing
5. Check the H2 console to see stored data

---

**Happy Testing! 🚀**
