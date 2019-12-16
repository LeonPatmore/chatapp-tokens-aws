package com.chatapp.tokens.configuration;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ConfigurationModule {

    @Provides
    @Singleton
    public Configuration providerConfiguration() {
        return new Configuration();
    }

}
