package koapi.routing

import koapi.RouteBuilder
import koapi.logging.Logger

import com.typesafe.config.ConfigFactory

import scala.util.Try

trait RouteDiscoverer {
  def discoverRoutes(): Unit
}

class ReflectionRouteDicoverer extends RouteDiscoverer with Logger {
  import org.reflections.Reflections

  def discoverRoutes() = {
    logger.info("discovering routes")
    val reflections = new Reflections()
    val classes = reflections.getSubTypesOf(classOf[RouteBuilder])
    classes.forEach { cls =>
      Try {
        if (!cls.isInterface) {
          cls.newInstance()
          logger.debug(s"registered routes from ${cls.getCanonicalName}")
        } else {
          logger.debug(
            s"skipping ${cls.getCanonicalName} as it doesn't look like an implementation"
          )
        }
      }.getOrElse {
        logger.warn(
          s"could not register routes defined in ${cls.getCanonicalName}"
        )
      }
    }
  }
}

class ConfigRouteDiscoverer extends RouteDiscoverer with Logger {
  import scala.reflect.runtime.universe
  private lazy val runtimeMirror =
    universe.runtimeMirror(getClass.getClassLoader)
  private val config = ConfigFactory.load()
  def discoverRoutes() = {
    logger.info("loading controllers defined in config")
    config.getStringList("koapi.router.controllers").forEach { controller =>
      logger.info(s"loading $controller")
      Try {
        Class
          .forName(controller)
          .getConstructors()
          .find(_.getParameterCount() == 0)
          .map(_.newInstance())
          .getOrElse {
            val module = runtimeMirror.staticModule(controller)
            val companion = runtimeMirror.reflectModule(module).instance
            companion.getClass.getMethod("apply").invoke(companion)
          }
      }.recover {
        case error =>
          logger.error(s"failed loading controller $controller", error)
          throw error
      }
    }
  }
}
