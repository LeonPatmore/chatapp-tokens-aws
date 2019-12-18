package com.chatapp.tokens.domain.external;

import com.chatapp.tokens.utils.GatewayResponse;
import org.apache.http.HttpStatus;

import java.util.Collections;

public class ServerErrorResponse extends GatewayResponse {

    private static final int CODE = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public ServerErrorResponse() {
        super("", Collections.emptyMap(), CODE);
    }

}
