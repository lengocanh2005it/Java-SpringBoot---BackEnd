package com.backend.JavaBackend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FNameValidator.class)
public @interface ValidateFNameType {
    public String message() default "FIRST_NAME_INVALID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};
}
