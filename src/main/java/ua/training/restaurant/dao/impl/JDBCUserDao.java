package ua.training.restaurant.dao.impl;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.GenericDao;
import ua.training.restaurant.dao.UserDao;
import ua.training.restaurant.dao.mapper.UserMapper;
import ua.training.restaurant.entity.user.Role;
import ua.training.restaurant.entity.user.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


/**
 * Created by Student on 26.01.2020
 */
public class JDBCUserDao implements UserDao {
    final static Logger log = Logger.getLogger(JDBCUserDao.class);
    private Connection connection;
    private Properties prop;

    public JDBCUserDao(Connection connection) {
        try {
            FileInputStream fis = new FileInputStream(GenericDao.PROPERTY_FILE_PATH);
            this.prop = new Properties();
            this.prop.load(fis);
        } catch (IOException e) {
            log.error(e.getMessage()); //todo
        }
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = MessageFormat.format(prop.getProperty("users.findById"), id);
        return findUser(query);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        String query = MessageFormat.format(prop.getProperty("users.findByUsernameAndPassword"), "'" + username + "'", "'" + password + "'");
        return findUser(query);
    }

    private Optional<User> findUser(String query) {
        Optional<User> user;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            rs.next();
            UserMapper userMapper = new UserMapper();
            user = Optional.of(userMapper.extractFromResultSet(rs));
        } catch (SQLException e) {
            user = Optional.empty();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        String query = MessageFormat.format(prop.getProperty("users.findAll"), "");
        return findUserList(query);
    }

    @Override
    public List<User> findByAuthoritiesContaining(Role role) {
        String query = MessageFormat.format(prop.getProperty("users.findByAuthorities"), role.ordinal());
        return findUserList(query);
    }

    private List<User> findUserList(String query) {
        List<User> users = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            UserMapper userMapper = new UserMapper();
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                users.add(user);
            }
        } catch (SQLException ignored) {
        }
        return users;
    }

    @Override
    public User save(User user) throws SQLException {
        String query = MessageFormat.format(prop.getProperty("users.save"), "'" + user.getNameEN() + "'", "'" + user.getNameUA() + "'",
                "'" + user.getPassword() + "'", "'" + user.getRegistrationDate() + "'", "'" + user.getUsername() + "'");
        saveOrUpdate(user, query);
        query = MessageFormat.format(prop.getProperty("user_authorities.save"), "");
        saveOrUpdate(user, query);
        return user;
    }

    @Override
    public User update(User user) throws SQLException {
        String query = MessageFormat.format(prop.getProperty("users.update"), user.getFunds().toString(), user.getOrdersNumber(), user.getOrdersTotalCost(), user.getId());
        return saveOrUpdate(user, query);
    }

    private User saveOrUpdate(User user, String query) throws SQLException {

        log.info(query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
        return user;
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
