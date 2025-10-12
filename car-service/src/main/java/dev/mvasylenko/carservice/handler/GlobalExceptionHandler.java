package dev.mvasylenko.carservice.handler;

import dev.mvasylenko.carservice.exception.CarAlreadyReservedException;
import dev.mvasylenko.carservice.exception.CarNotFoundException;
import dev.mvasylenko.carservice.exception.NotAvailableCarsException;
import dev.mvasylenko.core.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAvailableCarsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotAvailableCars(NotAvailableCarsException ex) {
        return new ErrorResponseDto("NO_AVAILABLE_CARS_EXCEPTION", ex.getMessage());
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleCarNotFound(CarNotFoundException ex) {
        return new ErrorResponseDto("CAR_NOT_FOUND_EXCEPTION", ex.getMessage());
    }

    @ExceptionHandler(CarAlreadyReservedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleCarNotFound(CarAlreadyReservedException ex) {
        return new ErrorResponseDto("CAR_ALREADY_RESERVED_EXCEPTION", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleCarNotFound(IllegalArgumentException ex) {
        return new ErrorResponseDto("ILLEGAL_ARGUMENT_EXCEPTION", ex.getMessage());
    }
}
