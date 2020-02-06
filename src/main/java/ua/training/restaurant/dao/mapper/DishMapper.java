package ua.training.restaurant.dao.mapper;

import ua.training.restaurant.entity.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Student on 26.01.2020
 */
public class DishMapper implements ObjectMapper<Dish> {
    @Override
    public Dish extractFromResultSet(ResultSet rs) throws SQLException {
        Dish dish = new Dish();
        dish.setId(rs.getLong("id"));
        dish.setNameEn(rs.getString("name_en"));
        dish.setNameUa(rs.getString("name_ua"));
        dish.setPortion(rs.getLong("portion"));
        dish.setPrice(rs.getLong("price"));
        return dish;
    }
}
