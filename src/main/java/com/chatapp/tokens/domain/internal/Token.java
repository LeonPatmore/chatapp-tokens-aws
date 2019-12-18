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

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
