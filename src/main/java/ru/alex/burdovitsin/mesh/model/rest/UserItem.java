package ru.alex.burdovitsin.mesh.model.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Данные пользователя")
@Setter
@Getter
public class UserItem implements Serializable {
    private static final long serialVersionUID = 1645522695515829190L;

    @Schema(description = "USER ID (Индификатор пользователя)", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private Long userId;

    @Schema(description = "Имя пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    @Size(max = 500)
    private String name;

    @Schema(description = "Дата рождения пользователя", example = "01.05.1993", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @Schema(description = "Баланс пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private BigDecimal balance;

    @Schema(description = "Список телефонов пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    @NotEmpty
    private List<PhoneItem> phoneItems;

    @Schema(description = "Список e-mail адрессов пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    @NotEmpty
    private List<EmailItem> emailItems;
}
