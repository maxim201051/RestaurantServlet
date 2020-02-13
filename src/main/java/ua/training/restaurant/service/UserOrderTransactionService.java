package ua.training.restaurant.service;

import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.user.User;

/**
 * Created by Student on 13.02.2020
 */
public interface UserOrderTransactionService {
    boolean saveUserAndOrder(User user, Order order);
}
