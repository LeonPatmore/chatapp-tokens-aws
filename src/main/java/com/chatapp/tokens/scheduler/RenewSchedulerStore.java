package com.chatapp.tokens.scheduler;

import com.chatapp.tokens.domain.common.Provider;

public interface RenewSchedulerStore {

    public void updateScheduleId(Provider provider, String externalId, String scheduleId);

    public String getScheduleId(Provider provider, String externalId) throws UnknownScheduleException;

}
