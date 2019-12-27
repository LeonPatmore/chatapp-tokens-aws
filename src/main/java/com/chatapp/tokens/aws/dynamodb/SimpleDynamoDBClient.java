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

import static com.chatapp.tokens.configuration.Configuration.DB_ENDPOINT_OVERRIDE;
import static com.chatapp.tokens.configuration.Configuration.OVERRIDE_DB_ENDPOINT;

public class SimpleDynamoDBClient {

    private final Logger log = LogManager.getLogger(getClass());

    private DynamoDB dynamoDB;

    public SimpleDynamoDBClient(String region) {
        this.dynamoDB = new DynamoDB(getAmazonDynamoDB(region, getDBOverride()));
    }

    Table getTable(String tableName) {
        log.info("Getting table with name [ {} ]", tableName);
        return dynamoDB.getTable(tableName);
    }

    private AmazonDynamoDB getAmazonDynamoDB(String region, @Nullable String dbHost) {
        if (!Objects.isNull(dbHost) ) {
            log.info("Creating DynamoDB client in region [ {} ], host [ {} ]", region, dbHost);
            AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                    new AwsClientBuilder.EndpointConfiguration(dbHost, region);
            return AmazonDynamoDBClientBuilder.standard()
                                              .withEndpointConfiguration(endpointConfiguration)
                                              .build();
        } else {
            log.info("Creating DynamoDB client in region [ {} ], standard client!", region);
            return AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
        }
    }

    @Nullable
    private static String getDBOverride() {
        if (Boolean.valueOf(OVERRIDE_DB_ENDPOINT)) {
            return DB_ENDPOINT_OVERRIDE;
        } else {
            return null;
        }
    }

}
