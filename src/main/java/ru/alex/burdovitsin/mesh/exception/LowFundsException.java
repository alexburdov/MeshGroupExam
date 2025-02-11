package ru.alex.burdovitsin.mesh.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LowFundsException extends RuntimeException {
    public LowFundsException() {
        log.error("Low Funds Exception");
    }
}
