package com.chatapp.tokens.scheduler;

import com.chatapp.tokens.domain.common.Provider;

public interface RenewScheduler {

    public void scheduleTokenRenewal(Provider provider, String externalId, int seconds);

}
