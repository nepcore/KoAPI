package koapi

import koapi.Controller
import koapi.body.BodyReader
import koapi.models.Request
import koapi.models.http.Method
import koapi.routing.{RouterLogic, RouterLogicImpl}
import koapi.routing.dsl.{Route, RoutePart}
import scala.collection.mutable.ListBuffer
import java.net.URI
import koapi.logging.Logger

class ControllerTestException(msg: String) extends Exception(msg)

trait TestController extends Controller with Logger {
  private val routerLogic: RouterLogic = new RouterLogicImpl()
  private lazy val routes: ListBuffer[Route[Array[Byte]]] = ListBuffer()

  override private[koapi] def register[T](
      method: Method.Value,
      route: IndexedSeq[RoutePart],
      handler: Action[T]
  )(implicit reader: BodyReader[T]): Unit = {
    routes.append(Route(method, route, request => handler(readBody(request))))
  }

  def simulate(
      uri: String,
      method: Method.Value = Method.GET,
      headers: Map[String, String] = Map.empty,
      body: String = null,
      underlying: Any = null
  ) = {
    val request = Request(
      method,
      new URI(uri),
      headers,
      Map.empty,
      Map.empty,
      Option(body).map(_.getBytes()),
      underlying
    )

    routerLogic
      .findRoute(routes.toSeq, request)
      .map {
        case (route, request) =>
          route.handler(request)
      }
      .getOrElse {
        throw new ControllerTestException(
          s"Request did not match any routes of ${getClass.getSimpleName}"
        )
      }
  }
}
