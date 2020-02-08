package ua.training.restaurant.dao.impl;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.DishDao;
import ua.training.restaurant.dao.GenericDao;
import ua.training.restaurant.dao.mapper.DishMapper;
import ua.training.restaurant.entity.Dish;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by Student on 26.01.2020
 */
public class JDBCDishDao implements DishDao {
    final static Logger log = Logger.getLogger(JDBCDishDao.class);
    private Connection connection;
    private Properties prop;

    public JDBCDishDao(Connection connection) {
        try {
            FileInputStream fis = new FileInputStream(GenericDao.PROPERTY_FILE_PATH);
            this.prop = new Properties();
            this.prop.load(fis);
        } catch (IOException e) {
            log.error(e);
        }
        this.connection = connection;
    }

    @Override
    public Optional<Dish> findById(Long id) {
        Optional<Dish> dish;
        String query = MessageFormat.format(prop.getProperty("dishes.findById"), id);
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            rs.next();
            DishMapper dishMapper = new DishMapper();
            dish = Optional.of(dishMapper.extractFromResultSet(rs));
        } catch (SQLException e) {
            dish = Optional.empty();
        }
        return dish;
    }

    @Override
    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        String query = MessageFormat.format(prop.getProperty("dishes.findAll"), "");
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            DishMapper dishMapper = new DishMapper();
            while (rs.next()) {
                Dish dish = dishMapper.extractFromResultSet(rs);
                dishes.add(dish);
            }
        } catch (SQLException ignored) {
        }
        return dishes;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
