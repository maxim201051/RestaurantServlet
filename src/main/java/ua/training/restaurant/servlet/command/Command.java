package ua.training.restaurant.servlet.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Student on 22.01.2020
 */
public interface Command {
    String execute(HttpServletRequest request);
}