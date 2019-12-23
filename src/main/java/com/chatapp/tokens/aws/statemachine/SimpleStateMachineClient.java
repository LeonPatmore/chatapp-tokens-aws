package com.chatapp.tokens.aws.statemachine;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.DescribeStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;

public class SimpleStateMachineClient {

    private AWSStepFunctions awsStepFunctions;
    private String stateMachineArn;

    public SimpleStateMachineClient(String stateMachineArn) {
        this.awsStepFunctions = AWSStepFunctionsClientBuilder.defaultClient();
        this.stateMachineArn = stateMachineArn;
    }

    public String startExecution(String name, String input) {
        StartExecutionRequest startExecutionRequest = new StartExecutionRequest()
                .withStateMachineArn(stateMachineArn)
                .withInput(input)
                .withName(name);
        return awsStepFunctions.startExecution(startExecutionRequest).getExecutionArn();
    }

}
