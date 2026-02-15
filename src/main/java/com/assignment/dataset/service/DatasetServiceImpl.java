package com.assignment.dataset.service;

import com.assignment.dataset.dto.DatasetDTO;
import com.assignment.dataset.entity.DatasetRecord;
import com.assignment.dataset.exception.DatasetException;
import com.assignment.dataset.repository.DatasetRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for dataset operations
 * Implements business logic for inserting, grouping, and sorting JSON records
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DatasetServiceImpl implements DatasetService {

    private final DatasetRecordRepository repository;

    @Override
    @Transactional
    public DatasetDTO.InsertRecordResponse insertRecord(String datasetName, Map<String, Object> recordData) {
        log.info("Inserting record into dataset: {}", datasetName);

        // Validate record data
        validateRecordData(recordData);

        // Create and save the record
        DatasetRecord record = DatasetRecord.builder()
                .datasetName(datasetName)
                .recordData(recordData)
                .build();

        DatasetRecord savedRecord = repository.save(record);
        log.info("Record saved with ID: {}", savedRecord.getId());

        // Extract the record ID from the data if present, otherwise use entity ID
        Object recordId = recordData.get("id");
        if (recordId == null) {
            recordId = savedRecord.getId();
        }

        return DatasetDTO.InsertRecordResponse.builder()
                .message("Record added successfully")
                .dataset(datasetName)
                .recordId(recordId)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DatasetDTO.GroupedRecordsResponse queryWithGroupBy(String datasetName, String groupBy) {
        log.info("Querying dataset: {} with groupBy: {}", datasetName, groupBy);

        // Fetch all records for the dataset
        List<DatasetRecord> records = repository.findByDatasetName(datasetName);

        if (records.isEmpty()) {
            log.warn("No records found for dataset: {}", datasetName);
            return DatasetDTO.GroupedRecordsResponse.builder()
                    .groupedRecords(new HashMap<>())
                    .build();
        }

        // Group records by the specified field
        Map<String, List<Map<String, Object>>> groupedRecords = groupRecordsByField(records, groupBy);

        log.info("Grouped {} records into {} groups", records.size(), groupedRecords.size());

        return DatasetDTO.GroupedRecordsResponse.builder()
                .groupedRecords(groupedRecords)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DatasetDTO.SortedRecordsResponse queryWithSortBy(String datasetName, String sortBy, String order) {
        log.info("Querying dataset: {} with sortBy: {}, order: {}", datasetName, sortBy, order);

        // Validate order parameter
        if (order != null && !order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            throw new DatasetException.InvalidQueryException(
                    "Invalid order parameter. Must be 'asc' or 'desc'");
        }

        // Default to ascending if not specified
        String sortOrder = order != null ? order.toLowerCase() : "asc";

        // Fetch all records for the dataset
        List<DatasetRecord> records = repository.findByDatasetName(datasetName);

        if (records.isEmpty()) {
            log.warn("No records found for dataset: {}", datasetName);
            return DatasetDTO.SortedRecordsResponse.builder()
                    .sortedRecords(new ArrayList<>())
                    .build();
        }

        // Sort records by the specified field
        List<Map<String, Object>> sortedRecords = sortRecordsByField(records, sortBy, sortOrder);

        log.info("Sorted {} records by field: {}", records.size(), sortBy);

        return DatasetDTO.SortedRecordsResponse.builder()
                .sortedRecords(sortedRecords)
                .build();
    }

    /**
     * Validate record data
     */
    private void validateRecordData(Map<String, Object> recordData) {
        if (recordData == null || recordData.isEmpty()) {
            throw new DatasetException.InvalidRecordException("Record data cannot be null or empty");
        }
    }

    /**
     * Group records by a specific field
     */
    private Map<String, List<Map<String, Object>>> groupRecordsByField(
            List<DatasetRecord> records, String groupByField) {

        Map<String, List<Map<String, Object>>> groupedMap = new LinkedHashMap<>();

        for (DatasetRecord record : records) {
            Map<String, Object> data = record.getRecordData();

            // Get the grouping key value
            Object groupKeyValue = data.get(groupByField);

            if (groupKeyValue == null) {
                // Handle records without the groupBy field
                log.warn("Record {} does not contain field: {}", record.getId(), groupByField);
                groupKeyValue = "null";
            }

            String groupKey = String.valueOf(groupKeyValue);

            // Add record to the appropriate group
            groupedMap.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(data);
        }

        return groupedMap;
    }

    /**
     * Sort records by a specific field
     */
    private List<Map<String, Object>> sortRecordsByField(
            List<DatasetRecord> records, String sortByField, String order) {

        List<Map<String, Object>> recordDataList = records.stream()
                .map(DatasetRecord::getRecordData)
                .collect(Collectors.toList());

        // Custom comparator for sorting
        Comparator<Map<String, Object>> comparator = (map1, map2) -> {
            Object value1 = map1.get(sortByField);
            Object value2 = map2.get(sortByField);

            // Handle null values
            if (value1 == null && value2 == null) return 0;
            if (value1 == null) return order.equals("asc") ? 1 : -1;
            if (value2 == null) return order.equals("asc") ? -1 : 1;

            // Compare values
            int result = compareValues(value1, value2);

            // Apply sort order
            return order.equals("asc") ? result : -result;
        };

        recordDataList.sort(comparator);
        return recordDataList;
    }

    /**
     * Compare two values intelligently
     */
    @SuppressWarnings("unchecked")
    private int compareValues(Object value1, Object value2) {
        // Try numeric comparison first
        if (value1 instanceof Number && value2 instanceof Number) {
            double num1 = ((Number) value1).doubleValue();
            double num2 = ((Number) value2).doubleValue();
            return Double.compare(num1, num2);
        }

        // Try comparable interface
        if (value1 instanceof Comparable && value2.getClass().equals(value1.getClass())) {
            try {
                return ((Comparable<Object>) value1).compareTo(value2);
            } catch (ClassCastException e) {
                // Fall through to string comparison
            }
        }

        // Fall back to string comparison
        return String.valueOf(value1).compareTo(String.valueOf(value2));
    }
}
