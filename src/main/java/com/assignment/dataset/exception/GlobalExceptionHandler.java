package com.assignment.dataset.exception;

import com.assignment.dataset.dto.DatasetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler for REST API
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatasetException.DatasetNotFoundException.class)
    public ResponseEntity<DatasetDTO.ErrorResponse> handleDatasetNotFound(
            DatasetException.DatasetNotFoundException ex, WebRequest request) {
        DatasetDTO.ErrorResponse errorResponse = DatasetDTO.ErrorResponse.builder()
                .error("Dataset Not Found")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatasetException.InvalidRecordException.class)
    public ResponseEntity<DatasetDTO.ErrorResponse> handleInvalidRecord(
            DatasetException.InvalidRecordException ex, WebRequest request) {
        DatasetDTO.ErrorResponse errorResponse = DatasetDTO.ErrorResponse.builder()
                .error("Invalid Record")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatasetException.InvalidQueryException.class)
    public ResponseEntity<DatasetDTO.ErrorResponse> handleInvalidQuery(
            DatasetException.InvalidQueryException ex, WebRequest request) {
        DatasetDTO.ErrorResponse errorResponse = DatasetDTO.ErrorResponse.builder()
                .error("Invalid Query")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatasetException.FieldNotFoundException.class)
    public ResponseEntity<DatasetDTO.ErrorResponse> handleFieldNotFound(
            DatasetException.FieldNotFoundException ex, WebRequest request) {
        DatasetDTO.ErrorResponse errorResponse = DatasetDTO.ErrorResponse.builder()
                .error("Field Not Found")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DatasetDTO.ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        DatasetDTO.ErrorResponse errorResponse = DatasetDTO.ErrorResponse.builder()
                .error("Internal Server Error")
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
