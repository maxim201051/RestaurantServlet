package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.UserDataNotValidException;
import ua.training.restaurant.service.UserService;
import ua.training.restaurant.service.UserServiceImpl;
import ua.training.restaurant.utils.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 22.01.2020
 */
public class SignUp implements Command {
    private final static Logger log = Logger.getLogger(SignUp.class);
    private UserService userService;

    public SignUp() {
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

    private String doGet(HttpServletRequest request) {
        return "/WEB-INF/view/signup.jsp";
    }

    private String doPost(HttpServletRequest request) {
        String url;
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String nameEn = request.getParameter("nameEn");
            String nameUa = request.getParameter("nameUa");
            Validator.throwExIfUserNotValid(username, password, nameEn, nameUa);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNameEN(nameEn);
            user.setNameUA(nameUa);
            userService.setDefaultParams(user);
            userService.save(user);
            url = "redirect:/login";
        } catch (UserDataNotValidException e) {
            log.error("Invalid data");
            request.setAttribute("validationFailureMessage", "validationFailureMessage");
            url = "/WEB-INF/view/signup.jsp";
        } catch (Exception e) {
            log.error("username already exists");
            request.setAttribute("errorMessage", "errorMessage");
            url = "/WEB-INF/view/signup.jsp";
        }
        return url;
    }
}
