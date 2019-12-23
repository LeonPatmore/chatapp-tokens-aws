package com.chatapp.tokens.scheduler;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RenewSchedulerModule {

    @Provides
    @Singleton
    public RenewScheduler provideRenewScheduler() {
        return new RenewSchedulerStateMachine();
    }

    @Provides
    @Singleton
    public RenewSchedulerStore provideRenewSchedulerStore() {
        return new RenewSchedulerStoreDynamoDB();
    }

}
