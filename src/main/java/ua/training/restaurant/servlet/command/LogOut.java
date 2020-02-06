package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.KitchenService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 22.01.2020
 */
public class LogOut implements Command {
    final static Logger log = Logger.getLogger(LogOut.class);

    public String execute(HttpServletRequest request) {
        log.info("user logged out");
        request.getSession().invalidate();
        return "redirect:/login.jsp";
    }
}
