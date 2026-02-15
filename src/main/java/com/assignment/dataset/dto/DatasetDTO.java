package com.assignment.dataset.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * DTOs for API requests and responses
 */
public class DatasetDTO {

    /**
     * Response for successful record insertion
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InsertRecordResponse {
        private String message;
        private String dataset;
        private Object recordId;
    }

    /**
     * Response for grouped records query
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupedRecordsResponse {
        private Map<String, List<Map<String, Object>>> groupedRecords;
    }

    /**
     * Response for sorted records query
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SortedRecordsResponse {
        private List<Map<String, Object>> sortedRecords;
    }

    /**
     * Error response
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {
        private String error;
        private String message;
        private Integer status;
        private String timestamp;
    }

    /**
     * Query parameters for dataset queries
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QueryParams {
        private String groupBy;
        private String sortBy;
        private String order; // asc or desc
    }
}
