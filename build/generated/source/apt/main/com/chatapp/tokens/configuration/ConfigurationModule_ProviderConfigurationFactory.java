package com.chatapp.tokens.configuration;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ConfigurationModule_ProviderConfigurationFactory implements Factory<Configuration> {
  private final ConfigurationModule module;

  public ConfigurationModule_ProviderConfigurationFactory(ConfigurationModule module) {
    this.module = module;
  }

  @Override
  public Configuration get() {
    return providerConfiguration(module);
  }

  public static ConfigurationModule_ProviderConfigurationFactory create(
      ConfigurationModule module) {
    return new ConfigurationModule_ProviderConfigurationFactory(module);
  }

  public static Configuration providerConfiguration(ConfigurationModule instance) {
    return Preconditions.checkNotNull(instance.providerConfiguration(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
