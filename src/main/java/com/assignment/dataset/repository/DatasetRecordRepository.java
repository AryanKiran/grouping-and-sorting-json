package com.assignment.dataset.repository;

import com.assignment.dataset.entity.DatasetRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for DatasetRecord entity
 */
@Repository
public interface DatasetRecordRepository extends JpaRepository<DatasetRecord, Long> {

    /**
     * Find all records for a specific dataset
     *
     * @param datasetName the name of the dataset
     * @return list of dataset records
     */
    List<DatasetRecord> findByDatasetName(String datasetName);

    /**
     * Check if a dataset exists
     *
     * @param datasetName the name of the dataset
     * @return true if dataset exists, false otherwise
     */
    boolean existsByDatasetName(String datasetName);

    /**
     * Count records in a dataset
     *
     * @param datasetName the name of the dataset
     * @return number of records
     */
    long countByDatasetName(String datasetName);
}
