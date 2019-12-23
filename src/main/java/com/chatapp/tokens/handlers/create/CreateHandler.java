package com.chatapp.tokens.handlers.create;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.external.requests.CreateRequestBody;
import com.chatapp.tokens.domain.external.requests.CreateRequestPath;
import com.chatapp.tokens.domain.external.responses.BadResponse;
import com.chatapp.tokens.domain.external.responses.ServerErrorResponse;
import com.chatapp.tokens.domain.internal.GatewayRequest;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.ApiGatewayHandler;
import com.chatapp.tokens.scheduler.RenewScheduler;
import com.chatapp.tokens.store.CannotPutTokenException;
import com.chatapp.tokens.store.TokenAlreadyExistsException;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.domain.internal.GatewayResponse;

import javax.inject.Inject;
import java.util.Collections;

public class CreateHandler extends ApiGatewayHandler<CreateRequestBody, CreateRequestPath> {

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
            Token newToken = new Token(input.getPathParameters().getProvider(),
                                       input.getBody().getExternalId(),
                                       "some cool shit!");
            tokensStore.putToken(newToken);
            renewScheduler.scheduleTokenRenewal(newToken.getProvider(), newToken.getExternalId(), 20);
            return new GatewayResponse(newToken,
                                       Collections.singletonMap("Content-Type", "application/json"),
                                       200);
        } catch (CannotPutTokenException e) {
            return new ServerErrorResponse();
        } catch (TokenAlreadyExistsException e) {
            return new BadResponse("Token", "already exists") ;
        }
    }

}
