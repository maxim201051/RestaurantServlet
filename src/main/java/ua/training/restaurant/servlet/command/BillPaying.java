package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.Bill;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.order.Order_Status;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 29.01.2020
 */
public class BillPaying implements Command {
    final static Logger log = Logger.getLogger(BillPaying.class);
    private OrderService orderService;

    public BillPaying() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginedUser");
        log.info("getting billpaying page");
        List<Order> orders = orderService.findByUser(user);
        List<Bill> bills = new ArrayList<>();
        orders.stream().filter(x -> x.getStatus().equals(Order_Status.UNPAID)).forEach(x -> bills.add(new Bill(x)));
        request.setAttribute("bills", bills);
        return "WEB-INF/view/billpaying.jsp";
    }
}
