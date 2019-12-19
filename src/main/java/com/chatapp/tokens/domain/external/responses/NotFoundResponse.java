package com.chatapp.tokens.domain.external.responses;

import com.chatapp.tokens.domain.internal.GatewayResponse;
import org.apache.http.HttpStatus;

import java.util.Collections;

public class NotFoundResponse extends GatewayResponse {

    private static final int CODE = HttpStatus.SC_NOT_FOUND;

    public NotFoundResponse() {
        super(null, Collections.emptyMap(), CODE);
    }

}
