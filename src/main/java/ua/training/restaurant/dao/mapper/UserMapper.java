package ua.training.restaurant.dao.mapper;

import ua.training.restaurant.entity.user.Role;
import ua.training.restaurant.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Student on 26.01.2020
 */
public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFunds(rs.getLong("funds"));
        user.setNameEN(rs.getString("name_en"));
        user.setNameUA(rs.getString("name_ua"));
        user.setOrdersNumber(rs.getInt("orders_number"));
        user.setOrdersTotalCost(rs.getLong("orders_total_cost"));
        user.setPassword(rs.getString("password"));
        user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        user.setUsername(rs.getString("username"));
        ArrayList<Role> authorities = new ArrayList<>();
        authorities.add(Role.values()[rs.getInt("authorities")]);
        user.setAuthorities(authorities);
        return user;
    }
}
