package ua.training.restaurant.service;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.DaoFactory;
import ua.training.restaurant.dao.DishDao;
import ua.training.restaurant.entity.Dish;
import ua.training.restaurant.exceptions.DishNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student
 */
public class DishServiceImpl implements DishService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public List<Dish> findAll() throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findAll();
        }
    }

    @Override
    public Dish findByID(Long id) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findById(id).orElseThrow(DishNotFoundException::new);
        }
    }
}
