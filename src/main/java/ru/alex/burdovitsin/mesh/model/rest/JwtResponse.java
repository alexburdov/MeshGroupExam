package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(description = "Ответ содержащий Token")
@Getter
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -6019381375692590343L;

    @Schema(description = "Token для авторизации")
    @NotBlank
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
