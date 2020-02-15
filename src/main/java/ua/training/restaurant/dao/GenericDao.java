package ua.training.restaurant.dao;

import java.util.Optional;

/**
 * Created by Student on 26.01.2020
 */
public interface GenericDao<T> extends AutoCloseable {
    String CONNECTION_PROPERTIES_FILE_PATH = "dbconnection.properties";
    String PROPERTY_FILE_PATH = "queries.properties";

    Optional<T> findById(Long id);
}
