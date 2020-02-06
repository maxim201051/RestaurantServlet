package ua.training.restaurant.dao;

import java.util.Optional;

/**
 * Created by Student on 26.01.2020
 */
public interface GenericDao<T> extends AutoCloseable {
    String PROPERTY_FILE_PATH = "D:\\EPAM\\RestaurantServlet\\src\\main\\resources\\queries.properties";

    Optional<T> findById(Long id);
}
