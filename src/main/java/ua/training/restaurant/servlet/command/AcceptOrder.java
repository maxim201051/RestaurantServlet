package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.exceptions.OrderNotFoundException;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 03.02.2020
 */
public class AcceptOrder implements Command {
    private OrderService orderService;
    private final static Logger log = Logger.getLogger(AcceptOrder.class);

    public AcceptOrder() {
        this.orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        log.info("trying to confirm order");
        try {
            orderService.confirmOrder(id);
        } catch (OrderNotFoundException e) {
            log.error("cannot find order by id " + id);
            request.setAttribute("failureMessage", "failureMessage");
        }
        return "redirect:/admin/orderconfirmation";
    }
}
