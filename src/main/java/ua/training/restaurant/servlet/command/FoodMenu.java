package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.DishService;
import ua.training.restaurant.service.DishServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class FoodMenu implements Command {
    private static final int RECORDS_PER_PAGE = 10;
    private final static Logger log = Logger.getLogger(FoodMenu.class);
    private DishService dishService;

    public FoodMenu() {
        dishService = new DishServiceImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page")) - 1;
        log.info("getting food menu page");
        try {
            request.setAttribute("dishes", dishService.findAll(page, RECORDS_PER_PAGE));
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        return "WEB-INF/view/foodmenu.jsp";
    }
}
