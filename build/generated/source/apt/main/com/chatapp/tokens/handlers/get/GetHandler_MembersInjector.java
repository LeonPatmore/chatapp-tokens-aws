package com.chatapp.tokens.handlers.get;

import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.utils.JsonUtils;
import dagger.MembersInjector;
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
public final class GetHandler_MembersInjector implements MembersInjector<GetHandler> {
  private final Provider<JsonUtils> jsonUtilsProvider;

  private final Provider<Configuration> configurationProvider;

  public GetHandler_MembersInjector(Provider<JsonUtils> jsonUtilsProvider,
      Provider<Configuration> configurationProvider) {
    this.jsonUtilsProvider = jsonUtilsProvider;
    this.configurationProvider = configurationProvider;
  }

  public static MembersInjector<GetHandler> create(Provider<JsonUtils> jsonUtilsProvider,
      Provider<Configuration> configurationProvider) {
    return new GetHandler_MembersInjector(jsonUtilsProvider, configurationProvider);}

  @Override
  public void injectMembers(GetHandler instance) {
    injectJsonUtils(instance, jsonUtilsProvider.get());
    injectConfiguration(instance, configurationProvider.get());
  }

  public static void injectJsonUtils(GetHandler instance, JsonUtils jsonUtils) {
    instance.jsonUtils = jsonUtils;
  }

  public static void injectConfiguration(GetHandler instance, Configuration configuration) {
    instance.configuration = configuration;
  }
}
