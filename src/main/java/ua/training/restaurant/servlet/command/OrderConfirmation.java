package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.order.Order_Status;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class OrderConfirmation implements Command {
    final static Logger log = Logger.getLogger(OrderConfirmation.class);
    private OrderService orderService;

    public OrderConfirmation() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting orderconfirmation page");
        request.setAttribute("orders", orderService.findByStatus(Order_Status.CREATED));
        return "WEB-INF/view/orderconfirmation.jsp";
    }
}
