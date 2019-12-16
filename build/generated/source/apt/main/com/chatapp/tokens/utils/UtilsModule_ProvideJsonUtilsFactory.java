package com.chatapp.tokens.utils;

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
public final class UtilsModule_ProvideJsonUtilsFactory implements Factory<JsonUtils> {
  private final UtilsModule module;

  public UtilsModule_ProvideJsonUtilsFactory(UtilsModule module) {
    this.module = module;
  }

  @Override
  public JsonUtils get() {
    return provideJsonUtils(module);
  }

  public static UtilsModule_ProvideJsonUtilsFactory create(UtilsModule module) {
    return new UtilsModule_ProvideJsonUtilsFactory(module);
  }

  public static JsonUtils provideJsonUtils(UtilsModule instance) {
    return Preconditions.checkNotNull(instance.provideJsonUtils(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
