package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.utils.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class ShoppingCart implements Command {
    final static Logger log = Logger.getLogger(ShoppingCart.class);

    public String execute(HttpServletRequest request) {
        String res = null;
        if (request.getMethod().equals("GET"))
            res = doGet(request);
        else if (request.getMethod().equals("POST"))
            res = doPost(request);
        return res;
    }

    public String doGet(HttpServletRequest request) {
        log.info("getting shoppingcart page");
        Order order = Utils.getOrderInSession(request);
        request.setAttribute("order", order);
        return "WEB-INF/view/shoppingcart.jsp";
    }

    public String doPost(HttpServletRequest request) {
        return null;
    }
}
