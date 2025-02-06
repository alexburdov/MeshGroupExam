package ru.alex.burdovitsin.mesh.validator;

import ru.alex.burdovitsin.mesh.model.rest.EmailOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static ru.alex.burdovitsin.mesh.common.Constants.*;

public class EmailOperationValidator implements
        ConstraintValidator<EmailOperationConstraint, EmailOperation> {

    private final static Pattern EMAIL_REGEXP_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(EmailOperation operation, ConstraintValidatorContext context) {
        boolean isValid = false;
        Set<String> messages = new HashSet<>();

        if (Objects.nonNull(operation)) {
            switch (operation.getOperation()) {
                case CREATE:
                    isValid = checkEmail(operation.getEmail(), messages)
                            && checkUserId(operation.getUserId(), messages);
                    break;
                case UPDATE:
                    isValid = checkEmail(operation.getEmail(), messages)
                            && checkEmailId(operation.getEmailId(), messages);
                    break;
                case DELETE:
                    isValid = checkEmailId(operation.getEmailId(), messages);
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

    private boolean checkEmail(String email, Set<String> messages) {
        if (Objects.nonNull(email) && EMAIL_REGEXP_PATTERN.matcher(email).matches()) {
            return true;
        }
        messages.add(INVALID_EMAIL_MESSAGE);
        return false;
    }

    private boolean checkUserId(Long userId, Set<String> messages) {
        if (Objects.nonNull(userId)) {
            return true;
        }
        messages.add(INVALID_USER_ID_MESSAGE);
        return false;
    }

    private boolean checkEmailId(Long emailId, Set<String> messages) {
        if (Objects.nonNull(emailId)) {
            return true;
        }
        messages.add(INVALID_EMAIL_ID_MESSAGE);
        return false;
    }
}
