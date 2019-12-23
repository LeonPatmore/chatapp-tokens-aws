package com.chatapp.tokens.handlers.renew;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.store.CannotUpdateTokenException;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.UnknownTokenException;

import javax.inject.Inject;

public class RenewHandler {

    @Inject
    public TokensStore tokensStore;

    public RenewHandler() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    public Token renewToken(Provider provider, String externalId) throws CannotUpdateTokenException,
                                                                         UnknownTokenException {
        Token renewedToken = new Token(provider, externalId, "renewed-token");
        tokensStore.updateToken(renewedToken);
        return renewedToken;
    }

}
