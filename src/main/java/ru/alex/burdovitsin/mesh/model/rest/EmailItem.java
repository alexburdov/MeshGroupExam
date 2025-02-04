package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description = "Item с e-mail пользователя")
@Setter
@Getter
public class EmailItem implements Serializable {
    private static final long serialVersionUID = 2615960455929269085L;

    @Schema(description = "E-Mail ID (Индетификатор e-mail)", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private Long emailId;

    @Schema(description = "e-mail пользователя", example = "tst@tst.tst", accessMode = Schema.AccessMode.READ_ONLY)
    @Email
    private String email;
}
