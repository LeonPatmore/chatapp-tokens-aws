package com.chatapp.tokens.scheduler;

import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.aws.statemachine.SimpleStateMachineClient;
import com.chatapp.tokens.configuration.Configuration;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class RenewSchedulerStateMachine implements RenewScheduler {

    private final Logger log = LogManager.getLogger(getClass());

    private SimpleStateMachineClient simpleStateMachineClient;

    @Inject
    public JsonUtils jsonUtils;

    @Inject
    public RenewSchedulerStore renewSchedulerStore;

    public RenewSchedulerStateMachine() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        this.simpleStateMachineClient = new SimpleStateMachineClient(Configuration.RENEW_STATE_MACHINE);
    }

    @Override
    public void scheduleTokenRenewal(Provider provider, String externalId, int seconds) {
        String id = generateId();
        log.info("Scheduling token renewal with ID [ {} ]" ,id);
        simpleStateMachineClient.startExecution(getExecutionName(provider, externalId, id),
                                                getExecutionInput(provider, externalId, seconds));
        renewSchedulerStore.updateScheduleId(provider, externalId, id);
    }

    @Override
    public String getScheduleId(Token token) throws UnknownScheduleException {
        return renewSchedulerStore.getScheduleId(token.getProvider(), token.getExternalId());
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    private static String getExecutionName(Provider provider, String externalId, String id) {
        return String.format("%s-%s-%s", provider.name(), externalId, id);
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
