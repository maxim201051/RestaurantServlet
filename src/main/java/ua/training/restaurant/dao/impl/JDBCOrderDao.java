package ua.training.restaurant.dao.impl;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.GenericDao;
import ua.training.restaurant.dao.OrderDao;
import ua.training.restaurant.dao.mapper.OrderMapper;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.order.Order_Status;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.service.KitchenService;

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
public class JDBCOrderDao implements OrderDao {
    final static Logger log = Logger.getLogger(JDBCOrderDao.class);
    private static final String SEARCH_COLUMN_ORDER_ID = "order_id";
    private static final String SEARCH_COLUMN_STATUS = "status";
    private static final String SEARCH_COLUMN_USER_ID = "user_id";
    private Connection connection;
    private Properties prop;

    public JDBCOrderDao(Connection connection) {
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
    public Optional<Order> findById(Long id) {
        Optional<Order> order;
        String query = MessageFormat.format(prop.getProperty("orders.genericQuery"), SEARCH_COLUMN_ORDER_ID, id);
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            OrderMapper orderMapper = new OrderMapper();
            order = Optional.of(orderMapper.extractFromResultSet(rs));
        } catch (SQLException e) {
            order = Optional.empty();
        }
        return order;
    }

    @Override
    public List<Order> findByUser(User user) {
        return findByUserId(user.getId());
    }

    @Override
    public List<Order> findByStatus(Order_Status order_status) {
        String query = MessageFormat.format(prop.getProperty("orders.genericQuery"), SEARCH_COLUMN_STATUS, order_status.ordinal());
        return findOrderList(query);
    }

    @Override
    public List<Order> findByUserId(Long id) {
        String query = MessageFormat.format(prop.getProperty("orders.genericQuery"), SEARCH_COLUMN_USER_ID, id);
        return findOrderList(query);
    }

    private List<Order> findOrderList(String query) {
        List<Order> orders = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            OrderMapper orderMapper = new OrderMapper();
            while (rs.next()) {
                Order order = orderMapper.extractFromResultSet(rs);
                orders.add(order);
            }
        } catch (SQLException ignored) {
        }
        return orders;
    }

    @Override
    public Order save(Order order) {//todo
        String query = MessageFormat.format(prop.getProperty("orders.genericQuery"), SEARCH_COLUMN_USER_ID, id);
        return saveOrUpdate(order, query);
    }

    private Order saveOrderUnits(Order order) { //todo
        String query;

    }

    @Override
    public Order update(Order order) {//todo
        String query = "";
        return saveOrUpdate(order, query);
    }

    private Order saveOrUpdate(Order order, String query) {
        try (Statement st = connection.createStatement()) {
            st.executeQuery(query);
        } catch (SQLException e) {
            log.error(e);
        }
        return order;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("unable to close db connection " + e.getMessage());
        }
    }
}
