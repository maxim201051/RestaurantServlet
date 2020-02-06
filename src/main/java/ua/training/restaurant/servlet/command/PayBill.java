package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.OrderNotFoundException;
import ua.training.restaurant.service.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 02.02.2020
 */
public class PayBill implements Command {
    final static Logger log = Logger.getLogger(PayBill.class);
    private OrderService orderService;
    private UserService userService;

    public PayBill() {
        orderService = new OrderServiceImpl();
        userService = new UserServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("loginedUser");
        log.info("trying to pay bill");
        Order order;
        try {
            order = orderService.findById(id);
            userService.addOrderToStatistic(user, order);
            orderService.setPaid(order);
            saveUserAndOrder(user, order);
        } catch (OrderNotFoundException e) {
            log.error("cannot find order by id " + id);
            request.setAttribute("failureMessage", "failureMessage");
        } catch (Exception e) {
            log.error("user dont have enough funds");
            request.setAttribute("notEnoughFundsMessage", "notEnoughFundsMessage");
        }
        return "redirect:/user/billpaying";
    }
}
