package ua.training.restaurant.exceptions;

/**
 * Created by Student on 06.02.2020
 */
public class UserDataNotValidException extends Exception {
    public UserDataNotValidException() {
    }

    public UserDataNotValidException(String message) {
        super(message);
    }
}
