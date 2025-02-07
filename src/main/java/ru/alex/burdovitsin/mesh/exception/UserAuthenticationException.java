package ru.alex.burdovitsin.mesh.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthenticationException extends RuntimeException {
    private final static String USER_NOT_AUTH_MESSAGE = "User Authentication failed";

    public UserAuthenticationException() {
        super(USER_NOT_AUTH_MESSAGE);
        log.error("UserAuthenticationException");
    }
}