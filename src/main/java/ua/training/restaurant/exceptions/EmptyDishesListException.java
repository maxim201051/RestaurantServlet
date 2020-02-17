package ua.training.restaurant.exceptions;

/**
 * Created by Student on 17.02.2020
 */
public class EmptyDishesListException extends RuntimeException {
    public EmptyDishesListException() {
    }

    public EmptyDishesListException(String message) {
        super(message);
    }
}
