package koapi

import koapi.body.{BodyWriter, BodyReader}
import koapi.models.{Request, Response}
import koapi.models.http.{Method, Status}
import koapi.routing.HttpRouter
import koapi.routing.dsl.{RoutePart, Route}

/** provides shortcuts to instanciate responses using more readable syntax */
@SuppressWarnings(Array("MethodNames"))
trait Responses {

  /** Creates a response
    * Mostly useful for custom response status codes
    *
    * @tparam T The type of the value used as HTTP response body
    * @param status The HTTP status code
    * @param body The HTTP respone body content
    * @param headers The HTTP response headers
    * @param writer An instance of [[koapi.body.BodyWriter]] helping with serializing the value passed as body
    *               For String and Option these are automatically provided
    *               BodyWriters for other types may be provided by other libraries or by you
    * @return An instance of [[koapi.models.Response]]
    */
  def Response[T](
      status: Int,
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit writer: BodyWriter[T]): Response[T] =
    new Response(status, body, headers)

  // 2xx
  /** Shortcut to create an HTTP 200 response
    * see [[koapi.Responses.Response]] for details
    */
  def Ok[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.OK, body, headers)

  /** Shortcut to create an HTTP 201 response
    * see [[koapi.Responses.Response]] for details
    */
  def Created[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.CREATED, body, headers)

  /** Shortcut to create an HTTP 202 response
    * see [[koapi.Responses.Response]] for details
    */
  def Accepted[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.ACCEPTED, body, headers)

  /** Shortcut to create an HTTP 204 response
    * see [[koapi.Responses.Response]] for details
    */
  def NoContent[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.NO_CONTENT, body, headers)

  // 3xx
  /** Shortcut to create an HTTP 301 response
    * see [[koapi.Responses.Response]] for details
    */
  def MovedPermanently[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.MOVED_PERMANENTLY, body, headers)

  /** Shortcut to create an HTTP 302 response
    * see [[koapi.Responses.Response]] for details
    */
  def Found[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.FOUND, body, headers)

  /** Shortcut to create an HTTP 303 response
    * see [[koapi.Responses.Response]] for details
    */
  def SeeOther[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.SEE_OTHER, body, headers)

  /** Shortcut to create an HTTP 307 response
    * see [[koapi.Responses.Response]] for details
    */
  def TemporaryRedirect[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.TEMPORARY_REDIRECT, body, headers)

  /** Shortcut to create an HTTP 308 response
    * see [[koapi.Responses.Response]] for details
    */
  def PermanentRedirect[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.PERMANENT_REDIRECT, body, headers)

  // 4xx
  /** Shortcut to create an HTTP 400 response
    * see [[koapi.Responses.Response]] for details
    */
  def BadRequest[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.BAD_REQUEST, body, headers)

  /** Shortcut to create an HTTP 401 response
    * see [[koapi.Responses.Response]] for details
    */
  def Unauthorized[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.UNAUTHORIZED, body, headers)

  /** Shortcut to create an HTTP 403 response
    * see [[koapi.Responses.Response]] for details
    */
  def Forbidden[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.FORBIDDEN, body, headers)

  /** Shortcut to create an HTTP 404 response
    * see [[koapi.Responses.Response]] for details
    */
  def NotFound[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.NOT_FOUND, body, headers)

  /** Shortcut to create an HTTP 406 response
    * see [[koapi.Responses.Response]] for details
    */
  def NotAcceptable[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.NOT_ACCEPTABLE, body, headers)

  /** Shortcut to create an HTTP 409 response
    * see [[koapi.Responses.Response]] for details
    */
  def Conflict[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.CONFLICT, body, headers)

  /** Shortcut to create an HTTP 410 response
    * see [[koapi.Responses.Response]] for details
    */
  def Gone[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.GONE, body, headers)

  // 5xx
  /** Shortcut to create an HTTP 500 response
    * see [[koapi.Responses.Response]] for details
    */
  def InternalServerError[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit writer: BodyWriter[T]): Response[T] =
    Response(Status.INTERNAL_SERVER_ERROR, body, headers)

  /** Shortcut to create an HTTP 501 response
    * see [[koapi.Responses.Response]] for details
    */
  def NotImplemented[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.NOT_IMPLEMENTED, body, headers)

  /** Shortcut to create an HTTP 502 response
    * see [[koapi.Responses.Response]] for details
    */
  def BadGateway[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.BAD_GATEWAY, body, headers)

  /** Shortcut to create an HTTP 503 response
    * see [[koapi.Responses.Response]] for details
    */
  def ServiceUnavailable[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.SERVICE_UNAVAILABLE, body, headers)

  /** Shortcut to create an HTTP 504 response
    * see [[koapi.Responses.Response]] for details
    */
  def GatewayTimeout[T](
      body: T = Option.empty,
      headers: Map[String, String] = Map.empty[String, String]
  )(implicit
      writer: BodyWriter[T]
  ): Response[T] = Response(Status.GATEWAY_TIMEOUT, body, headers)
}

/** provides shortcuts to register routes to the [[koapi.routing.HttpRouter]] */
trait RouteBuilder {
  type Action[T] = Request[T] => Response[_]
  type NoBody = None.type

  /** Uses an implicit body reader to transform the request body to the target type
    *
    * @tparam T The target type for the request body transformation
    * @param reader The body reader to use for the transformation
    * @return A new [[koapi.models.Request]] with the transformed body
    */
  private[koapi] def readBody[T](
      request: Request[Array[Byte]]
  )(implicit reader: BodyReader[T]): Request[T] =
    Request(
      request.method,
      request.uri,
      request.headers,
      request.pathParams,
      request.queryParams,
      request.body.flatMap(reader(_)),
      request.underlying
    )

  /** Registers a route to the [[koapi.routing.HttpRouter]]
    *
    * @param method The HTTP method
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  private[koapi] def register[T](
      method: Method.Value,
      route: IndexedSeq[RoutePart],
      handler: Action[T]
  )(implicit reader: BodyReader[T]): Unit = {
    HttpRouter.register(
      Route(method, route, request => handler(readBody(request)))
    )
  }

  /** Registers a GET route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def get[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.GET, route, handler)
    handler
  }

  /** Registers a POST route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def post[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.POST, route, handler)
    handler
  }

  /** Registers a PUT route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def put[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.PUT, route, handler)
    handler
  }

  /** Registers a DELETE route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def delete[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.DELETE, route, handler)
    handler
  }

  /** Registers a HEAD route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def head[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.HEAD, route, handler)
    handler
  }

  /** Registers a TRACE route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def trace[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.TRACE, route, handler)
    handler
  }

  /** Registers an OPTIONS route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def options[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.OPTIONS, route, handler)
    handler
  }

  /** Registers a PATCH route to the [[koapi.routing.HttpRouter]]
    *
    * @param route The route
    * @param handler The function used to handle a request to this route
    * @param reader The [[koapi.body.BodyWriter]] used to transform the body to the functions input type
    * @return The handler registered (mostly for unit testing purposes)
    */
  def patch[T](
      route: IndexedSeq[RoutePart]
  )(handler: Action[T])(implicit reader: BodyReader[T]): Action[T] = {
    register[T](Method.PATCH, route, handler)
    handler
  }
}

/** provides helpers for defining handlers */
trait Controller extends RouteBuilder with Responses
