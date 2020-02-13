package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.Dish;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.exceptions.DishNotFoundException;
import ua.training.restaurant.service.*;
import ua.training.restaurant.utils.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 02.02.2020
 */
public class BuyProduct implements Command {
    final static Logger log = Logger.getLogger(BuyProduct.class);
    private DishService dishService;
    private OrderService orderService;

    public BuyProduct() {
        dishService = new DishServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        log.info("trying to add dish with id " + id + " to shopping cart");
        Dish dish;
        try {
            dish = dishService.findByID(id);
            Order order = Utils.getOrderInSession(request);
            orderService.addDish(order, dish, 1);
        } catch (DishNotFoundException e) {
            log.error("cannot find dish by id " + id);
            request.setAttribute("failureMessage", "failureMessage");
        } catch (Exception e) {
            log.error(e);
        }
        return "redirect:/foodmenu?page=1";
    }
}
