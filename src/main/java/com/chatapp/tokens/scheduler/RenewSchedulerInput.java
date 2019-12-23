package com.chatapp.tokens.scheduler;

import com.chatapp.tokens.domain.common.Provider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class RenewSchedulerInput {

    private Provider provider;

    private String externalId;

    private int waitSeconds;

    public RenewSchedulerInput(Provider provider, String externalId, int waitSeconds) {
        this.provider = provider;
        this.externalId = externalId;
        this.waitSeconds = waitSeconds;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

}
