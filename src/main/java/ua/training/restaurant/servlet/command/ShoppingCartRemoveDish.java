package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.Dish;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.exceptions.DishNotFoundException;
import ua.training.restaurant.service.DishService;
import ua.training.restaurant.service.DishServiceImpl;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;
import ua.training.restaurant.utils.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 03.02.2020
 */
public class ShoppingCartRemoveDish implements Command {
    private final static Logger log = Logger.getLogger(ShoppingCartRemoveDish.class);
    private OrderService orderService;
    private DishService dishService;

    public ShoppingCartRemoveDish() {
        orderService = new OrderServiceImpl();
        dishService = new DishServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        log.info("trying to remove dish with id " + id);
        Dish dish;
        try {
            dish = dishService.findByID(id);
            Order order = Utils.getOrderInSession(request);
            orderService.removeDish(order, dish);
        } catch (DishNotFoundException e) {
            log.error("cannot find dish by id " + id);
            request.setAttribute("failureMessage", "failureMessage");
        } catch (Exception e) {
            log.error(e);
        }
        return "redirect:/shoppingcart";
    }
}
