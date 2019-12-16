package com.chatapp.tokens.configuration;

public class Configuration {

    static final String AWS_REGION = System.getenv("AWS_REGION");

    private Properties chatappTokensProperties;

    public Configuration() {
        this.chatappTokensProperties = new PropertiesLoaderSSM().getProperties();
    }

    public Properties getChatappTokensProperties() {
        return chatappTokensProperties;
    }
}
