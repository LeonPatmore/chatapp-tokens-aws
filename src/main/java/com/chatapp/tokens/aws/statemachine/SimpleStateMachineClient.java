package com.chatapp.tokens.aws.statemachine;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.DescribeExecutionRequest;
import com.amazonaws.services.stepfunctions.model.DescribeExecutionResult;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.chatapp.tokens.configuration.Configuration.*;

public class SimpleStateMachineClient {

    private final Logger log = LogManager.getLogger(getClass());

    private AWSStepFunctions awsStepFunctions;
    private String stateMachineArn;

    public SimpleStateMachineClient(String region, String stateMachineArn) {
        this.awsStepFunctions = getAwsStepFunctions(region, getStateMachineOverride());
        this.stateMachineArn = stateMachineArn;
    }

    public String startExecution(String name, String input) {
        StartExecutionRequest startExecutionRequest = new StartExecutionRequest()
                .withStateMachineArn(stateMachineArn)
                .withInput(input)
                .withName(name);
        return awsStepFunctions.startExecution(startExecutionRequest).getExecutionArn();
    }

    public DescribeExecutionResult getExecution(String name) {
        DescribeExecutionRequest describeExecutionRequest = new DescribeExecutionRequest();
        describeExecutionRequest.setExecutionArn(getExecutionArn(name));
        return awsStepFunctions.describeExecution(describeExecutionRequest);
    }

    private String getExecutionArn(String name) {
        return String.format("%s:%s", this.stateMachineArn, name);
    }

    private AWSStepFunctions getAwsStepFunctions(String region, @Nullable String stepFunctionsHost) {
        if (!Objects.isNull(stepFunctionsHost) ) {
            log.info("Setting up AWS Step Function client with region [ {} ], host [ {} ]",
                     region,
                     stepFunctionsHost);
            AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                    new AwsClientBuilder.EndpointConfiguration(stepFunctionsHost, region);
            return AWSStepFunctionsClientBuilder.standard()
                                                .withEndpointConfiguration(endpointConfiguration)
                                                .build();
        } else {
            log.info("Setting up AWS Step Function client with region [ {} ], standard client!", region);
            return AWSStepFunctionsClientBuilder.standard().withRegion(region).build();
        }
    }

    @Nullable
    private static String getStateMachineOverride() {
        if (Boolean.valueOf(OVERRIDE_STATE_MACHINE_ENDPOINT)) {
            return STATE_MACHINE_ENDPOINT_OVERRIDE;
        } else {
            return null;
        }
    }

}
