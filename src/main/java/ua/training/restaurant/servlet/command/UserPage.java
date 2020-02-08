package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class UserPage implements Command {
    final static Logger log = Logger.getLogger(UserPage.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting user's page");
        return "WEB-INF/view/user.jsp";
    }
}
