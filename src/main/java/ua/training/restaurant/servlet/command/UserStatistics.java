package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.UserService;
import ua.training.restaurant.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class UserStatistics implements Command {
    final static Logger log = Logger.getLogger(UserStatistics.class);
    private UserService userService;

    public UserStatistics() {
        userService = new UserServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting userstatistics page");
        request.setAttribute("users", userService.findAllUsers());
        return "WEB-INF/view/userstatistics.jsp";
    }
}
