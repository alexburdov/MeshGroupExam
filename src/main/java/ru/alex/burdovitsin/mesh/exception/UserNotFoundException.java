package ru.alex.burdovitsin.mesh.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {

    private final static String USER_NOT_FOUND_MESSAGE = "User \"%s\" not found";

    public UserNotFoundException(String userName) {
        super(String.format(USER_NOT_FOUND_MESSAGE, userName));
        log.error("User \"{}\" not found", userName);
    }
}
