package com.chatapp.tokens.handlers.renew;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RenewHandlerModule {

    @Provides
    @Singleton
    public RenewHandler provideRenewHandler() {
        return new RenewHandler();
    }

}
