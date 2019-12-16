package com.chatapp.tokens;

import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.configuration.ConfigurationModule;
import com.chatapp.tokens.configuration.PropertiesLoaderSSM;
import com.chatapp.tokens.handlers.get.GetHandler;
import com.chatapp.tokens.utils.JsonUtils;
import com.chatapp.tokens.utils.UtilsModule;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {UtilsModule.class, ConfigurationModule.class})
@Singleton
public interface ApplicationComponent {

    JsonUtils providerJsonUtils();

    Configuration providerConfiguration();

    void inject(GetHandler getHandler);

    void inject(PropertiesLoaderSSM propertiesLoaderSSM);

}
