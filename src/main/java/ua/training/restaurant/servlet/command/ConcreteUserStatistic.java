package ua.training.restaurant.servlet.command;

import org.apache.log4j.Logger;
import ua.training.restaurant.service.KitchenService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 29.01.2020
 */
public class ConcreteUserStatistic implements Command {//todo
    final static Logger log = Logger.getLogger(ConcreteUserStatistic.class);


    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
