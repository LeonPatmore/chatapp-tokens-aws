package com.chatapp.tokens.utils;

import com.chatapp.tokens.configuration.PropertiesLoaderSSM;
import com.chatapp.tokens.configuration.PropertiesLoaderSSM_MembersInjector;
import com.chatapp.tokens.handlers.get.GetHandler;
import com.chatapp.tokens.handlers.get.GetHandler_MembersInjector;
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
public final class DaggerUtilsComponent implements UtilsComponent {
  private Provider<JsonUtils> provideJsonUtilsProvider;

  private DaggerUtilsComponent(UtilsModule utilsModuleParam) {

    initialize(utilsModuleParam);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static UtilsComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final UtilsModule utilsModuleParam) {
    this.provideJsonUtilsProvider = DoubleCheck.provider(UtilsModule_ProvideJsonUtilsFactory.create(utilsModuleParam));
  }

  @Override
  public JsonUtils providerJsonUtils() {
    return provideJsonUtilsProvider.get();}

  @Override
  public void inject(GetHandler getHandler) {
    injectGetHandler(getHandler);}

  @Override
  public void inject(PropertiesLoaderSSM propertiesLoaderSSM) {
    injectPropertiesLoaderSSM(propertiesLoaderSSM);}

  private GetHandler injectGetHandler(GetHandler instance) {
    GetHandler_MembersInjector.injectJsonUtils(instance, provideJsonUtilsProvider.get());
    return instance;
  }

  private PropertiesLoaderSSM injectPropertiesLoaderSSM(PropertiesLoaderSSM instance) {
    PropertiesLoaderSSM_MembersInjector.injectJsonUtils(instance, provideJsonUtilsProvider.get());
    return instance;
  }

  public static final class Builder {
    private UtilsModule utilsModule;

    private Builder() {
    }

    public Builder utilsModule(UtilsModule utilsModule) {
      this.utilsModule = Preconditions.checkNotNull(utilsModule);
      return this;
    }

    public UtilsComponent build() {
      if (utilsModule == null) {
        this.utilsModule = new UtilsModule();
      }
      return new DaggerUtilsComponent(utilsModule);
    }
  }
}
