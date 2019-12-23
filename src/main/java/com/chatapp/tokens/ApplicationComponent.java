package com.chatapp.tokens;

import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.configuration.ConfigurationModule;
import com.chatapp.tokens.configuration.PropertiesLoaderSSM;
import com.chatapp.tokens.handlers.create.CreateHandler;
import com.chatapp.tokens.handlers.get.GetHandler;
import com.chatapp.tokens.handlers.renew.RenewHandler;
import com.chatapp.tokens.handlers.renew.RenewHandlerAPI;
import com.chatapp.tokens.handlers.renew.RenewHandlerModule;
import com.chatapp.tokens.handlers.renew.RenewHandlerStateMachine;
import com.chatapp.tokens.scheduler.*;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.TokensStoreDynamoDB;
import com.chatapp.tokens.store.TokensStoreModule;
import com.chatapp.tokens.utils.JsonUtils;
import com.chatapp.tokens.utils.UtilsModule;
import com.chatapp.tokens.utils.ValidationUtils;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {UtilsModule.class,
                      ConfigurationModule.class,
                      TokensStoreModule.class,
                      RenewHandlerModule.class,
                      RenewSchedulerModule.class})
@Singleton
public interface ApplicationComponent {

    JsonUtils providerJsonUtils();

    ValidationUtils providerValidationUtils();

    Configuration providerConfiguration();

    TokensStore providerTokensStore();

    RenewHandler providerRenewHandler();

    RenewScheduler providerRenewScheduler();

    RenewSchedulerStore providerRenewSchedulerStore();

    void inject(GetHandler getHandler);

    void inject(CreateHandler postHandler);

    void inject(RenewHandler renewHandler);

    void inject(RenewHandlerAPI renewHandlerApi);

    void inject(RenewHandlerStateMachine renewHandlerStateMachine);

    void inject(PropertiesLoaderSSM propertiesLoaderSSM);

    void inject(TokensStoreDynamoDB tokensStoreDynamoDB);

    void inject(RenewSchedulerStateMachine renewSchedulerStateMachine);

    void inject(RenewSchedulerStoreDynamoDB renewSchedulerStoreDynamoDB);

}
