package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.alex.burdovitsin.mesh.common.OperationTypes;
import ru.alex.burdovitsin.mesh.validator.EmailOperationConstraint;

import java.io.Serializable;

@Schema(description = "Операция с e-mail пользователя")
@Setter
@Getter
@EmailOperationConstraint
public class EmailOperation implements Serializable {
    private static final long serialVersionUID = -6070785264299597707L;

    @Schema(description = "E-Mail ID (Индетификатор e-mail) - имеет значение при обновление и удаление e-mail")
    private Long emailId;

    @Schema(description = "Тип операции по работе с e-mail: Создание/Изменение/Удаление"
            , allowableValues = {"CREATE", "UPDATE", "DELETE"}
    )
    private OperationTypes operation;

    @Schema(description = "e-mail пользователя. При операции удаление может принимать значение удаляемого e-mail")
    private String email;
}
