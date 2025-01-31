package ru.alex.burdovitsin.mesh.model.rest;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
