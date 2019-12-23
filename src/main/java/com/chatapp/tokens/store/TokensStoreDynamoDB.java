package com.chatapp.tokens.store;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Expected;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.aws.dynamodb.SimpleDynamoDBClient;
import com.chatapp.tokens.aws.dynamodb.SimpleDynamoTableClient;
import com.chatapp.tokens.aws.dynamodb.UnknownItemException;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import static com.chatapp.tokens.configuration.Configuration.*;

public class TokensStoreDynamoDB implements TokensStore {

    private final Logger log = LogManager.getLogger(getClass());

    private static final String PRIMARY_KEY_NAME = "id";

    private SimpleDynamoTableClient simpleDynamoTableClient;

    @Inject
    public JsonUtils jsonUtils;

    TokensStoreDynamoDB() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        this.simpleDynamoTableClient = new SimpleDynamoTableClient(
                new SimpleDynamoDBClient(AWS_REGION, getDBOverride()), TABLE_NAME);
    }

    @Nullable
    private static String getDBOverride() {
        if (Boolean.valueOf(OVERRIDE_DB_ENDPOINT)) {
            return DB_ENDPOINT_OVERRIDE;
        } else {
            return null;
        }
    }

    @Override
    public Token getToken(Provider provider, String externalId) throws CannotGetTokenException, UnknownTokenException {
        try {
            Item item = simpleDynamoTableClient.getItem(getPrimaryKey(provider, externalId));
            return jsonUtils.getObject(item.toJSON(), Token.class);
        } catch (DeserializableException e) {
            throw new CannotGetTokenException("Entry in DynamoDB is not de-serializable!", e);
        } catch (UnknownItemException e) {
            throw new UnknownTokenException("Token can not be found in DynamoDB!", e);
        }
    }

    @Override
    public void putToken(Token token) {
        PrimaryKey primaryKey = getPrimaryKey(token);
        log.info("Inserting token with primary key [ {} ]", primaryKey.toString());
        simpleDynamoTableClient.putItem(
                new Item().withPrimaryKey(primaryKey)
                          .withString("token", token.getToken())
                          .withString("provider", token.getProvider().name())
                          .withString("externalId", token.getExternalId()));
    }

    @Override
    public void updateToken(Token token) throws UnknownTokenException {
        PrimaryKey primaryKey = getPrimaryKey(token);
        log.info("Updating token with primary key [ {} ]", primaryKey.toString());
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#T", "token");
        expressionAttributeNames.put("#I", PRIMARY_KEY_NAME);
        Map<String, Object> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val1", token.getToken());
        expressionAttributeValues.put(":primaryKeyValue", getPrimaryKeyValue(token));
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(primaryKey)
                .withUpdateExpression("set #T = :val1")
                .withConditionExpression("#I = :primaryKeyValue")
                .withValueMap(expressionAttributeValues)
                .withNameMap(expressionAttributeNames);
        try {
            simpleDynamoTableClient.updateItem(updateItemSpec);
        } catch (UnknownItemException e) {
            throw new UnknownTokenException("Can not find Token in DB!", e);
        }
    }

    private static AttributeUpdate[] getAttributeUpdates(Token token) {
        AttributeUpdate tokenUpdate = new AttributeUpdate("token").put(token.getToken());
        return new AttributeUpdate[]{tokenUpdate};
    }

    private static String getPrimaryKeyValue(Token token) {
        return getId(token.getProvider(), token.getExternalId());
    }

    private static PrimaryKey getPrimaryKey(Token token) {
        return new PrimaryKey(PRIMARY_KEY_NAME, getPrimaryKeyValue(token));
    }

    private static PrimaryKey getPrimaryKey(Provider provider, String externalId) {
        return new PrimaryKey(PRIMARY_KEY_NAME, getId(provider, externalId));
    }

    private static String getId(Provider provider, String externalId) {
        return String.format("%s-%s", provider.name(), externalId).toLowerCase();
    }

}
