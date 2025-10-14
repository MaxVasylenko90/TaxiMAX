package dev.mvasylenko.driverservice.exception;

public class DriverHasNoCarException extends RuntimeException {

    public DriverHasNoCarException(String message) {
        super(message);
    }
}
