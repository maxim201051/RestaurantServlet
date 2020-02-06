package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.DaoFactory;
import ua.training.restaurant.dao.UserDao;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.utils.AppUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 22.01.2020
 */
public class Login implements Command {
    final static Logger log = Logger.getLogger(Login.class);

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
        return "/WEB-INF/view/login.jsp";
    }

    private String doPost(HttpServletRequest request) {
        String url;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DaoFactory daoFactory = DaoFactory.getInstance();//TODO
        User user = null;
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findByUsernameAndPassword(username, password).orElseGet(null);
        } catch (Exception e) {
            log.error(e);
        }

        //DataDAO.findByUsernameAndPassword(username, password);


        if (user == null) {
            String errorMessage = "errorMessage";
            request.setAttribute("errorMessage", errorMessage);
            url = "/WEB-INF/view/login.jsp";
            return url;
        }

        AppUtils.storeLoginedUser(request.getSession(), user);

        //
        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (Exception e) {
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(redirectId);
        if (requestUri != null) {
            url = "redirect:/" + requestUri;
        } else {
            // По умолчанию после успешного входа в систему
            // перенаправить на страницу роли
            url = "redirect:/signup"; //TODO
            //url = request.getContextPath() + "/" + userAccount.getAuthorities().get(0).getAuthority().toLowerCase();
        }
        return url;
    }
}

