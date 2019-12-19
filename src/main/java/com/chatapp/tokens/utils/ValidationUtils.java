package com.chatapp.tokens.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationUtils {

    private Validator validator;

    public ValidationUtils() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public <T> void validateObject(T object) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            List<GenericViolation> genericViolations = new ArrayList<>();
            for (ConstraintViolation<T> violation : violations) {
                genericViolations.add(new GenericViolation(violation.getPropertyPath().toString(),
                                                           violation.getMessage(),
                                                           violation.getInvalidValue()));
            }
            throw new ConstraintViolationException(genericViolations);
        }
    }

}
