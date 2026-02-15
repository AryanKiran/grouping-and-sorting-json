package com.assignment.dataset.exception;

/**
 * Custom exceptions for the application
 */
public class DatasetException extends RuntimeException {

    public DatasetException(String message) {
        super(message);
    }

    public DatasetException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when dataset is not found
     */
    public static class DatasetNotFoundException extends DatasetException {
        public DatasetNotFoundException(String datasetName) {
            super("Dataset not found: " + datasetName);
        }
    }

    /**
     * Exception thrown for invalid record data
     */
    public static class InvalidRecordException extends DatasetException {
        public InvalidRecordException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown for invalid query parameters
     */
    public static class InvalidQueryException extends DatasetException {
        public InvalidQueryException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when groupBy field is not found in records
     */
    public static class FieldNotFoundException extends DatasetException {
        public FieldNotFoundException(String fieldName) {
            super("Field not found in records: " + fieldName);
        }
    }
}
