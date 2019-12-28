package com.chatapp.tokens.domain.internal;

import com.chatapp.tokens.domain.common.Provider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Token {

    private Provider provider;
    private String externalId;
    private String token;
    private int lifetimeSeconds;

    public Token() {
        // For Jackson
    }

    public Token(Provider provider, String externalId, String token, int lifetimeSeconds) {
        this.provider = provider;
        this.externalId = externalId;
        this.token = token;
        this.lifetimeSeconds = lifetimeSeconds;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLifetimeSeconds() {
        return lifetimeSeconds;
    }

    public void setLifetimeSeconds(int lifetimeSeconds) {
        this.lifetimeSeconds = lifetimeSeconds;
    }

}
