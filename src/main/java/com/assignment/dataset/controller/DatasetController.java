package com.assignment.dataset.controller;

import com.assignment.dataset.dto.DatasetDTO;
import com.assignment.dataset.service.DatasetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for Dataset operations
 * Provides endpoints for inserting records and querying datasets
 */
@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
@Slf4j
public class DatasetController {

    private final DatasetService datasetService;

    /**
     * Insert a new record into a dataset
     *
     * POST /api/dataset/{datasetName}/record
     *
     * @param datasetName the name of the dataset
     * @param recordData  the JSON record to insert
     * @return insert response with record ID
     */
    @PostMapping("/{datasetName}/record")
    public ResponseEntity<DatasetDTO.InsertRecordResponse> insertRecord(
            @PathVariable String datasetName,
            @RequestBody Map<String, Object> recordData) {

        log.info("POST /api/dataset/{}/record - Inserting record", datasetName);

        DatasetDTO.InsertRecordResponse response = datasetService.insertRecord(datasetName, recordData);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Query a dataset with optional group-by and sort-by operations
     *
     * GET /api/dataset/{datasetName}/query?groupBy=field
     * GET /api/dataset/{datasetName}/query?sortBy=field&order=asc
     *
     * @param datasetName the name of the dataset
     * @param groupBy     optional field to group by
     * @param sortBy      optional field to sort by
     * @param order       optional sort order (asc or desc)
     * @return grouped or sorted records
     */
    @GetMapping("/{datasetName}/query")
    public ResponseEntity<?> queryDataset(
            @PathVariable String datasetName,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {

        log.info("GET /api/dataset/{}/query - groupBy: {}, sortBy: {}, order: {}",
                datasetName, groupBy, sortBy, order);

        // Validate that at least one query parameter is provided
        if (groupBy == null && sortBy == null) {
            log.warn("No query parameters provided");
            // Return all records sorted by ID if no parameters
            return ResponseEntity.ok(datasetService.queryWithSortBy(datasetName, "id", "asc"));
        }

        // Group-by takes precedence if both are provided
        if (groupBy != null) {
            DatasetDTO.GroupedRecordsResponse response = datasetService.queryWithGroupBy(datasetName, groupBy);
            return ResponseEntity.ok(response);
        }

        // Sort-by operation
        DatasetDTO.SortedRecordsResponse response = datasetService.queryWithSortBy(datasetName, sortBy, order);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "JSON Dataset API"
        ));
    }
}
