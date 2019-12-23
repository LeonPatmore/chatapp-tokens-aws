package com.chatapp.tokens.store;

public class TokenAlreadyExistsException extends Exception {

    public TokenAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
