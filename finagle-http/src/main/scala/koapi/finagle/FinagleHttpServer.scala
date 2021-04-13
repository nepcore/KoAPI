package koapi.finagle

import java.io.{PrintWriter, StringWriter}
import java.net.URI

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{
  Status,
  Request => FinagleRequest,
  Response => FinagleResponse
}
import com.twitter.util.{Await, Future}
import com.typesafe.config.Config
import koapi.logging.Logger
import koapi.models.http.Method
import koapi.models.{Request, Response}
import koapi.routing.HttpRouter
import sun.misc.Signal

import scala.util.Try
import koapi.utils.URLHelper

/** Wrapper for finagle to handle incoming requests
  *
  * @param config The configuration for the HTTP handler
  */
class FinagleHttpServer(config: Config) extends Logger {
  Signal.handle(new Signal("INT"), (_: Signal) => stop)

  def stop = {
    logger.info("Stopping server")
    server.close()
  }

  private val ip = config.getString("koapi.http.bindIp")
  private val port = config.getInt("koapi.http.port")
  private val bindAddress = s"$ip:$port"
  private val writeExceptionToResponse =
    config.getBoolean("koapi.http.writeExceptionToResponse")

  private val service: Service[FinagleRequest, FinagleResponse] =
    (request: FinagleRequest) => {
      logger.debug(s"Incoming finagle request: $request, reading body")
      val body = Await.result(
        request.reader
          .map { buf =>
            var bytes = Array.emptyByteArray
            for (i <- 0 until buf.length) {
              bytes = bytes :+ buf.get(i)
            }
            bytes
          }
          .read()
      )

      val req = Request(
        Method.withName(request.method.name),
        new URI(request.uri),
        request.headerMap.toMap,
        Map.empty[String, String],
        URLHelper.parseQueryParams(request.uri),
        body,
        request
      )
      val maybeRes = Try[Response[_]](HttpRouter.handle(req))
      logger.debug(s"Handling request $req resulted in $maybeRes")

      maybeRes.fold(
        { error =>
          logger.error(s"Handling request $req failed with exception", error)
          val response =
            FinagleResponse(request.version, Status.InternalServerError)
          if (writeExceptionToResponse) {
            val stringWriter = new StringWriter
            val printWriter = new PrintWriter(stringWriter)
            error.printStackTrace(printWriter)
            response.write(stringWriter.toString)
            printWriter.close()
            stringWriter.close()
          }
          Future.value(response)
        },
        { res =>
          logger.debug(s"Request $req handled successfully, answering $res")
          val response =
            FinagleResponse(request.version, Status.fromCode(res.status))
          res.headers.foreach { entry =>
            val (name, value) = entry
            response.headerMap.add(name, value)
          }
          response.write(res.written)
          Future.value(response)
        }
      )
    }

  logger.info(s"Binding to $bindAddress")
  val server = Http.serve(bindAddress, service)
  Await.ready(server)
}
