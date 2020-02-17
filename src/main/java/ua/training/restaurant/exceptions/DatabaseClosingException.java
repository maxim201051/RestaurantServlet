package ua.training.restaurant.exceptions;

/**
 * Created by Student on 17.02.2020
 */
public class DatabaseClosingException extends RuntimeException {
    public DatabaseClosingException() {
    }

    public DatabaseClosingException(String message) {
        super(message);
    }
}
