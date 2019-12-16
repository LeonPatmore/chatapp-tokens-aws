package com.chatapp.tokens.configuration;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Properties {

    private String dbHost;

    private String facebookEndpoint;

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getFacebookEndpoint() {
        return facebookEndpoint;
    }

    public void setFacebookEndpoint(String facebookEndpoint) {
        this.facebookEndpoint = facebookEndpoint;
    }

}
