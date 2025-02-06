package ru.alex.burdovitsin.mesh.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailOperationValidator.class)
@Target( {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailOperationConstraint {
    String message() default "e-mail operation structure incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
