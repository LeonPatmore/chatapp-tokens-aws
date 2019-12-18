package com.chatapp.tokens.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {

    private final Logger log = LogManager.getLogger(getClass());

    public static final String AWS_REGION = System.getenv("AWS_REGION");
    public static final String TABLE_NAME = System.getenv("TABLE_NAME");
    public static final String DB_ENDPOINT_OVERRIDE = System.getenv("DB_ENDPOINT_OVERRIDE");
    public static final String OVERRIDE_DB_ENDPOINT = System.getenv("OVERRIDE_DB_ENDPOINT");

    private Properties properties;

    public Configuration() {
        log.info("Table name: [ {} ], DB endpoint override: [ {} ]", TABLE_NAME, DB_ENDPOINT_OVERRIDE);
        this.properties = new PropertiesLoaderSSM().getProperties();
    }

    public Properties getProperties() {
        return properties;
    }

}
