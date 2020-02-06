package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.exceptions.OrderNotFoundException;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class Order implements Command {
    final static Logger log = Logger.getLogger(Order.class);
    private OrderService orderService;

    public Order() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String url;
        Long id = Long.parseLong(request.getParameter("id"));
        log.info("getting userstatistics page");
        try {
            request.setAttribute("order", orderService.findById(id));
            url = "WEB-INF/view/order.jsp";
        } catch (OrderNotFoundException e) {
            log.error("cannot find order by id " + id);
            url = "redirect:/";
        }
        return url;
    }
}
