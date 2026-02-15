package com.assignment.dataset.service;

import com.assignment.dataset.dto.DatasetDTO;

import java.util.Map;

/**
 * Service interface for dataset operations
 */
public interface DatasetService {

    /**
     * Insert a new record into a dataset
     *
     * @param datasetName the name of the dataset
     * @param recordData  the JSON record data
     * @return insert response with record ID
     */
    DatasetDTO.InsertRecordResponse insertRecord(String datasetName, Map<String, Object> recordData);

    /**
     * Query dataset with group-by operation
     *
     * @param datasetName the name of the dataset
     * @param groupBy     the field to group by
     * @return grouped records response
     */
    DatasetDTO.GroupedRecordsResponse queryWithGroupBy(String datasetName, String groupBy);

    /**
     * Query dataset with sort-by operation
     *
     * @param datasetName the name of the dataset
     * @param sortBy      the field to sort by
     * @param order       sort order (asc or desc)
     * @return sorted records response
     */
    DatasetDTO.SortedRecordsResponse queryWithSortBy(String datasetName, String sortBy, String order);
}
