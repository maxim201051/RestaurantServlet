package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.KitchenService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class Error404 implements Command {
    final static Logger log = Logger.getLogger(Error404.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting error page");
        return "WEB-INF/view/error.jsp";
    }
}
