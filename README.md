### Compilation
`./gradlew shadowJar`

Jar location: `build/jars/chatapp-tokens-all.jar`
 
### Local Testing
In order to test locally, you must first start AWS DynamoDB and AWS Step Functions locally.

**Start AWS DynamoDB**: `docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb`
- You must run with shared db since the AWS credentials that you create the table with and the one that AWS SAM local uses 
will not be the same.

**Create Table**: 

**Start AWS Step Functions**: `docker run -p 8083:8083 --env-file aws-stepfunctions-cal-env-vars.txt amazon/aws-stepfunctions-local`
- The env file configures Lambda to use SAM local.

**Start SAM Local**: `sam local start-api -t sam-cli-template.yaml -n lambda-local-env-vars.json`

### Packaging
`sam package --template-file sam-cli-template.yaml --output-template-file packaged.yaml --s3-bucket nexmo-tokens`

### Deploying
`aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file packaged.yaml --stack-name chatapp-tokens-dev`
