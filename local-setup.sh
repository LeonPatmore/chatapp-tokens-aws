#!/usr/bin/env bash

# Create renew state machine.
RENEW_STATE_MACHINE=`cat renew-state-machine.json`
RENEW_STATE_MACHINE=${RENEW_STATE_MACHINE//\$\{RenewFunctionArn\}/arn:aws:lambda:us-east-1:123456789012:function:RenewTokenFunctionStateMachine}
aws stepfunctions --endpoint-url http://localhost:8083 create-state-machine --name ChatappTokensRenew --definition "${RENEW_STATE_MACHINE}" --role-arn "arn:aws:iam::012345678901:role/DummyRole"

# Create required DynamoDB tables.
aws dynamodb create-table --endpoint http://localhost:8000 --attribute-definitions AttributeName=id,AttributeType=S --table-name ChatappTokensScheduler --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
aws dynamodb create-table --endpoint http://localhost:8000 --attribute-definitions AttributeName=id,AttributeType=S --table-name ChatappTokens --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5