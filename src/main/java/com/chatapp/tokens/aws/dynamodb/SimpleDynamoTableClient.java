package com.chatapp.tokens.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.Objects;

public class SimpleDynamoTableClient {

    private Table table;

    public SimpleDynamoTableClient(SimpleDynamoDBClient simpleDynamoDBClient, String tableName) {
        this.table = simpleDynamoDBClient.getTable(tableName);
    }

    public Item getItem(String primaryKeyName, String primaryKeyValue) throws UnknownItemException {
        Item item = table.getItem(primaryKeyName, primaryKeyValue);
        if (Objects.isNull(item)) {
            throw new UnknownItemException();
        }
        return item;
    }

    public PutItemOutcome putItem(Item item) {
        return table.putItem(item);
    }

}
