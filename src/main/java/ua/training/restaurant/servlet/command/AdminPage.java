package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class AdminPage implements Command {
    final static Logger log = Logger.getLogger(AdminPage.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting admin's page");
        return "WEB-INF/view/admin.jsp";
    }
}
