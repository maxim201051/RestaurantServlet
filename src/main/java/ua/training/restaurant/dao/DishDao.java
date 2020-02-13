package ua.training.restaurant.dao;

import ua.training.restaurant.entity.Dish;

import java.util.List;

/**
 * Created by Student on 26.01.2020
 */
public interface DishDao extends GenericDao<Dish> {
    List<Dish> findAll(int firstIndex, int elementsNumber);
}
