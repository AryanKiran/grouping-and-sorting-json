package com.assignment.dataset.repository;

import com.assignment.dataset.entity.DatasetRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for DatasetRecordRepository
 */
@DataJpaTest
@ActiveProfiles("test")
class DatasetRecordRepositoryTest {

    @Autowired
    private DatasetRecordRepository repository;

    private Map<String, Object> sampleRecord;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        sampleRecord = new HashMap<>();
        sampleRecord.put("id", 1);
        sampleRecord.put("name", "John Doe");
        sampleRecord.put("age", 30);
        sampleRecord.put("department", "Engineering");
    }

    @Test
    void testSaveAndFindRecord() {
        // Given
        DatasetRecord record = DatasetRecord.builder()
                .datasetName("employee_dataset")
                .recordData(sampleRecord)
                .build();

        // When
        DatasetRecord savedRecord = repository.save(record);

        // Then
        assertThat(savedRecord).isNotNull();
        assertThat(savedRecord.getId()).isNotNull();
        assertThat(savedRecord.getDatasetName()).isEqualTo("employee_dataset");
        assertThat(savedRecord.getRecordData()).isEqualTo(sampleRecord);
        assertThat(savedRecord.getCreatedAt()).isNotNull();
    }

    @Test
    void testFindByDatasetName() {
        // Given
        DatasetRecord record1 = DatasetRecord.builder()
                .datasetName("employee_dataset")
                .recordData(sampleRecord)
                .build();

        Map<String, Object> record2Data = new HashMap<>();
        record2Data.put("id", 2);
        record2Data.put("name", "Jane Smith");
        record2Data.put("age", 25);
        record2Data.put("department", "Engineering");

        DatasetRecord record2 = DatasetRecord.builder()
                .datasetName("employee_dataset")
                .recordData(record2Data)
                .build();

        repository.save(record1);
        repository.save(record2);

        // When
        List<DatasetRecord> records = repository.findByDatasetName("employee_dataset");

        // Then
        assertThat(records).hasSize(2);
        assertThat(records).extracting(DatasetRecord::getDatasetName)
                .containsOnly("employee_dataset");
    }

    @Test
    void testExistsByDatasetName() {
        // Given
        DatasetRecord record = DatasetRecord.builder()
                .datasetName("test_dataset")
                .recordData(sampleRecord)
                .build();
        repository.save(record);

        // When & Then
        assertThat(repository.existsByDatasetName("test_dataset")).isTrue();
        assertThat(repository.existsByDatasetName("non_existent")).isFalse();
    }

    @Test
    void testCountByDatasetName() {
        // Given
        for (int i = 1; i <= 3; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", i);
            data.put("name", "User " + i);

            DatasetRecord record = DatasetRecord.builder()
                    .datasetName("count_test")
                    .recordData(data)
                    .build();
            repository.save(record);
        }

        // When
        long count = repository.countByDatasetName("count_test");

        // Then
        assertThat(count).isEqualTo(3);
    }

    @Test
    void testFindByDatasetNameReturnsEmptyListWhenNoRecords() {
        // When
        List<DatasetRecord> records = repository.findByDatasetName("non_existent_dataset");

        // Then
        assertThat(records).isEmpty();
    }
}
