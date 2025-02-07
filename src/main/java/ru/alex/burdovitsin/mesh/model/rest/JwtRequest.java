package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Запрос для аудинтификации")
@Getter
@NoArgsConstructor
@ToString
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 8275168691657327882L;

    @Schema(description = "Имя пользователя")
    @NotBlank
    @Size(max = 500)
    private String username;

    @Schema(description = "Пароль")
    @NotBlank
    private String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
