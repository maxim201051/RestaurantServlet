package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.user.Role;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class ConcreteUserStatistic implements Command {
    private final static Logger log = Logger.getLogger(ConcreteUserStatistic.class);
    private OrderService orderService;

    public ConcreteUserStatistic() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting concrete user statistic page");
        User user = (User) request.getSession().getAttribute("loginedUser");
        if (user.getAuthorities().get(0).equals(Role.USER)) {
            try {
                request.setAttribute("orders", orderService.findByUser(user));
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            Long id = Long.parseLong(request.getParameter("id"));
            try {
                request.setAttribute("orders", orderService.findByUserId(id));
            } catch (Exception e) {
                log.error(e);
                return "redirect:/userstatistics";
            }
        }
        return "WEB-INF/view/concreteuserstatistic.jsp";
    }
}
