package com.chatapp.tokens.configuration;

import org.jetbrains.annotations.Nullable;

public class Configuration {

    public static final String AWS_REGION = System.getenv("AWS_REGION");
    public static final String TABLE_NAME = System.getenv("TABLE_NAME");
    @Nullable
    public static final String DB_ENDPOINT_OVERRIDE = System.getenv("DB_ENDPOINT_OVERRIDE");

    private Properties properties;

    public Configuration() {
        this.properties = new PropertiesLoaderSSM().getProperties();
    }

    public Properties getProperties() {
        return properties;
    }

}
