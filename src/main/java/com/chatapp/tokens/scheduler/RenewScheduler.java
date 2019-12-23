package com.chatapp.tokens.scheduler;

import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;

public interface RenewScheduler {

    public void scheduleTokenRenewal(Provider provider, String externalId, int seconds);

    public String getScheduleId(Token token) throws UnknownScheduleException;

}
