package ru.alex.burdovitsin.mesh.controller.handler;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alex.burdovitsin.mesh.exception.InvalidOperationException;
import ru.alex.burdovitsin.mesh.exception.UserAuthenticationException;
import ru.alex.burdovitsin.mesh.exception.UserNotFoundException;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Set<Pair<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Set<Pair<String, String>> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(
                (error)
                        -> errors.add(
                        Pair.of(error.getObjectName(), error.getDefaultMessage())
                )
        );
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(InvalidOperationException.class)
    public String handleInvalidOperationException(InvalidOperationException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserAuthenticationException.class)
    public String handleUserAuthenticationException(UserAuthenticationException ex) {
        return ex.getMessage();
    }
}
