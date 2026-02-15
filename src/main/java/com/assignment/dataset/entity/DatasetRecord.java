package com.assignment.dataset.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Entity representing a JSON record in a dataset
 * Uses JSON column type for flexible schema storage
 */
@Entity
@Table(name = "dataset_records", indexes = {
    @Index(name = "idx_dataset_name", columnList = "dataset_name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatasetRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataset_name", nullable = false)
    private String datasetName;

    @Column(name = "record_data", columnDefinition = "json", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> recordData;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
