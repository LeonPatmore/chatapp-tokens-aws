package com.chatapp.tokens.configuration;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.aws.SimpleSSMClient;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import static com.chatapp.tokens.configuration.Configuration.AWS_REGION;

public class PropertiesLoaderSSM implements PropertiesLoader {

    private final Logger log = LogManager.getLogger(getClass());

    private SimpleSSMClient simpleSSMClient;

    @Inject
    public JsonUtils jsonUtils;

    private final ApplicationComponent applicationComponent;

    public PropertiesLoaderSSM() {
        applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        this.simpleSSMClient = new SimpleSSMClient(AWS_REGION);
    }

    private static String getPropertiesParamKey() {
        return System.getenv("CONFIG_PARAM_KEY");
    }

    @Override
    public Properties getProperties() {
        log.info("Param key: {}", getPropertiesParamKey());
        String propertiesJson = simpleSSMClient.getParameter(getPropertiesParamKey(), true);
        try {
            return jsonUtils.getObject(propertiesJson, Properties.class);
        } catch (DeserializableException e) {
            throw new InvalidPropertiesException("Cannot de-serialize properties: " + propertiesJson);
        }
    }

}
