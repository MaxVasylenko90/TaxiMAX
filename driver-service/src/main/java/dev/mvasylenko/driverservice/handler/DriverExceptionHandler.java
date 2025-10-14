package dev.mvasylenko.driverservice.handler;

import dev.mvasylenko.core.dto.ErrorResponseDto;
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
        return new ErrorResponseDto("DRIVER_NOT_FOUND_EXCEPTION", ex.getMessage());
    }

    @ExceptionHandler(DriverHasNoCarException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDriverHasNoCarException(DriverHasNoCarException ex) {
        return new ErrorResponseDto("DRIVER_HAS_NO_CAR_EXCEPTION", ex.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleArithmeticException(ArithmeticException ex) {
        return new ErrorResponseDto("ARITHMETIC_EXCEPTION", ex.getMessage());
    }
}
