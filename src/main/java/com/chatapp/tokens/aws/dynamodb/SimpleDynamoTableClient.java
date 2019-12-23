package com.chatapp.tokens.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;

import java.util.Objects;

public class SimpleDynamoTableClient {

    private Table table;

    public SimpleDynamoTableClient(SimpleDynamoDBClient simpleDynamoDBClient, String tableName) {
        this.table = simpleDynamoDBClient.getTable(tableName);
    }

    public Item getItem(PrimaryKey primaryKey) throws UnknownItemException {
        Item item = table.getItem(primaryKey);
        if (Objects.isNull(item)) {
            throw new UnknownItemException();
        }
        return item;
    }

    public PutItemOutcome putItem(PutItemSpec putItemSpec) throws ConditionalCheckFailedException {
        return table.putItem(putItemSpec);
    }

    public UpdateItemOutcome updateItem(UpdateItemSpec updateItemSpec) throws ConditionalCheckFailedException {
        return table.updateItem(updateItemSpec);
    }

}
