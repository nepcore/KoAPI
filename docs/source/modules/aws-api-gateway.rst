aws-api-gateway
===============

This modules allows you to host your application as a Lambda Function on AWS and access it through AWS API Gateway

You can add this module to your sbt project by adding ``libraryDependencies ++= "koapi" %% "aws-api-gateway" % ":project_version:"`` to your ``build.sbt`` file

The handler you need to set in your CloudFormation template (or directly on the AWS web interface) should be set to ``koapi.aws.apigateway.RequestHandler::handleRequest``
