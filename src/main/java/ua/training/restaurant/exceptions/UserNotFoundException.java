package ua.training.restaurant.exceptions;

/**
 * Created by Student on 02.02.2020
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
