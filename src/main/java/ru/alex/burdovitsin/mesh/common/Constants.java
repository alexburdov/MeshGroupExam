package ru.alex.burdovitsin.mesh.common;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Constants {

    public static final String DEFAULT_BALANCE_STRING = "0.00";

    public static final BigDecimal DEFAULT_BALANCE = new BigDecimal(DEFAULT_BALANCE_STRING);

    public static final long MILLISECOND_IN_SECOND = 1000;

    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized";

    public static final String NULL_VALIDATED_OBJECT_MESSAGE = "Validate object is Null";

    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Phone Number is invalid";

    public static final String INVALID_USER_ID_MESSAGE = "User ID is invalid";

    public static final String INVALID_PHONE_ID_MESSAGE = "Phone ID is invalid";

    public static final String INVALID_EMAIL_MESSAGE = "E-mail is invalid";

    public static final String INVALID_EMAIL_ID_MESSAGE = "E-mail ID is invalid";

}
