package dev.mvasylenko.carservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarAlreadyReservedException extends RuntimeException {
    public CarAlreadyReservedException(String message) {
        super(message);
    }
}
