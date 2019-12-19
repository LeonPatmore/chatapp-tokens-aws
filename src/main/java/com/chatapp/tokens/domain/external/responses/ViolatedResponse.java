package com.chatapp.tokens.domain.external.responses;

import com.chatapp.tokens.domain.internal.GatewayResponse;
import com.chatapp.tokens.utils.GenericViolation;
import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ViolatedResponse extends GatewayResponse {

    private static final int CODE = HttpStatus.SC_BAD_REQUEST;

    public ViolatedResponse(List<GenericViolation> genericViolations) {
        super(new ResponseObject(genericViolations), Collections.emptyMap(), CODE);
    }

    public static class ResponseObject {

        public ResponseObject(List<GenericViolation> genericViolations) {
            this.genericViolations = genericViolations;
        }

        public List<GenericViolation> getGenericViolations() {
            return genericViolations;
        }

        private List<GenericViolation> genericViolations;

    }

}
