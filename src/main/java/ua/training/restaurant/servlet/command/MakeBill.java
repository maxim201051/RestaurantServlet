package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.order.Order_Status;
import ua.training.restaurant.exceptions.OrderNotFoundException;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 03.02.2020
 */
public class MakeBill implements Command {
    final static Logger log = Logger.getLogger(MakeBill.class);
    private OrderService orderService;

    public MakeBill() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        log.info("trying to make bill");
        Order order;
        try {
            order = orderService.findById(id);
            order.setStatus(Order_Status.UNPAID);
            orderService.update(order);
        } catch (OrderNotFoundException e) {
            log.error("cannot find order by id " + id);
            request.setAttribute("failureMessage", "order.label.failureMessage");
        }
        return "redirect:/admin/billmaking";
    }
}
