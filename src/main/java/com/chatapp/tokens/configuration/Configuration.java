package com.chatapp.tokens.configuration;

public class Configuration {

    public static final String AWS_REGION = System.getenv("AWS_REGION");
    public static final String TABLE_NAME_TOKENS = System.getenv("TABLE_NAME_TOKENS");
    public static final String TABLE_NAME_SCHEDULER = System.getenv("TABLE_NAME_SCHEDULER");
    public static final String DB_ENDPOINT_OVERRIDE = System.getenv("DB_ENDPOINT_OVERRIDE");
    public static final String OVERRIDE_DB_ENDPOINT = System.getenv("OVERRIDE_DB_ENDPOINT");
    public static final String STATE_MACHINE_ENDPOINT_OVERRIDE = System.getenv("STATE_MACHINE_ENDPOINT_OVERRIDE");
    public static final String OVERRIDE_STATE_MACHINE_ENDPOINT = System.getenv("OVERRIDE_STATE_MACHINE_ENDPOINT");
    public static final String RENEW_STATE_MACHINE = System.getenv("RENEW_STATE_MACHINE");

    private Properties properties;

    public Configuration() {
        this.properties = new PropertiesLoaderSSM().getProperties();
    }

    public Properties getProperties() {
        return properties;
    }

}
