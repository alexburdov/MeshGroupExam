package ru.alex.burdovitsin.mesh.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
