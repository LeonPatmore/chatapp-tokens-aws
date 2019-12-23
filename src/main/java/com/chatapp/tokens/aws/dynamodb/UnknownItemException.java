package com.chatapp.tokens.aws.dynamodb;

public class UnknownItemException extends Exception {

    public UnknownItemException() {}

    public UnknownItemException(Throwable cause) {
        super(cause);
    }

}
