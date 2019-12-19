package com.chatapp.tokens.store;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.chatapp.tokens.ApplicationComponent;
import com.chatapp.tokens.DaggerApplicationComponent;
import com.chatapp.tokens.aws.dynamodb.SimpleDynamoDBClient;
import com.chatapp.tokens.aws.dynamodb.SimpleDynamoTableClient;
import com.chatapp.tokens.aws.dynamodb.UnknownItemException;
import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.utils.DeserializableException;
import com.chatapp.tokens.utils.JsonUtils;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import static com.chatapp.tokens.configuration.Configuration.*;

public class TokensStoreDynamoDB implements TokensStore {

    private static final String PRIMARY_KEY_NAME = "id";

    private SimpleDynamoTableClient simpleDynamoTableClient;

    @Inject
    public JsonUtils jsonUtils;

    public TokensStoreDynamoDB() {
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
            Item item = simpleDynamoTableClient.getItem(PRIMARY_KEY_NAME, getId(provider, externalId));
            return jsonUtils.getObject(item.toJSON(), Token.class);
        } catch (DeserializableException e) {
            throw new CannotGetTokenException("Entry in DynamoDB is not de-serializable!", e);
        } catch (UnknownItemException e) {
            throw new UnknownTokenException("Token can not be found in DynamoDB!", e);
        }
    }

    @Override
    public void putToken(Token token) {
        simpleDynamoTableClient.putItem(new Item().withPrimaryKey(new PrimaryKey(PRIMARY_KEY_NAME,
                                                                                 getId(token.getProvider(),
                                                                                       token.getExternalId())))
                                                  .withString("token", token.getToken())
                                                  .withString("provider", token.getProvider().name())
                                                  .withString("externalId", token.getExternalId()));
    }

    private static String getId(Provider provider, String externalId) {
        return String.format("%s-%s", provider.name(), externalId).toLowerCase();
    }

}
