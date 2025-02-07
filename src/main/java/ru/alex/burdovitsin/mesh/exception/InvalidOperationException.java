package ru.alex.burdovitsin.mesh.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException() {
        super("Invalid operation");
        log.error("Invalid operation");
    }

    public InvalidOperationException(String message) {
        super(message);
        log.error("Invalid operation: " + message);
    }
}
