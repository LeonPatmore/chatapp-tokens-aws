package com.chatapp.tokens.domain.internal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect
public class GatewayResponse {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String body;
    private final Map<String, String> headers;
    private final int statusCode;

    public GatewayResponse(final Object body, final Map<String, String> headers, final int statusCode) {
        this.body = getBodyAsString(body);
        this.statusCode = statusCode;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
    }

    private static String getBodyAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "Unknown response body!";
        }
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
