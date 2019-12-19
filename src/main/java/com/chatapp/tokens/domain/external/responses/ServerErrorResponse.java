package com.chatapp.tokens.domain.external.responses;

import com.chatapp.tokens.domain.internal.GatewayResponse;
import org.apache.http.HttpStatus;

import java.util.Collections;

public class ServerErrorResponse extends GatewayResponse {

    private static final int CODE = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public ServerErrorResponse() {
        super(null, Collections.emptyMap(), CODE);
    }

}
