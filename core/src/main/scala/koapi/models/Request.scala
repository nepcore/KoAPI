package koapi.models

import java.net.URI

import koapi.models.http.Method

/** A representation of an HTTP request
  *
  * @tparam T The type of the request body content
  * @param method The HTTP request method
  * @param uri The uri of the request
  * @param headers The HTTP headers sent with the request
  * @param pathParams The path parameters (usually parsed and filled in by the [[koapi.routing.HttpRouter]])
  * @param body The request body
  * @param underlying The underlying request type from the entry point
  *                      Useful to handle differences in entry points' implementations of certain request parts
  *                      like e.g. AWS API Gateway and its Custom Lambda Authorizers
  */
final case class Request[T](
    method: Method.Value,
    uri: URI,
    headers: Map[String, String],
    pathParams: Map[String, Any],
    queryParams: Map[String, Seq[String]],
    body: Option[T],
    underlying: Any
)
