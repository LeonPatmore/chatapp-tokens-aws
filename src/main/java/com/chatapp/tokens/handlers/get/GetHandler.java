package com.chatapp.tokens.handlers.get;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.utils.GatewayResponse;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;

import javax.inject.Inject;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

public class GetHandler implements RequestStreamHandler {

    @Inject
    public JsonUtils jsonUtils;

    @Inject
    public Configuration configuration;

    private final ApplicationComponent applicationComponent;
//    private final ConfigurationComponent configurationComponent;

    public GetHandler() {
        applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) {
        try {
            GetRequest getRequest = jsonUtils.getObject(input, GetRequest.class);
            GatewayResponse response = new GatewayResponse<>(getRequest.getPathParameters().getProvider() + configuration.getChatappTokensProperties().getDbHost(),
                                                             Collections.singletonMap("Content-Type", "application/json"),
                                                             200);
            jsonUtils.writeObject(output, response);
        } catch (DeserializableException e) {
            e.printStackTrace();
        }
    }
}
