package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(description = "Сообщение о ошибке")
@Setter
@Getter
@ToString
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 8585395365359767271L;

    @NotBlank
    @Schema(description = "Наименование модуля")
    private final String module;

    @Schema(description = "Причина ошибки")
    @NotBlank
    private final String cause;

    @Schema(description = "Сообщение из тела Exception")
    @NotBlank
    private final String exceptionMessage;

    public ErrorResponse(String module, String cause, String exceptionMessage) {
        this.module = module;
        this.cause = cause;
        this.exceptionMessage = exceptionMessage;
    }
}
