package com.chatapp.tokens.handlers.create;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.external.requests.CreateRequestBody;
import com.chatapp.tokens.domain.external.requests.CreateRequestPath;
import com.chatapp.tokens.domain.external.responses.BadResponse;
import com.chatapp.tokens.domain.internal.GatewayRequest;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.ApiGatewayHandler;
import com.chatapp.tokens.scheduler.RenewScheduler;
import com.chatapp.tokens.store.TokenAlreadyExistsException;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.domain.internal.GatewayResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;

public class CreateHandler extends ApiGatewayHandler<CreateRequestBody, CreateRequestPath> {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    public TokensStore tokensStore;

    @Inject
    public RenewScheduler renewScheduler;

    public CreateHandler() {
        super(CreateRequestBody.class, CreateRequestPath.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public GatewayResponse handleRequest(GatewayRequest<CreateRequestBody, CreateRequestPath> input) {
        try {
            Provider provider = input.getPathParameters().getProvider();
            String externalId = input.getBody().getExternalId();
            log.info("Trying to create a token for provider [ {} ], external id [ {} ]",
                     provider.name(),
                     externalId);
            int lifetimeSeconds = 300;
            Token newToken = new Token(provider, externalId, "some cool shit!", lifetimeSeconds);
            tokensStore.putToken(newToken);
            renewScheduler.scheduleTokenRenewal(provider, externalId, lifetimeSeconds);
            return new GatewayResponse(newToken,
                                       Collections.singletonMap("Content-Type", "application/json"),
                                       200);
        } catch (TokenAlreadyExistsException e) {
            return new BadResponse("Token", "already exists");
        }
    }

}
