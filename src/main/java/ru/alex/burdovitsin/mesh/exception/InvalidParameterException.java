package ru.alex.burdovitsin.mesh.exception;

public class InvalidParameterException extends RuntimeException {

    private static final String INVALID_PARAMETER_MESSAGE = "Invalid parameter: %s";

    public InvalidParameterException(String parameterName) {
        super(String.format(INVALID_PARAMETER_MESSAGE, parameterName));
    }
}
