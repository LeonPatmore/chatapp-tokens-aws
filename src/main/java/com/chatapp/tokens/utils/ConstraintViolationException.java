package com.chatapp.tokens.utils;

import java.util.List;

public class ConstraintViolationException extends Exception {

    private List<GenericViolation> genericViolations;

    public ConstraintViolationException(List<GenericViolation> genericViolations) {
        this.genericViolations = genericViolations;
    }

    public List<GenericViolation> getGenericViolations() {
        return genericViolations;
    }

}
