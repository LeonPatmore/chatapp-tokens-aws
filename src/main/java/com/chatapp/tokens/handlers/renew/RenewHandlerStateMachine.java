package com.chatapp.tokens.handlers.renew;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.external.requests.RenewRequest;
import com.chatapp.tokens.handlers.StateMachineHandler;
import com.chatapp.tokens.store.CannotUpdateTokenException;
import com.chatapp.tokens.store.UnknownTokenException;
import com.chatapp.tokens.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


public class RenewHandlerStateMachine extends StateMachineHandler<RenewRequest> {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    public JsonUtils jsonUtils;

    @Inject
    public RenewHandler renewHandler;

    public RenewHandlerStateMachine() {
        super(RenewRequest.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    protected void handleRequest(RenewRequest input) {
        try {
            renewHandler.renewToken(input.getProvider(), input.getExternalId());
        } catch (CannotUpdateTokenException | UnknownTokenException e) {
            log.error("Could not renew token!", e);
            throw new RuntimeException(e);
        }
    }

}
