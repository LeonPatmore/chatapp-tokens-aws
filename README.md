### Compilation
`./gradlew shadowJar`

Jar location: `build/jars/chatapp-tokens-all.jar`
 
### Local Testing
Start DynamoDB: `docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb`
#### Api Gateway Handlers
`sam local start-api -t sam-cli-template.yaml -n local-env-vars.json`
#### SNS Handlers
`sam local generate-event sns notification --message "$(cat src/test/resources/sns_renew_body.json.template)" --topic ChatappTokensRenew | sam local invoke RenewTokenFunctionSNS -t sam-cli-template.yaml -n local-env-vars.json`

### Packaging
`sam package --template-file sam-cli-template.yaml --output-template-file packaged.yaml --s3-bucket nexmo-tokens`

### Deploying
`aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file packaged.yaml --stack-name chatapp-tokens-dev`
