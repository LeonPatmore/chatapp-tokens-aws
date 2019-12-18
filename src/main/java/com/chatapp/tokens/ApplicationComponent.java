package com.chatapp.tokens;

import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.configuration.ConfigurationModule;
import com.chatapp.tokens.configuration.PropertiesLoaderSSM;
import com.chatapp.tokens.handlers.get.GetHandler;
import com.chatapp.tokens.handlers.create.CreateHandler;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.TokensStoreDynamoDB;
import com.chatapp.tokens.store.TokensStoreModule;
import com.chatapp.tokens.utils.JsonUtils;
import com.chatapp.tokens.utils.UtilsModule;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {UtilsModule.class, ConfigurationModule.class, TokensStoreModule.class})
@Singleton
public interface ApplicationComponent {

    JsonUtils providerJsonUtils();

    Configuration providerConfiguration();

    TokensStore providerTokensStore();

    void inject(GetHandler getHandler);

    void inject(CreateHandler postHandler);

    void inject(PropertiesLoaderSSM propertiesLoaderSSM);

    void inject(TokensStoreDynamoDB tokensStoreDynamoDB);

}
