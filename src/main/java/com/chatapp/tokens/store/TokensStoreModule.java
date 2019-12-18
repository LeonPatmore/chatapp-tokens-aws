package com.chatapp.tokens.store;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class TokensStoreModule {

    @Provides
    @Singleton
    public TokensStore provideTokensStore() {
        return new TokensStoreDynamoDB();
    }

}
