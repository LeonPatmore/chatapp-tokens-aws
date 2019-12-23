package com.chatapp.tokens.scheduler;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.aws.statemachine.SimpleStateMachineClient;
import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;

import javax.inject.Inject;

public class RenewSchedulerStateMachine implements RenewScheduler {

    private SimpleStateMachineClient simpleStateMachineClient;

    @Inject
    public JsonUtils jsonUtils;

    public RenewSchedulerStateMachine() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        this.simpleStateMachineClient = new SimpleStateMachineClient(Configuration.RENEW_STATE_MACHINE);
    }

    @Override
    public void scheduleTokenRenewal(Provider provider, String externalId, int seconds) {
        simpleStateMachineClient.startExecution(getExecutionName(provider, externalId),
                                                getExecutionInput(provider, externalId, seconds));
    }

    private static String getExecutionName(Provider provider, String externalId) {
        return String.format("%s-%s", provider.name(), externalId);
    }

    private String getExecutionInput(Provider provider, String externalId, int seconds) {
        RenewSchedulerInput renewSchedulerInput = new RenewSchedulerInput(provider, externalId, seconds);
        try {
            return jsonUtils.toJson(renewSchedulerInput);
        } catch (DeserializableException e) {
            throw new RuntimeException(e);
        }
    }

}
