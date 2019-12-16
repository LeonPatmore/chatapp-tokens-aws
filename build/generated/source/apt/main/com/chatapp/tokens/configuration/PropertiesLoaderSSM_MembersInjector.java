package com.chatapp.tokens.configuration;

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
public final class PropertiesLoaderSSM_MembersInjector implements MembersInjector<PropertiesLoaderSSM> {
  private final Provider<JsonUtils> jsonUtilsProvider;

  public PropertiesLoaderSSM_MembersInjector(Provider<JsonUtils> jsonUtilsProvider) {
    this.jsonUtilsProvider = jsonUtilsProvider;
  }

  public static MembersInjector<PropertiesLoaderSSM> create(Provider<JsonUtils> jsonUtilsProvider) {
    return new PropertiesLoaderSSM_MembersInjector(jsonUtilsProvider);}

  @Override
  public void injectMembers(PropertiesLoaderSSM instance) {
    injectJsonUtils(instance, jsonUtilsProvider.get());
  }

  public static void injectJsonUtils(PropertiesLoaderSSM instance, JsonUtils jsonUtils) {
    instance.jsonUtils = jsonUtils;
  }
}
