package com.chatapp.tokens.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.chatapp.tokens.domain.external.requests.EmptyRequest;
import com.chatapp.tokens.domain.external.responses.BadResponse;
import com.chatapp.tokens.domain.external.responses.ViolatedResponse;
import com.chatapp.tokens.domain.internal.GatewayRequest;
import com.chatapp.tokens.domain.internal.GatewayResponse;
import com.chatapp.tokens.utils.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.*;

public abstract class ApiGatewayHandler<T, U> implements RequestStreamHandler {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    protected JsonUtils jsonUtils;

    @Inject
    protected ValidationUtils validationUtils;

    private Class<T> targetBodyObject;

    private Class<U> targetPathParametersObject;

    public ApiGatewayHandler(Class<T> targetBodyObject, Class<U> targetPathParametersObject) {
        this.targetBodyObject = targetBodyObject;
        this.targetPathParametersObject = targetPathParametersObject;
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) {
        String inputStr = jsonUtils.inputStreamToString(input);
        log.info("Received input: {}", inputStr);
        GatewayResponse response;
        try {
            JsonNode rootNode = jsonUtils.getJsonNode(inputStr);
            U pathParameters = getPathParameters(rootNode);
            validationUtils.validateObject(pathParameters);
            T body = getBody(rootNode);
            if (targetBodyObject != EmptyRequest.class) {
                validationUtils.validateObject(body);
            }
            response = handleRequest(new GatewayRequest<>(pathParameters, body));
        } catch (DeserializableException e) {
            log.error(e);
            response = new BadResponse("Bad Request", "Could not de-serialize request payload!");
        } catch (ConstraintViolationException e) {
            log.error(e);
            response = new ViolatedResponse(e.getGenericViolations());
        }
        jsonUtils.writeObject(output, response);
    }

    private U getPathParameters(JsonNode rootNode) throws DeserializableException {
        JsonNode pathParametersNode = rootNode.get("pathParameters");
        return jsonUtils.getObject(pathParametersNode, targetPathParametersObject);
    }

    private T getBody(JsonNode rootNode) throws DeserializableException {
        String body = rootNode.get("body").asText();
        return jsonUtils.getObject(body, targetBodyObject);
    }

    protected abstract GatewayResponse handleRequest(GatewayRequest<T, U> input);

}
