package ru.alex.burdovitsin.mesh.model.rest;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ErrorResponse  implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String module;

    private final String cause;

    private final String exceptionMessage;

    public ErrorResponse(String module, String cause, String exceptionMessage) {

        this.module = module;
        this.cause = cause;
        this.exceptionMessage = exceptionMessage;
    }
}
