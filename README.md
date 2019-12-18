### Compilation
1) `./gradlew shadowJar`

Jar location: `build/jars/chatapp-tokens-all.jar`
 
### Local Testing
1) Start DynamoDB: `docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb`
1) Start SAM Local: `sam local start-api -t sam-cli-template.yaml -n local-env-vars.json`

### Packaging
1) `sam package --template-file sam-cli-template.yaml --output-template-file packaged.yaml --s3-bucket nexmo-tokens`

### Deploying
1) `aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file packaged.yaml --stack-name chatapp-tokens-dev`
