package com.chatapp.tokens.handlers.renew;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.scheduler.RenewScheduler;
import com.chatapp.tokens.store.TokensStore;
import com.chatapp.tokens.store.UnknownTokenException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class RenewHandler {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    public TokensStore tokensStore;

    @Inject
    public RenewScheduler renewScheduler;

    public RenewHandler() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    public Token renewToken(Provider provider, String externalId) throws UnknownTokenException {
        log.info("Renewing token for provider [ {} ], external ID [ {} ]", provider.name(), externalId);
        Token renewedToken = new Token(provider, externalId, "renewed-token", 300);
        tokensStore.updateToken(renewedToken);
        renewScheduler.scheduleTokenRenewal(provider, externalId, 300);
        return renewedToken;
    }

}
