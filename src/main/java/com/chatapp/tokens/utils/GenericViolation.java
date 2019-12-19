package com.chatapp.tokens.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class GenericViolation {

    private String path;
    private String message;
    private Object value;

    public GenericViolation(String path, String message, Object value) {
        this.path = path;
        this.message = message;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }

}
