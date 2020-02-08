package ua.training.restaurant.service;

import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.UserNotFoundException;

import java.util.List;

/**
 * Created by Student
 */
public interface UserService {
    User findByUsernameAndPassword(String username, String password) throws Exception;

    User save(User user) throws Exception;

    User update(User user) throws Exception;

    List<User> findAllUsers() throws Exception;

    User setDefaultParams(User user);

    User addOrderToStatistic(User user, Order order);

    User addFunds(User user, Long funds) throws Exception;
}
