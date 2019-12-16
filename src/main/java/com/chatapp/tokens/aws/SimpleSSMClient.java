package com.chatapp.tokens.aws;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClient;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSSMClient {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private AWSSimpleSystemsManagement client;

    public SimpleSSMClient(String region) {
        log.info("Creating SSM client in region [ {} ]", region);
        client = AWSSimpleSystemsManagementClient.builder().withRegion(region).build();
    }

    public String getParameter(String key, boolean encrypted) {
        log.info("Retrieving parameter [ {} ], encrypted [ {} ]", key, encrypted);
        GetParameterRequest getParameterRequest = new GetParameterRequest().withName(key)
                                                                           .withWithDecryption(encrypted);
        GetParameterResult result = client.getParameter(getParameterRequest);
        String value = result.getParameter().getValue();
        log.info("Found parameter, [ {} : {} ]", key, value);
        return value;
    }

}