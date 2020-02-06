package ua.training.restaurant.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Student on 26.01.2020
 */
public interface ObjectMapper<T> {
    T extractFromResultSet(ResultSet rs) throws SQLException;
}
