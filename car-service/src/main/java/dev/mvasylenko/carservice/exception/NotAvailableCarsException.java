package dev.mvasylenko.carservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotAvailableCarsException extends RuntimeException {
    public NotAvailableCarsException(String message) {
        super(message);
    }
}
