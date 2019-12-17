package com.chatapp.tokens.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.chatapp.tokens.domain.external.ErrorResponse;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.GatewayResponse;
import com.chatapp.tokens.utils.JsonUtils;

import javax.inject.Inject;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Handler<T> implements RequestStreamHandler {

    @Inject
    protected JsonUtils jsonUtils;

    private Class<T> targetRequestObject;

    public Handler(Class<T> targetRequestObject) {
        this.targetRequestObject = targetRequestObject;
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) {
        try {
            T request = jsonUtils.getObject(input, targetRequestObject);
            GatewayResponse response = handleRequest(request);
            jsonUtils.writeObject(output, response);
        } catch (DeserializableException e) {
            jsonUtils.writeObject(output, new ErrorResponse("Bad Request",
                                                            "Could not de-serialize request payload!"));
        }
    }

    public abstract GatewayResponse handleRequest(T input);

}
