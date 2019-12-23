package com.chatapp.tokens.scheduler;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.aws.dynamodb.SimpleDynamoDBClient;
import com.chatapp.tokens.aws.dynamodb.SimpleDynamoTableClient;
import com.chatapp.tokens.aws.dynamodb.UnknownItemException;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.TokenRenewSchedule;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;

import javax.inject.Inject;

import static com.chatapp.tokens.configuration.Configuration.AWS_REGION;
import static com.chatapp.tokens.configuration.Configuration.TABLE_NAME_SCHEDULER;

public class RenewSchedulerStoreDynamoDB implements RenewSchedulerStore {

    private final SimpleDynamoTableClient simpleDynamoTableClient;

    private static final String PRIMARY_KEY_NAME = "id";

    @Inject
    public JsonUtils jsonUtils;

    public RenewSchedulerStoreDynamoDB() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        this.simpleDynamoTableClient = new SimpleDynamoTableClient(new SimpleDynamoDBClient(AWS_REGION),
                                                                   TABLE_NAME_SCHEDULER);
    }

    @Override
    public void updateScheduleId(Provider provider, String externalId, String scheduleId) {
        Item newItem = new Item().withPrimaryKey(getPrimaryKey(provider, externalId))
                                 .withString("provider", provider.name())
                                 .withString("externalId", externalId)
                                 .withString("scheduleId", scheduleId);
        PutItemSpec putItemSpec = new PutItemSpec().withItem(newItem);
        simpleDynamoTableClient.putItem(putItemSpec);
    }

    @Override
    public String getScheduleId(Provider provider, String externalId) throws UnknownScheduleException {
        try {
            Item item = simpleDynamoTableClient.getItem(getPrimaryKey(provider, externalId));
            TokenRenewSchedule tokenRenewSchedule = jsonUtils.getObject(item.toJSON(),
                                                                        TokenRenewSchedule.class);
            return tokenRenewSchedule.getScheduleId();
        } catch (UnknownItemException e) {
            throw new UnknownScheduleException("Could not find schedule in DB!", e);
        } catch (DeserializableException e) {
            throw new RuntimeException(e);
        }
    }

    private static PrimaryKey getPrimaryKey(Provider provider, String externalId) {
        return new PrimaryKey(PRIMARY_KEY_NAME, getPrimaryKeyValue(provider, externalId));
    }

    private static String getPrimaryKeyValue(Provider provider, String externalId) {
        return String.format("%s-%s", provider.name(), externalId);
    }

}
