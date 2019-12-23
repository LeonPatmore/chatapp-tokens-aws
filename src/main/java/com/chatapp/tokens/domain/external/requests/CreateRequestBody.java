package com.chatapp.tokens.domain.external.requests;

import com.chatapp.tokens.domain.internal.TokenCredentials;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotNull;

@JsonAutoDetect
public class CreateRequestBody {

    private TokenCredentials credentials;

    @NotNull
    private String externalId;

    public TokenCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(TokenCredentials credentials) {
        this.credentials = credentials;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

}
