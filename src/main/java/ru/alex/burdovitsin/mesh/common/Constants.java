package ru.alex.burdovitsin.mesh.common;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Constants {

    public static final String DEFAULT_BALANCE_STRING = "0.00";
    public static final BigDecimal DEFAULT_BALANCE = new BigDecimal(DEFAULT_BALANCE_STRING);
}
