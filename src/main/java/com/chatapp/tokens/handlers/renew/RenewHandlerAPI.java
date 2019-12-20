package com.chatapp.tokens.handlers.renew;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.external.requests.EmptyRequest;
import com.chatapp.tokens.domain.external.requests.RenewRequestPath;
import com.chatapp.tokens.domain.external.responses.NotFoundResponse;
import com.chatapp.tokens.domain.external.responses.ServerErrorResponse;
import com.chatapp.tokens.domain.internal.GatewayRequest;
import com.chatapp.tokens.domain.internal.GatewayResponse;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.ApiGatewayHandler;
import com.chatapp.tokens.store.CannotUpdateTokenException;
import com.chatapp.tokens.store.UnknownTokenException;

import javax.inject.Inject;
import java.util.Collections;

public class RenewHandlerAPI extends ApiGatewayHandler<EmptyRequest, RenewRequestPath> {

    @Inject
    public RenewHandler renewHandler;

    public RenewHandlerAPI() {
        super(EmptyRequest.class, RenewRequestPath.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    protected GatewayResponse handleRequest(GatewayRequest<EmptyRequest, RenewRequestPath> input) {
        try {
            Token renewedToken = renewHandler.renewToken(input.getPathParameters().getProvider(),
                                                         input.getPathParameters().getExternalId());
            return new GatewayResponse(renewedToken,
                                       Collections.singletonMap("Content-Type", "application/json"),
                                       200);
        } catch (CannotUpdateTokenException e) {
            return new ServerErrorResponse();
        } catch (UnknownTokenException e) {
            return new NotFoundResponse();
        }
    }

}
