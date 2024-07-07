package com.backend.JavaBackend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FNameValidator implements ConstraintValidator<ValidateFNameType, String> {
    @Override
    public boolean isValid(String fNameType, ConstraintValidatorContext constraintValidatorContext) {
       return !fNameType.isEmpty();
    }
}
