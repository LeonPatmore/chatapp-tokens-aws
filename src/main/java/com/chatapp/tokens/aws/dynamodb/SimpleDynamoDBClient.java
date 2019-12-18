package com.chatapp.tokens.aws.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SimpleDynamoDBClient {

    private final Logger log = LogManager.getLogger(getClass());

    private DynamoDB dynamoDB;

    public SimpleDynamoDBClient(String region, @Nullable String dbHost) {
        log.info("Creating DynamoDB client in region [ {} ]", region);
        this.dynamoDB = new DynamoDB(getAmazonDynamoDB(region, dbHost));
    }

    Table getTable(String tableName) {
        log.info("Getting table with name [ {} ]", tableName);
        return dynamoDB.getTable(tableName);
    }

    private AmazonDynamoDB getAmazonDynamoDB(String region, @Nullable String dbHost) {
        if (!Objects.isNull(dbHost)) {
            AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                    new AwsClientBuilder.EndpointConfiguration(dbHost, region);
            return AmazonDynamoDBClientBuilder.standard()
                                              .withEndpointConfiguration(endpointConfiguration)
                                              .build();
        } else {
            return AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
        }
    }

}
