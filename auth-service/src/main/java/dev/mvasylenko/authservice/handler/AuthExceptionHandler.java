package dev.mvasylenko.authservice.handler;

import dev.mvasylenko.authservice.exception.RegistrationFailedException;
import dev.mvasylenko.core.dto.ErrorResponseDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleRuntimeException(RuntimeException e) {
        return new ErrorResponseDto("RUNTIME_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ErrorResponseDto("USER_NOT_FOUND_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleJwtException(JwtException e) {
        return new ErrorResponseDto("JWT_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleAuthenticationException(AuthenticationException e) {
        return new ErrorResponseDto("AUTHENTICATION_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleServletException(ServletException e) {
        return new ErrorResponseDto("SERVLET_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleIoException(IOException e) {
        return new ErrorResponseDto("IO_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleNoSuchElementException(NoSuchElementException e) {
        return new ErrorResponseDto("NO_SUCH_ELEMENT_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(RegistrationFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleRegistrationFailedException(RegistrationFailedException e) {
        return new ErrorResponseDto("REGISTRATION_FAILED_EXCEPTION", e.getMessage());
    }
}
