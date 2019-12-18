package com.chatapp.tokens.handlers.get;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.domain.external.BadResponse;
import com.chatapp.tokens.domain.external.GetRequest;
import com.chatapp.tokens.domain.external.NotFoundResponse;
import com.chatapp.tokens.domain.external.ServerErrorResponse;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.handlers.Handler;
import com.chatapp.tokens.store.CannotGetTokenException;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.UnknownTokenException;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.GatewayResponse;
import com.chatapp.tokens.utils.JsonUtils;

import javax.inject.Inject;
import java.util.Collections;

public class GetHandler extends Handler<GetRequest> {

    @Inject
    public Configuration configuration;

    @Inject
    public TokensStore tokensStore;

    @Inject
    public JsonUtils jsonUtils;

    public GetHandler() {
        super(GetRequest.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public GatewayResponse handleRequest(GetRequest input) {
        try {
            Token token = tokensStore.getToken(input.getPathParameters().getProvider(),
                                               input.getPathParameters().getId());
            return new GatewayResponse<>(jsonUtils.toJson(token),
                                         Collections.singletonMap("Content-Type", "application/json"),
                                         200);
        } catch (CannotGetTokenException e) {
            return new BadResponse("Internal Problem", "We can not get your token!");
        } catch (UnknownTokenException e) {
            return new NotFoundResponse();
        } catch (DeserializableException e) {
            return new ServerErrorResponse();
        }
    }

}
