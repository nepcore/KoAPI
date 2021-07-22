package koapi.aws.apigateway

import com.amazonaws.services.lambda.runtime.{
  Context,
  RequestHandler => AWSRequestHandler
}
import com.amazonaws.services.lambda.runtime.events.{
  APIGatewayProxyRequestEvent,
  APIGatewayProxyResponseEvent
}
import koapi.models.http.Method
import koapi.models.Request
import koapi.routing.HttpRouter
import java.net.URI
import java.util.Base64
import scala.collection.JavaConverters._
import scala.util.Try
import koapi.logging.Logger
import koapi.routing.dsl.Param

/** The request handler for AWS Lambda */
class RequestHandler
    extends AWSRequestHandler[
      APIGatewayProxyRequestEvent,
      APIGatewayProxyResponseEvent
    ]
    with Logger {

  /** The Entrypoint for AWS Lambda
    * Returns an API Gateway representation of the result from handling the request
    *
    * @param event The AWS request
    * @param context The AWS request context
    */
  override def handleRequest(
      event: APIGatewayProxyRequestEvent,
      context: Context
  ): APIGatewayProxyResponseEvent = {
    val body = if (event.getIsBase64Encoded()) {
      Try(Base64.getDecoder().decode(event.getBody())).toOption
    } else {
      Option(event.getBody()).map(_.getBytes())
    }

    val query =
      Option(event.getMultiValueQueryStringParameters())
        .map { params =>
          params.asScala.mapValues { value =>
            Option(value).map(_.asScala.toSeq).getOrElse(Seq())
          }.toMap
        }
        .getOrElse(Map())

    val request = Request(
      Method.withName(event.getHttpMethod()),
      new URI(event.getPath()),
      event.getHeaders().asScala.toMap,
      Map.empty[String, Param],
      query,
      body,
      (event, context)
    )

    logger.debug(
      s"Transformed AWS request model $event into KoAPI request model $request"
    )

    val response = HttpRouter.handle(request)

    logger.debug(s"Responding to request with response $response")

    new APIGatewayProxyResponseEvent()
      .withIsBase64Encoded(true)
      .withBody(new String(Base64.getEncoder().encode(response.written)))
      .withStatusCode(response.status)
      .withHeaders(response.headers.asJava)
  }
}
