package com.chatapp.tokens.handlers.create;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.domain.external.CreateRequest;
import com.chatapp.tokens.domain.external.BadResponse;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.Handler;
import com.chatapp.tokens.store.CannotPutTokenException;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.utils.GatewayResponse;

import javax.inject.Inject;
import java.util.Collections;

public class CreateHandler extends Handler<CreateRequest> {

    @Inject
    public Configuration configuration;

    @Inject
    public TokensStore tokensStore;

    public CreateHandler() {
        super(CreateRequest.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public GatewayResponse handleRequest(CreateRequest input) {
        try {
            tokensStore.putToken(new Token("whatsapp-b", "hi!!!"));
            return new GatewayResponse<>("",
                                         Collections.singletonMap("Content-Type", "application/json"),
                                         200);
        } catch (CannotPutTokenException e) {
            return new BadResponse("Internal Problem", "We can not put your token into the DB!");
        }
    }

}
