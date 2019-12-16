package com.chatapp.tokens;

import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.configuration.ConfigurationModule;
import com.chatapp.tokens.configuration.ConfigurationModule_ProviderConfigurationFactory;
import com.chatapp.tokens.configuration.PropertiesLoaderSSM;
import com.chatapp.tokens.configuration.PropertiesLoaderSSM_MembersInjector;
import com.chatapp.tokens.handlers.get.GetHandler;
import com.chatapp.tokens.handlers.get.GetHandler_MembersInjector;
import com.chatapp.tokens.utils.JsonUtils;
import com.chatapp.tokens.utils.UtilsModule;
import com.chatapp.tokens.utils.UtilsModule_ProvideJsonUtilsFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerApplicationComponent implements ApplicationComponent {
  private Provider<JsonUtils> provideJsonUtilsProvider;

  private Provider<Configuration> providerConfigurationProvider;

  private DaggerApplicationComponent(UtilsModule utilsModuleParam,
      ConfigurationModule configurationModuleParam) {

    initialize(utilsModuleParam, configurationModuleParam);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ApplicationComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final UtilsModule utilsModuleParam,
      final ConfigurationModule configurationModuleParam) {
    this.provideJsonUtilsProvider = DoubleCheck.provider(UtilsModule_ProvideJsonUtilsFactory.create(utilsModuleParam));
    this.providerConfigurationProvider = DoubleCheck.provider(ConfigurationModule_ProviderConfigurationFactory.create(configurationModuleParam));
  }

  @Override
  public JsonUtils providerJsonUtils() {
    return provideJsonUtilsProvider.get();}

  @Override
  public Configuration providerConfiguration() {
    return providerConfigurationProvider.get();}

  @Override
  public void inject(GetHandler getHandler) {
    injectGetHandler(getHandler);}

  @Override
  public void inject(PropertiesLoaderSSM propertiesLoaderSSM) {
    injectPropertiesLoaderSSM(propertiesLoaderSSM);}

  private GetHandler injectGetHandler(GetHandler instance) {
    GetHandler_MembersInjector.injectJsonUtils(instance, provideJsonUtilsProvider.get());
    GetHandler_MembersInjector.injectConfiguration(instance, providerConfigurationProvider.get());
    return instance;
  }

  private PropertiesLoaderSSM injectPropertiesLoaderSSM(PropertiesLoaderSSM instance) {
    PropertiesLoaderSSM_MembersInjector.injectJsonUtils(instance, provideJsonUtilsProvider.get());
    return instance;
  }

  public static final class Builder {
    private UtilsModule utilsModule;

    private ConfigurationModule configurationModule;

    private Builder() {
    }

    public Builder utilsModule(UtilsModule utilsModule) {
      this.utilsModule = Preconditions.checkNotNull(utilsModule);
      return this;
    }

    public Builder configurationModule(ConfigurationModule configurationModule) {
      this.configurationModule = Preconditions.checkNotNull(configurationModule);
      return this;
    }

    public ApplicationComponent build() {
      if (utilsModule == null) {
        this.utilsModule = new UtilsModule();
      }
      if (configurationModule == null) {
        this.configurationModule = new ConfigurationModule();
      }
      return new DaggerApplicationComponent(utilsModule, configurationModule);
    }
  }
}
