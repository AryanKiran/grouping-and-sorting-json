package com.assignment.dataset;

import com.assignment.dataset.entity.DatasetRecord;
import com.assignment.dataset.repository.DatasetRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End-to-End Integration Tests
 * Tests the complete flow from controller to database
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DatasetIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DatasetRecordRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testCompleteWorkflow_InsertAndQuery() throws Exception {
        // Step 1: Insert multiple records
        Map<String, Object> record1 = new HashMap<>();
        record1.put("id", 1);
        record1.put("name", "John Doe");
        record1.put("age", 30);
        record1.put("department", "Engineering");

        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Record added successfully"))
                .andExpect(jsonPath("$.recordId").value(1));

        Map<String, Object> record2 = new HashMap<>();
        record2.put("id", 2);
        record2.put("name", "Jane Smith");
        record2.put("age", 25);
        record2.put("department", "Engineering");

        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record2)))
                .andExpect(status().isCreated());

        Map<String, Object> record3 = new HashMap<>();
        record3.put("id", 3);
        record3.put("name", "Alice Brown");
        record3.put("age", 28);
        record3.put("department", "Marketing");

        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record3)))
                .andExpect(status().isCreated());

        // Step 2: Query with groupBy
        mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("groupBy", "department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords.Engineering", hasSize(2)))
                .andExpect(jsonPath("$.groupedRecords.Marketing", hasSize(1)))
                .andExpect(jsonPath("$.groupedRecords.Engineering[0].name").exists())
                .andExpect(jsonPath("$.groupedRecords.Engineering[1].name").exists());

        // Step 3: Query with sortBy ascending
        mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("sortBy", "age")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords", hasSize(3)))
                .andExpect(jsonPath("$.sortedRecords[0].age").value(25))
                .andExpect(jsonPath("$.sortedRecords[1].age").value(28))
                .andExpect(jsonPath("$.sortedRecords[2].age").value(30));

        // Step 4: Query with sortBy descending
        mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("sortBy", "age")
                        .param("order", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords", hasSize(3)))
                .andExpect(jsonPath("$.sortedRecords[0].age").value(30))
                .andExpect(jsonPath("$.sortedRecords[1].age").value(28))
                .andExpect(jsonPath("$.sortedRecords[2].age").value(25));
    }

    @Test
    void testMultipleDatasets() throws Exception {
        // Insert into dataset 1
        Map<String, Object> record1 = new HashMap<>();
        record1.put("id", 1);
        record1.put("product", "Laptop");
        record1.put("price", 1200);

        mockMvc.perform(post("/api/dataset/products/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record1)))
                .andExpect(status().isCreated());

        // Insert into dataset 2
        Map<String, Object> record2 = new HashMap<>();
        record2.put("id", 1);
        record2.put("name", "John");
        record2.put("age", 30);

        mockMvc.perform(post("/api/dataset/users/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record2)))
                .andExpect(status().isCreated());

        // Query dataset 1
        mockMvc.perform(get("/api/dataset/products/query")
                        .param("sortBy", "price")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords", hasSize(1)))
                .andExpect(jsonPath("$.sortedRecords[0].product").value("Laptop"));

        // Query dataset 2
        mockMvc.perform(get("/api/dataset/users/query")
                        .param("sortBy", "age")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords", hasSize(1)))
                .andExpect(jsonPath("$.sortedRecords[0].name").value("John"));
    }

    @Test
    void testSortByStringField() throws Exception {
        Map<String, Object> record1 = new HashMap<>();
        record1.put("id", 1);
        record1.put("name", "Zara");

        Map<String, Object> record2 = new HashMap<>();
        record2.put("id", 2);
        record2.put("name", "Alice");

        Map<String, Object> record3 = new HashMap<>();
        record3.put("id", 3);
        record3.put("name", "Mike");

        mockMvc.perform(post("/api/dataset/names/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/dataset/names/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/dataset/names/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record3)))
                .andExpect(status().isCreated());

        // Sort alphabetically
        mockMvc.perform(get("/api/dataset/names/query")
                        .param("sortBy", "name")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords[0].name").value("Alice"))
                .andExpect(jsonPath("$.sortedRecords[1].name").value("Mike"))
                .andExpect(jsonPath("$.sortedRecords[2].name").value("Zara"));
    }

    @Test
    void testEmptyDatasetQuery() throws Exception {
        // Query non-existent dataset
        mockMvc.perform(get("/api/dataset/non_existent/query")
                        .param("groupBy", "field"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords").isEmpty());
    }

    @Test
    void testRecordWithNestedStructure() throws Exception {
        Map<String, Object> record = new HashMap<>();
        record.put("id", 1);
        record.put("name", "John");

        Map<String, Object> address = new HashMap<>();
        address.put("street", "123 Main St");
        address.put("city", "New York");
        record.put("address", address);

        mockMvc.perform(post("/api/dataset/complex/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Record added successfully"));
    }
}
