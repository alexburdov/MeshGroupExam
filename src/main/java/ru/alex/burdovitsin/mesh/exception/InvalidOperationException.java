package ru.alex.burdovitsin.mesh.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException() {
        super("Invalid operation");
    }
}
