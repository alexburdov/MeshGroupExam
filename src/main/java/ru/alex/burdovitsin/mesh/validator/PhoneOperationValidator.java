package ru.alex.burdovitsin.mesh.validator;

import ru.alex.burdovitsin.mesh.model.rest.PhoneOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class PhoneOperationValidator implements
        ConstraintValidator<PhoneOperationConstraint, PhoneOperation> {
    private static final Pattern PHONE_REGEXP_PATTERN = Pattern.compile("^(\\d{13}|\\s*)?$");

    private static final String NULL_VALIDATED_OBJECT_MESSAGE = "Validate object is Null";

    private static final String INVALID_PHONE_NUMBER_MESSAGE = "Phone Number is invalid";

    private static final String INVALID_USER_ID_MESSAGE = "User ID is invalid";

    private static final String INVALID_PHONE_ID_MESSAGE = "Phone ID is invalid";

    @Override
    public boolean isValid(PhoneOperation operation, ConstraintValidatorContext context) {
        boolean isValid = false;
        Set<String> messages = new HashSet<>();

        if (Objects.nonNull(operation)) {
            switch (operation.getOperation()) {
                case CREATE:
                    isValid = checkPhoneNumber(operation.getPhoneNumber(), messages)
                            && checkUserId(operation.getUserId(), messages);
                    break;
                case UPDATE:
                    isValid = checkPhoneNumber(operation.getPhoneNumber(), messages)
                            && checkPhoneId(operation.getPhoneId(), messages);
                    break;
                case DELETE:
                    isValid = checkPhoneId(operation.getPhoneId(), messages);
                    break;
            }
        } else {
            messages.add(NULL_VALIDATED_OBJECT_MESSAGE);
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.join(", ", messages)
            ).addConstraintViolation();
        }
        return isValid;
    }

    private boolean checkPhoneNumber(String phoneNumber, Set<String> messages) {
        if (Objects.nonNull(phoneNumber) && PHONE_REGEXP_PATTERN.matcher(phoneNumber).matches()) {
            return true;
        }
        messages.add(INVALID_PHONE_NUMBER_MESSAGE);
        return false;
    }

    private boolean checkUserId(Long userId, Set<String> messages) {
        if (Objects.nonNull(userId)) {
            return true;
        }
        messages.add(INVALID_USER_ID_MESSAGE);
        return false;
    }

    private boolean checkPhoneId(Long phoneId, Set<String> messages) {
        if (Objects.nonNull(phoneId)) {
            return true;
        }
        messages.add(INVALID_PHONE_ID_MESSAGE);
        return false;
    }
}
