package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.KitchenService;
import ua.training.restaurant.service.UserService;
import ua.training.restaurant.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 22.01.2020
 */
public class SignUp implements Command {
    final static Logger log = Logger.getLogger(SignUp.class);
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
        log.info("trying to register new user");
        if (result.hasErrors()) {
            log.error("Invalid data");
            modelAndView.addObject("failureMessage", "signup.label.error");
            modelAndView.setViewName("signup");
        } else {
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.setDefaultParams(user);
                userService.saveOrUpdate(user);
                modelAndView.setViewName("redirect:/login");
            } catch (Exception e) {
                log.error("username already exists");
                modelAndView.addObject("failureMessage", "signup.label.alreadyRegistered");
                modelAndView.setViewName("signup");
            }
        }
        return modelAndView;
        return url;
    }
}
