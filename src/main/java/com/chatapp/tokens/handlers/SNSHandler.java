package com.chatapp.tokens.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.chatapp.tokens.utils.ConstraintViolationException;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;
import com.chatapp.tokens.utils.ValidationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public abstract class SNSHandler<T> implements RequestHandler<SNSEvent, Void> {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    protected JsonUtils jsonUtils;

    @Inject
    protected ValidationUtils validationUtils;

    private Class<T> targetMessageObject;

    public SNSHandler(Class<T> targetMessageObject) {
        this.targetMessageObject = targetMessageObject;
    }

    @Override
    public Void handleRequest(SNSEvent input, Context context) {
        log.info("Handling [ {} ] SNS records!", input.getRecords().size());
        for (SNSEvent.SNSRecord snsRecord : input.getRecords()) {
            try {
                T request = jsonUtils.getObject(snsRecord.getSNS().getMessage(), targetMessageObject);
                validationUtils.validateObject(request);
                handleRequest(request);
            } catch (DeserializableException | ConstraintViolationException e) {
                log.error(e);
            }
        }
        return null;
    }

    protected abstract void handleRequest(T input);

}
