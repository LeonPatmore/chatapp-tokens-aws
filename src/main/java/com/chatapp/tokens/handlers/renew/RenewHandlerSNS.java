package com.chatapp.tokens.handlers.renew;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.domain.external.requests.RenewRequestSNS;
import com.chatapp.tokens.handlers.SNSHandler;
import com.chatapp.tokens.store.CannotUpdateTokenException;
import com.chatapp.tokens.store.UnknownTokenException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


public class RenewHandlerSNS extends SNSHandler<RenewRequestSNS> {

    private final Logger log = LogManager.getLogger(getClass());

    @Inject
    public RenewHandler renewHandler;

    public RenewHandlerSNS() {
        super(RenewRequestSNS.class);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
    }

    @Override
    public void handleRequest(RenewRequestSNS request) {
        try {
            renewHandler.renewToken(request.getProvider(), request.getExternalId());
        } catch (CannotUpdateTokenException | UnknownTokenException e) {
            log.error(e);
        }
    }

}
