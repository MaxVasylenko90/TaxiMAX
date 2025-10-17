package dev.mvasylenko.driverservice.handler;

import dev.mvasylenko.core.dto.ErrorResponseDto;
import dev.mvasylenko.driverservice.exception.DriverAlreadyHasCarException;
import dev.mvasylenko.driverservice.exception.DriverHasNoCarException;
import dev.mvasylenko.driverservice.exception.DriverNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DriverExceptionHandler {

    @ExceptionHandler(DriverNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleDriverNotFoundException(DriverNotFoundException ex) {
        return new ErrorResponseDto("DriverNotFoundException", ex.getMessage());
    }

    @ExceptionHandler(DriverHasNoCarException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDriverHasNoCarException(DriverHasNoCarException ex) {
        return new ErrorResponseDto("DriverHasNoCarException", ex.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleArithmeticException(ArithmeticException ex) {
        return new ErrorResponseDto("ArithmeticException", ex.getMessage());
    }

    @ExceptionHandler(DriverAlreadyHasCarException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleDriverAlreadyHasCarException(Exception ex) {
        return new ErrorResponseDto("DriverAlreadyHasCarException", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleCarAlreadyReservedException(Exception ex) {
        return new ErrorResponseDto("Exception", ex.getMessage());
    }
}
