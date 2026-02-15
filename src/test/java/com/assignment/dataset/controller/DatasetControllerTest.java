package com.assignment.dataset.controller;

import com.assignment.dataset.dto.DatasetDTO;
import com.assignment.dataset.service.DatasetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for DatasetController
 */
@WebMvcTest(DatasetController.class)
class DatasetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DatasetService datasetService;

    private Map<String, Object> sampleRecord;

    @BeforeEach
    void setUp() {
        sampleRecord = new HashMap<>();
        sampleRecord.put("id", 1);
        sampleRecord.put("name", "John Doe");
        sampleRecord.put("age", 30);
        sampleRecord.put("department", "Engineering");
    }

    @Test
    void testInsertRecord_Success() throws Exception {
        // Given
        DatasetDTO.InsertRecordResponse mockResponse = DatasetDTO.InsertRecordResponse.builder()
                .message("Record added successfully")
                .dataset("employee_dataset")
                .recordId(1)
                .build();

        when(datasetService.insertRecord(eq("employee_dataset"), any()))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRecord)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Record added successfully"))
                .andExpect(jsonPath("$.dataset").value("employee_dataset"))
                .andExpect(jsonPath("$.recordId").value(1));
    }

    @Test
    void testInsertRecord_InvalidJson() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testQueryWithGroupBy_Success() throws Exception {
        // Given
        Map<String, List<Map<String, Object>>> groupedData = new LinkedHashMap<>();

        List<Map<String, Object>> engineeringRecords = new ArrayList<>();
        engineeringRecords.add(sampleRecord);

        Map<String, Object> record2 = new HashMap<>();
        record2.put("id", 2);
        record2.put("name", "Jane Smith");
        record2.put("age", 25);
        record2.put("department", "Engineering");
        engineeringRecords.add(record2);

        List<Map<String, Object>> marketingRecords = new ArrayList<>();
        Map<String, Object> record3 = new HashMap<>();
        record3.put("id", 3);
        record3.put("name", "Alice Brown");
        record3.put("age", 28);
        record3.put("department", "Marketing");
        marketingRecords.add(record3);

        groupedData.put("Engineering", engineeringRecords);
        groupedData.put("Marketing", marketingRecords);

        DatasetDTO.GroupedRecordsResponse mockResponse = DatasetDTO.GroupedRecordsResponse.builder()
                .groupedRecords(groupedData)
                .build();

        when(datasetService.queryWithGroupBy(eq("employee_dataset"), eq("department")))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("groupBy", "department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords").exists())
                .andExpect(jsonPath("$.groupedRecords.Engineering").isArray())
                .andExpect(jsonPath("$.groupedRecords.Engineering[0].id").value(1))
                .andExpect(jsonPath("$.groupedRecords.Marketing").isArray())
                .andExpect(jsonPath("$.groupedRecords.Marketing[0].id").value(3));
    }

    @Test
    void testQueryWithSortBy_Ascending() throws Exception {
        // Given
        List<Map<String, Object>> sortedRecords = new ArrayList<>();

        Map<String, Object> record2 = new HashMap<>();
        record2.put("id", 2);
        record2.put("name", "Jane Smith");
        record2.put("age", 25);
        record2.put("department", "Engineering");

        Map<String, Object> record3 = new HashMap<>();
        record3.put("id", 3);
        record3.put("name", "Alice Brown");
        record3.put("age", 28);
        record3.put("department", "Marketing");

        sortedRecords.add(record2);
        sortedRecords.add(record3);
        sortedRecords.add(sampleRecord);

        DatasetDTO.SortedRecordsResponse mockResponse = DatasetDTO.SortedRecordsResponse.builder()
                .sortedRecords(sortedRecords)
                .build();

        when(datasetService.queryWithSortBy(eq("employee_dataset"), eq("age"), eq("asc")))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("sortBy", "age")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords").isArray())
                .andExpect(jsonPath("$.sortedRecords[0].age").value(25))
                .andExpect(jsonPath("$.sortedRecords[1].age").value(28))
                .andExpect(jsonPath("$.sortedRecords[2].age").value(30));
    }

    @Test
    void testQueryWithSortBy_Descending() throws Exception {
        // Given
        List<Map<String, Object>> sortedRecords = new ArrayList<>();
        sortedRecords.add(sampleRecord);

        DatasetDTO.SortedRecordsResponse mockResponse = DatasetDTO.SortedRecordsResponse.builder()
                .sortedRecords(sortedRecords)
                .build();

        when(datasetService.queryWithSortBy(eq("employee_dataset"), eq("age"), eq("desc")))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("sortBy", "age")
                        .param("order", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords").isArray());
    }

    @Test
    void testQueryWithNoParameters_ReturnsDefault() throws Exception {
        // Given
        List<Map<String, Object>> sortedRecords = new ArrayList<>();
        sortedRecords.add(sampleRecord);

        DatasetDTO.SortedRecordsResponse mockResponse = DatasetDTO.SortedRecordsResponse.builder()
                .sortedRecords(sortedRecords)
                .build();

        when(datasetService.queryWithSortBy(eq("employee_dataset"), eq("id"), eq("asc")))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/dataset/employee_dataset/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords").isArray());
    }

    @Test
    void testHealthCheck() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/dataset/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("JSON Dataset API"));
    }
}
