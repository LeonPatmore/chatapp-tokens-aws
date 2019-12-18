package com.chatapp.tokens.domain.external;

import com.chatapp.tokens.utils.GatewayResponse;
import org.apache.http.HttpStatus;

import java.util.Collections;

public class NotFoundResponse extends GatewayResponse<String> {

    private static final int CODE = HttpStatus.SC_NOT_FOUND;

    public NotFoundResponse() {
        super("", Collections.emptyMap(), CODE);
    }

}
