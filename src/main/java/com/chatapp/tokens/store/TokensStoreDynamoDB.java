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

import javax.inject.Inject;

import static com.chatapp.tokens.configuration.Configuration.AWS_REGION;
import static com.chatapp.tokens.configuration.Configuration.DB_ENDPOINT_OVERRIDE;
import static com.chatapp.tokens.configuration.Configuration.TABLE_NAME;

public class TokensStoreDynamoDB implements TokensStore {

    private static final String PRIMARY_KEY_NAME = "id";

    private SimpleDynamoTableClient simpleDynamoTableClient;

    @Inject
    public JsonUtils jsonUtils;

    public TokensStoreDynamoDB() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        this.simpleDynamoTableClient = new SimpleDynamoTableClient(
                new SimpleDynamoDBClient(AWS_REGION, DB_ENDPOINT_OVERRIDE), TABLE_NAME);
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
        simpleDynamoTableClient.putItem(new Item().withPrimaryKey(new PrimaryKey(PRIMARY_KEY_NAME, token.getId()))
                                                  .withString("token", token.getToken()));
    }

    private static String getId(Provider provider, String externalId) {
        return String.format("%s-%s", provider.name(), externalId).toLowerCase();
    }

}
