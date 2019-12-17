package com.chatapp.tokens.handlers.get;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.domain.external.GetRequest;
import com.chatapp.tokens.handlers.Handler;
import com.chatapp.tokens.utils.GatewayResponse;

import javax.inject.Inject;
import java.util.Collections;

public class GetHandler extends Handler<GetRequest> {

    @Inject
    public Configuration configuration;

    public GetHandler() {
        super(GetRequest.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public GatewayResponse handleRequest(GetRequest input) {
        return new GatewayResponse<>(input.getPathParameters().getProvider() + configuration.getChatappTokensProperties().getDbHost(),
                                     Collections.singletonMap("Content-Type", "application/json"),
                                     200);
    }

}
