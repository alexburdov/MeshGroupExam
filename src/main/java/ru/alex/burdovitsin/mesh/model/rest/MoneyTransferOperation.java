package ru.alex.burdovitsin.mesh.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Операция по переводе денег")
@Setter
@Getter
@ToString
public class MoneyTransferOperation implements Serializable {

    private static final long serialVersionUID = -4154376453919714789L;

    @Schema(description = "UserID получателя перевода")
    @NotNull
    private Long transferToUser;

    @Schema(description = "Размер перевода")
    @NotNull
    private BigDecimal transferAmount;
}
