package ua.training.restaurant.exceptions;

/**
 * Created by Student on 17.02.2020
 */
public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException() {
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}
