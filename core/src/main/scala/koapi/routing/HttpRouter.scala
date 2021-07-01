package koapi.routing

import koapi.logging.Logger
import koapi.models.{Request, Response}
import koapi.routing.dsl._

import scala.collection.mutable.ListBuffer
import scala.util.Try

import com.typesafe.config.ConfigFactory

trait RouterLogic {
  def findRoute(
      routes: Seq[Route[Array[Byte]]],
      request: Request[Array[Byte]]
  ): Option[(Route[Array[Byte]], Request[Array[Byte]])]
}

class RouterLogicImpl extends RouterLogic {
  def findRoute(
      routes: Seq[Route[Array[Byte]]],
      request: Request[Array[Byte]]
  ) = {
    var parametrizedRequest =
      request.copy(headers = request.headers.map(x => (x._1.toLowerCase, x._2)))
    val pathParts =
      request.uri.getPath.substring(1).split("/").filter(_.length() != 0)
    routes
      .filter { route => route.parts.length == pathParts.length }
      .find { route =>
        var matches = true
        if (route.method != request.method) {
          matches = false
        } else {
          for (i <- route.parts.indices) {
            route.parts(i) match {
              case SimpleRoutePart(part) if part == pathParts(i) =>
              case ParametrizedRoutePart(part) if part.isValid(pathParts(i)) =>
                parametrizedRequest = parametrizedRequest.copy(pathParams =
                  parametrizedRequest.pathParams + (part.name -> Param(
                    pathParts(i)
                  ))
                )
              case _ =>
                matches = false
            }
          }
        }
        matches
      }
      .map((_, parametrizedRequest))
  }
}

object HttpRouter extends Logger {
  private val routerLogic: RouterLogic = new RouterLogicImpl()
  private val routes: ListBuffer[Route[Array[Byte]]] = ListBuffer()

  /** Registers a new route handler
    *
    * @param route The route (including request handler) to be registered
    */
  def register(route: Route[Array[Byte]]): Unit = {
    logger.debug(
      s"Registering route ${route.method} ${route.parts.mkString("/")}"
    )
    routes += route
  }

  /** Handles a request using a registered route or the error handler
    *
    * @param request The request to be handled
    * @return The response to be sent out to the client
    */
  def handle(request: Request[Array[Byte]]): Response[_] = {
    logger.debug(s"Routing request for ${request.uri}")

    routerLogic
      .findRoute(routes.toSeq, request)
      .map {
        case (route, parametrizedRequest) =>
          Try {
            logger.debug(
              s"Matching route found, calling handler with request $parametrizedRequest"
            )
            route.handler(parametrizedRequest)
          }.fold(
            {
              case e: BodyParsingException => ErrorHandler(e)
              case e                       => ErrorHandler(HandlerException(e))
            },
            ok => ok
          )
      }
      .getOrElse(ErrorHandler(NotFoundError))
  }

  /** finds the routes to be handled (controllers don't instatiate themselves, so we do so here)
    * this method is called when the router is instanciated on first access
    * TODO: make this configurable to use custom implementations
    */
  private def discoverRoutes() = {
    val config = ConfigFactory.load()
    if (config.getBoolean("koapi.router.autoDiscover")) {
      logger.debug("Discovering routes using reflection")
      new ReflectionRouteDicoverer().discoverRoutes()
    } else {
      logger.debug("Discovering routes using config")
      new ConfigRouteDiscoverer().discoverRoutes()
    }
  }

  discoverRoutes()
}
