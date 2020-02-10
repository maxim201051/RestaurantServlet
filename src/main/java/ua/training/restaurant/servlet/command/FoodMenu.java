package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.DishService;
import ua.training.restaurant.service.DishServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class FoodMenu implements Command {
    final static Logger log = Logger.getLogger(FoodMenu.class);
    private DishService dishService;

    public FoodMenu() {
        dishService = new DishServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("getting food menu page");
        try {
            request.setAttribute("dishes", dishService.findAll());
        } catch (Exception e) {
            log.error(e);
        }
        return "WEB-INF/view/foodmenu.jsp";
    }
}
