package com.chatapp.tokens.domain.internal;

public class GatewayRequest<T, U> {

    private U pathParameters;
    private T body;

    public GatewayRequest(U pathParameters, T body) {
        this.pathParameters = pathParameters;
        this.body = body;
    }

    public U getPathParameters() {
        return pathParameters;
    }

    public T getBody() {
        return body;
    }

}
