package ua.training.restaurant.service;

import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.UserNotFoundException;

import java.util.List;

/**
 * Created by Student
 */
public interface UserService {
    User findByUsernameAndPassword(String username, String password) throws UserNotFoundException;

    User save(User user);

    User update(User user);

    List<User> findAllUsers();

    User setDefaultParams(User user);

    User addOrderToStatistic(User user, Order order);

    User addFunds(User user, Long funds);
}
