package com.chatapp.tokens.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.chatapp.tokens.utils.ConstraintViolationException;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;
import com.chatapp.tokens.utils.ValidationUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class StateMachineHandler<T> implements RequestStreamHandler {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    protected JsonUtils jsonUtils;

    @Inject
    protected ValidationUtils validationUtils;

    private Class<T> targetObject;

    public StateMachineHandler(Class<T> targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) {
        String inputStr = jsonUtils.inputStreamToString(input);
        log.info("Received input: {}", inputStr);
        try {
            JsonNode rootNode = jsonUtils.getJsonNode(inputStr);
            T inputObject = jsonUtils.getObject(rootNode.get("Payload"), targetObject);
            validationUtils.validateObject(inputObject);
            handleRequest(inputObject);
        } catch (DeserializableException | ConstraintViolationException e) {
            log.error("Can not process request from state machine!", e);
        }
    }

    protected abstract void handleRequest(T input);

}
