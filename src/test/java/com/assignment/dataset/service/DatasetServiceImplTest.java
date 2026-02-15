package com.assignment.dataset.service;

import com.assignment.dataset.dto.DatasetDTO;
import com.assignment.dataset.entity.DatasetRecord;
import com.assignment.dataset.exception.DatasetException;
import com.assignment.dataset.repository.DatasetRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DatasetServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class DatasetServiceImplTest {

    @Mock
    private DatasetRecordRepository repository;

    @InjectMocks
    private DatasetServiceImpl service;

    private Map<String, Object> sampleRecord;
    private List<DatasetRecord> sampleRecords;

    @BeforeEach
    void setUp() {
        sampleRecord = new HashMap<>();
        sampleRecord.put("id", 1);
        sampleRecord.put("name", "John Doe");
        sampleRecord.put("age", 30);
        sampleRecord.put("department", "Engineering");

        // Create sample records for testing
        sampleRecords = new ArrayList<>();

        Map<String, Object> record1 = new HashMap<>();
        record1.put("id", 1);
        record1.put("name", "John Doe");
        record1.put("age", 30);
        record1.put("department", "Engineering");

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

        sampleRecords.add(createDatasetRecord(1L, "employee_dataset", record1));
        sampleRecords.add(createDatasetRecord(2L, "employee_dataset", record2));
        sampleRecords.add(createDatasetRecord(3L, "employee_dataset", record3));
    }

    private DatasetRecord createDatasetRecord(Long id, String datasetName, Map<String, Object> data) {
        return DatasetRecord.builder()
                .id(id)
                .datasetName(datasetName)
                .recordData(data)
                .build();
    }

    @Test
    void testInsertRecord_Success() {
        // Given
        DatasetRecord savedRecord = createDatasetRecord(1L, "employee_dataset", sampleRecord);
        when(repository.save(any(DatasetRecord.class))).thenReturn(savedRecord);

        // When
        DatasetDTO.InsertRecordResponse response = service.insertRecord("employee_dataset", sampleRecord);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Record added successfully");
        assertThat(response.getDataset()).isEqualTo("employee_dataset");
        assertThat(response.getRecordId()).isEqualTo(1);

        verify(repository, times(1)).save(any(DatasetRecord.class));
    }

    @Test
    void testInsertRecord_WithoutIdInData() {
        // Given
        Map<String, Object> recordWithoutId = new HashMap<>();
        recordWithoutId.put("name", "Test User");
        recordWithoutId.put("age", 25);

        DatasetRecord savedRecord = createDatasetRecord(10L, "test_dataset", recordWithoutId);
        when(repository.save(any(DatasetRecord.class))).thenReturn(savedRecord);

        // When
        DatasetDTO.InsertRecordResponse response = service.insertRecord("test_dataset", recordWithoutId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getRecordId()).isEqualTo(10L);
    }

    @Test
    void testInsertRecord_NullData_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> service.insertRecord("test_dataset", null))
                .isInstanceOf(DatasetException.InvalidRecordException.class)
                .hasMessageContaining("Record data cannot be null or empty");

        verify(repository, never()).save(any(DatasetRecord.class));
    }

    @Test
    void testInsertRecord_EmptyData_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> service.insertRecord("test_dataset", new HashMap<>()))
                .isInstanceOf(DatasetException.InvalidRecordException.class)
                .hasMessageContaining("Record data cannot be null or empty");

        verify(repository, never()).save(any(DatasetRecord.class));
    }

    @Test
    void testQueryWithGroupBy_Success() {
        // Given
        when(repository.findByDatasetName("employee_dataset")).thenReturn(sampleRecords);

        // When
        DatasetDTO.GroupedRecordsResponse response = service.queryWithGroupBy("employee_dataset", "department");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getGroupedRecords()).hasSize(2);
        assertThat(response.getGroupedRecords()).containsKeys("Engineering", "Marketing");
        assertThat(response.getGroupedRecords().get("Engineering")).hasSize(2);
        assertThat(response.getGroupedRecords().get("Marketing")).hasSize(1);

        verify(repository, times(1)).findByDatasetName("employee_dataset");
    }

    @Test
    void testQueryWithGroupBy_EmptyDataset() {
        // Given
        when(repository.findByDatasetName("empty_dataset")).thenReturn(new ArrayList<>());

        // When
        DatasetDTO.GroupedRecordsResponse response = service.queryWithGroupBy("empty_dataset", "department");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getGroupedRecords()).isEmpty();
    }

    @Test
    void testQueryWithGroupBy_MissingField() {
        // Given
        when(repository.findByDatasetName("employee_dataset")).thenReturn(sampleRecords);

        // When
        DatasetDTO.GroupedRecordsResponse response = service.queryWithGroupBy("employee_dataset", "nonexistent_field");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getGroupedRecords()).containsKey("null");
        assertThat(response.getGroupedRecords().get("null")).hasSize(3);
    }

    @Test
    void testQueryWithSortBy_Ascending() {
        // Given
        when(repository.findByDatasetName("employee_dataset")).thenReturn(sampleRecords);

        // When
        DatasetDTO.SortedRecordsResponse response = service.queryWithSortBy("employee_dataset", "age", "asc");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getSortedRecords()).hasSize(3);
        assertThat(response.getSortedRecords().get(0).get("age")).isEqualTo(25);
        assertThat(response.getSortedRecords().get(1).get("age")).isEqualTo(28);
        assertThat(response.getSortedRecords().get(2).get("age")).isEqualTo(30);

        verify(repository, times(1)).findByDatasetName("employee_dataset");
    }

    @Test
    void testQueryWithSortBy_Descending() {
        // Given
        when(repository.findByDatasetName("employee_dataset")).thenReturn(sampleRecords);

        // When
        DatasetDTO.SortedRecordsResponse response = service.queryWithSortBy("employee_dataset", "age", "desc");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getSortedRecords()).hasSize(3);
        assertThat(response.getSortedRecords().get(0).get("age")).isEqualTo(30);
        assertThat(response.getSortedRecords().get(1).get("age")).isEqualTo(28);
        assertThat(response.getSortedRecords().get(2).get("age")).isEqualTo(25);
    }

    @Test
    void testQueryWithSortBy_DefaultAscending() {
        // Given
        when(repository.findByDatasetName("employee_dataset")).thenReturn(sampleRecords);

        // When
        DatasetDTO.SortedRecordsResponse response = service.queryWithSortBy("employee_dataset", "age", null);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getSortedRecords().get(0).get("age")).isEqualTo(25);
    }

    @Test
    void testQueryWithSortBy_InvalidOrder_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> service.queryWithSortBy("employee_dataset", "age", "invalid"))
                .isInstanceOf(DatasetException.InvalidQueryException.class)
                .hasMessageContaining("Invalid order parameter");

        verify(repository, never()).findByDatasetName(anyString());
    }

    @Test
    void testQueryWithSortBy_EmptyDataset() {
        // Given
        when(repository.findByDatasetName("empty_dataset")).thenReturn(new ArrayList<>());

        // When
        DatasetDTO.SortedRecordsResponse response = service.queryWithSortBy("empty_dataset", "age", "asc");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getSortedRecords()).isEmpty();
    }

    @Test
    void testQueryWithSortBy_StringField() {
        // Given
        when(repository.findByDatasetName("employee_dataset")).thenReturn(sampleRecords);

        // When
        DatasetDTO.SortedRecordsResponse response = service.queryWithSortBy("employee_dataset", "name", "asc");

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getSortedRecords()).hasSize(3);
        assertThat(response.getSortedRecords().get(0).get("name")).isEqualTo("Alice Brown");
        assertThat(response.getSortedRecords().get(1).get("name")).isEqualTo("Jane Smith");
        assertThat(response.getSortedRecords().get(2).get("name")).isEqualTo("John Doe");
    }
}
