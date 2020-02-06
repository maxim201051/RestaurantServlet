package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.EmptyOrderException;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;
import ua.training.restaurant.utils.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 02.02.2020
 */
public class SaveOrder implements Command {
    final static Logger log = Logger.getLogger(SaveOrder.class);
    private OrderService orderService;

    public SaveOrder() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginedUser");
        log.info("trying to insert created order into db");
        Order order = Utils.getOrderInSession(request);
        try {
            orderService.save(order, user);
            Utils.removeOrderInSession(request);
        } catch (EmptyOrderException e) {
            log.error("order is empty");
            request.setAttribute("failureMessage", "failureMessage");
        }
        return "redirect:/user/shoppingcart";
    }
}
