package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.service.UserService;
import ua.training.restaurant.service.UserServiceImpl;
import ua.training.restaurant.utils.AppUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 22.01.2020
 */
public class Login implements Command {
    private final static Logger log = Logger.getLogger(Login.class);
    private UserService userService;

    public Login() {
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
        log.info("getting login page");
        request.getSession().invalidate();
        return "/WEB-INF/view/login.jsp";
    }

    private String doPost(HttpServletRequest request) {
        String url;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            log.error(e);
            String errorMessage = "errorMessage";
            request.setAttribute("errorMessage", errorMessage);
            url = "/WEB-INF/view/login.jsp";
            return url;
        }
        AppUtils.storeLoginedUser(request.getSession(), user);

        //
        int redirectId = -1;//Todo не видит redirectId
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (Exception e) {
            log.error(e);
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(redirectId);
        if (requestUri != null) {
            url = "redirect:/" + requestUri;
        } else {
            url = "redirect:/" + user.getAuthorities().get(0).name().toLowerCase();
        }
        return url;
    }
}

