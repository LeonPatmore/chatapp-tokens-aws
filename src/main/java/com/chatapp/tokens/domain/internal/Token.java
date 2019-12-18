package com.chatapp.tokens.domain.internal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Token {

    private String id;
    private String token;

    public Token() {
        // For Jackson
    }

    public Token(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

        this.id = id;
    }

    public String getToken() {
    }

    public void setToken(String token) {
        this.token = token;
    }

}
