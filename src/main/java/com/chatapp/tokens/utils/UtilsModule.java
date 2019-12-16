package com.chatapp.tokens.utils;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    public JsonUtils provideJsonUtils() {
        return new JsonUtils();
    }

}
