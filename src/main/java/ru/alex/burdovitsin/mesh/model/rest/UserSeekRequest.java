package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "Запрос для получения списка пользователей")
@Validated
@Setter
@Getter
@ToString
public class UserSeekRequest implements Serializable {

    private static final long serialVersionUID = -6364157684823446214L;

    @Schema(description = "Размер страницы. NULL - если не задана постраничная разметка")
    @Min(0)
    @Max(200)
    @Null
    private Integer pageSize;

    @Min(0)
    @Schema(description = "Номер страницы. NULL - если не задана постраничная разметка")
    private Integer pageOffset;

    @Schema(description = "Имя пользователя. Без учета окончания")
    private String nameStartFrom;

    @Schema(description = "Дата рождения. Больше или равно")
    private LocalDate dateOfBirthFrom;

    @Schema(description = "Номер телефона. Полное совпадение")
    private String phoneNumberFull;

    @Schema(description = "E-mail пользователя. Полное совпадение.")
    private String emailFull;
}
