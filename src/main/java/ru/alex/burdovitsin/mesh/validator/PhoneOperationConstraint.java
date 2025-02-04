package ru.alex.burdovitsin.mesh.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneOperationValidator.class)
@Target( {ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneOperationConstraint {
    String message() default "Phone operation structure incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
