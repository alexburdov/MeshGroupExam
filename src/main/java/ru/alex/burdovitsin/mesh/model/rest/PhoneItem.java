package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Schema(description = "Item с телефоном пользователя")
@Setter
@Getter
public class PhoneItem implements Serializable {
    private static final long serialVersionUID = -4574644957102091236L;

    @Schema(description = "Phone ID (Индетификатор телефона)", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private Long phoneId;

    @Schema(description = "Телефон пользователя", example = "79207865432", accessMode = Schema.AccessMode.READ_ONLY)
    @Pattern(regexp = "^(\\d{13}|\\s*)?$")
    private String phoneNumber;
}
