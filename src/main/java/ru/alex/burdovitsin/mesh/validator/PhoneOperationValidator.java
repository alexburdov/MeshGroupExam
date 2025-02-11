package ru.alex.burdovitsin.mesh.validator;

import ru.alex.burdovitsin.mesh.model.rest.PhoneOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static ru.alex.burdovitsin.mesh.common.Constants.*;

public class PhoneOperationValidator implements
        ConstraintValidator<PhoneOperationConstraint, PhoneOperation> {
    private static final Pattern PHONE_REGEXP_PATTERN = Pattern.compile("^(\\d{11}|\\s*)?$");

    @Override
    public boolean isValid(PhoneOperation operation, ConstraintValidatorContext context) {
        boolean isValid = false;
        Set<String> messages = new HashSet<>();

        if (Objects.nonNull(operation)) {
            switch (operation.getOperation()) {
                case CREATE:
                    isValid = checkPhoneNumber(operation.getPhoneNumber(), messages);
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

    private boolean checkPhoneId(Long phoneId, Set<String> messages) {
        if (Objects.nonNull(phoneId)) {
            return true;
        }
        messages.add(INVALID_PHONE_ID_MESSAGE);
        return false;
    }
}
