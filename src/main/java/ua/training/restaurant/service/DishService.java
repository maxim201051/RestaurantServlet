package ua.training.restaurant.service;

import ua.training.restaurant.entity.Dish;

import java.util.List;

/**
 * Created by Student
 */
public interface DishService {
    List<Dish> findAll(int page, int recordsPerPage) throws Exception;

    Dish findByID(Long id) throws Exception;
}
