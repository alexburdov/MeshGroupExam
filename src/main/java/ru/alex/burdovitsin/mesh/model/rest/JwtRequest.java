package ru.alex.burdovitsin.mesh.model.rest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
