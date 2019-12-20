package com.chatapp.tokens.handlers.get;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.external.requests.EmptyRequest;
import com.chatapp.tokens.domain.external.requests.GetRequestPath;
import com.chatapp.tokens.domain.external.responses.BadResponse;
import com.chatapp.tokens.domain.external.responses.NotFoundResponse;
import com.chatapp.tokens.domain.internal.GatewayRequest;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.ApiGatewayHandler;
import com.chatapp.tokens.store.CannotGetTokenException;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.UnknownTokenException;
import com.chatapp.tokens.domain.internal.GatewayResponse;

import javax.inject.Inject;
import java.util.Collections;

public class GetHandler extends ApiGatewayHandler<EmptyRequest, GetRequestPath> {

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
            Token token = tokensStore.getToken(input.getPathParameters().getProvider(),
                                               input.getPathParameters().getExternalId());
            return new GatewayResponse(token,
                                       Collections.singletonMap("Content-Type", "application/json"),
                                       200);
        } catch (CannotGetTokenException e) {
            return new BadResponse("Internal Problem", "We can not get your token!");
        } catch (UnknownTokenException e) {
            return new NotFoundResponse();
        }
    }

}
