package ua.training.restaurant.service;

import ua.training.restaurant.entity.Dish;
import ua.training.restaurant.exceptions.DishNotFoundException;

import java.util.List;

/**
 * Created by Student
 */
public interface DishService {
    List<Dish> findAll() throws Exception;

    Dish findByID(Long id) throws Exception;
}
