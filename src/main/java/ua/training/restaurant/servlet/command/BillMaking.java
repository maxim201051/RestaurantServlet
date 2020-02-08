package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.order.Order_Status;
import ua.training.restaurant.service.OrderService;
import ua.training.restaurant.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class BillMaking implements Command {
    final static Logger log = Logger.getLogger(BillMaking.class);
    private OrderService orderService;

    public BillMaking() {
        orderService = new OrderServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting bill making page");
        try {
            request.setAttribute("orders", orderService.findByStatus(Order_Status.READY));
        } catch (Exception e) {
            log.error(e);
        }
        return "WEB-INF/view/billmaking.jsp";
    }
}
