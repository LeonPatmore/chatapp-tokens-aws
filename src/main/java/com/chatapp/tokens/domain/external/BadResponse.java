package com.chatapp.tokens.domain.external;

import com.chatapp.tokens.utils.GatewayResponse;
import org.apache.http.HttpStatus;

import java.util.Collections;

public class BadResponse extends GatewayResponse {

    private static final int CODE = HttpStatus.SC_BAD_REQUEST;

    public BadResponse(String name, String reason) {
        super(new ResponseObject(name, reason), Collections.emptyMap(), CODE);
    }

    public static class ResponseObject {

        private String name;
        private String reason;

        ResponseObject(String name, String reason) {
            this.name = name;
            this.reason = reason;
        }

        public String getName() {
            return name;
        }

        public String getReason() {
            return reason;
        }

    }

}
