package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
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
        log.info("getting order info page");
        try {
            request.setAttribute("order", orderService.findById(id));
            url = "WEB-INF/view/order.jsp";
        } catch (Exception e) {
            log.error("cannot find order by id " + id);
            url = "redirect:/";
        }
        return url;
    }
}
