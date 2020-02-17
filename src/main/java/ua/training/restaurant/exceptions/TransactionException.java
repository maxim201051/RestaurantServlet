package ua.training.restaurant.exceptions;

/**
 * Created by Student on 17.02.2020
 */
public class TransactionException extends RuntimeException {
    public TransactionException() {
    }

    public TransactionException(String message) {
        super(message);
    }
}
