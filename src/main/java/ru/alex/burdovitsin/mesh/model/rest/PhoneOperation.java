package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.alex.burdovitsin.mesh.common.OperationTypes;
import ru.alex.burdovitsin.mesh.validator.PhoneOperationConstraint;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description = "Операция с телефоном пользователя")
@Setter
@Getter
@PhoneOperationConstraint
@ToString
public class PhoneOperation implements Serializable {
    private static final long serialVersionUID = 8747347580322301999L;

    @Schema(description = "Phone ID (Индетификатор телефона) - имеет значение при обновление и удаление телефона")
    private Long phoneId;

    @Schema(description = "Тип операции по работе с телефоном: Создание/Изменение/Удаление"
            , allowableValues = {"CREATE", "UPDATE", "DELETE"}
    )
    private OperationTypes operation;

    @Schema(description = "Телефон пользователя. При операции удаление может принимать значение удаляемого телефона")
    private String phoneNumber;
}
