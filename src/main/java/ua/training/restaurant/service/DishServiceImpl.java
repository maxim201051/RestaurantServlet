package ua.training.restaurant.service;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.DaoFactory;
import ua.training.restaurant.dao.DishDao;
import ua.training.restaurant.entity.Dish;
import ua.training.restaurant.exceptions.DishNotFoundException;
import ua.training.restaurant.servlet.command.AcceptOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student
 */
public class DishServiceImpl implements DishService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private final static Logger log = Logger.getLogger(DishServiceImpl.class);

    @Override
    public List<Dish> findAll() {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findAll();
        } catch (Exception e) {
            log.error(e);
        }
        return new ArrayList<>();
    }

    @Override
    public Dish findByID(Long id) throws DishNotFoundException {
        Dish dish = null;
        try (DishDao dao = daoFactory.createDishDao()) {
            dish = dao.findById(id).orElseThrow(DishNotFoundException::new);
        } catch (Exception e) {
            log.error(e);
        }
        return dish;
    }
}
