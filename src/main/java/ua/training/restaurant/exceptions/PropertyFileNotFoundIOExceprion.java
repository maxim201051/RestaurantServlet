package ua.training.restaurant.exceptions;

/**
 * Created by Student on 17.02.2020
 */
public class PropertyFileNotFoundIOExceprion extends RuntimeException {
    public PropertyFileNotFoundIOExceprion() {
    }

    public PropertyFileNotFoundIOExceprion(String message) {
        super(message);
    }
}
