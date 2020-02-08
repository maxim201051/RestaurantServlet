package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.service.UserService;
import ua.training.restaurant.service.UserServiceImpl;
import ua.training.restaurant.utils.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class AddFunds implements Command {
    final static Logger log = Logger.getLogger(AddFunds.class);
    private UserService userService;

    public AddFunds() {
        userService = new UserServiceImpl();
    }

    public String execute(HttpServletRequest request) {
        String res = null;
        if (request.getMethod().equals("GET"))
            res = doGet(request);
        else if (request.getMethod().equals("POST"))
            res = doPost(request);
        return res;
    }

    public String doGet(HttpServletRequest request) {
        log.info("getting addfunds page");
        return "WEB-INF/view/addfunds.jsp";
    }

    public String doPost(HttpServletRequest request) {
        String url;
        log.info("trying to add funds to user account");
        try {
            Long funds = Long.parseLong(request.getParameter("funds"));
            User user = (User) request.getSession().getAttribute("loginedUser");
            Validator.throwExIfFundsNotValid(funds);
            userService.addFunds(user, funds);
            url = "redirect:/user";
        } catch (Exception e) {
            log.error("invalid funds");
            request.setAttribute("failureMessage", "failureMessage");
            url = "WEB-INF/view/addfunds.jsp";
        }
        return url;
    }
}
