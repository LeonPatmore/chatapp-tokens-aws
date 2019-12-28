package com.chatapp.tokens.handlers.get;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.external.requests.EmptyRequest;
import com.chatapp.tokens.domain.external.requests.GetRequestPath;
import com.chatapp.tokens.domain.external.responses.NotFoundResponse;
import com.chatapp.tokens.domain.internal.GatewayRequest;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.ApiGatewayHandler;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.UnknownTokenException;
import com.chatapp.tokens.domain.internal.GatewayResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;

public class GetHandler extends ApiGatewayHandler<EmptyRequest, GetRequestPath> {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    public TokensStore tokensStore;

    public GetHandler() {
        super(EmptyRequest.class, GetRequestPath.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public GatewayResponse handleRequest(GatewayRequest<EmptyRequest, GetRequestPath> input) {
        try {
            Provider provider = input.getPathParameters().getProvider();
            String externalId = input.getPathParameters().getExternalId();
            log.info("Trying to get token for provider [ {} ], external ID [ {} ]",
                     provider.name(),
                     externalId);
            Token token = tokensStore.getToken(provider, externalId);
            return new GatewayResponse(token,
                                       Collections.singletonMap("Content-Type", "application/json"),
                                       200);
        } catch (UnknownTokenException e) {
            return new NotFoundResponse();
        }
    }

}
