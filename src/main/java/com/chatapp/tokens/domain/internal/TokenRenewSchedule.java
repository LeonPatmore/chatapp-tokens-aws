package com.chatapp.tokens.domain.internal;

import com.chatapp.tokens.domain.common.Provider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class TokenRenewSchedule {

    private Provider provider;
    private String externalId;
    private String scheduleId;

    public TokenRenewSchedule(Provider provider, String externalId, String scheduleId) {
        this.provider = provider;
        this.externalId = externalId;
        this.scheduleId = scheduleId;
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

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

}
