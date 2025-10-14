package dev.mvasylenko.paymentservice.handler;

import dev.mvasylenko.core.dto.ErrorResponseDto;
import dev.mvasylenko.paymentservice.exception.PaymentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionHandler {
    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return new ErrorResponseDto("PAYMENT_NOT_FOUND_EXCEPTION", ex.getMessage());
    }
}
