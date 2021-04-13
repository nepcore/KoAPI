package koapi.routing

import java.io.{PrintWriter, StringWriter}

import com.typesafe.config.ConfigFactory
import koapi.logging.Logger
import koapi.models.Response
import koapi.Responses

trait ErrorHandler {

  /** Handles an error
    *
    * @param error The error to be handled
    * @return A response representing the error
    */
  def apply(error: Error): Response[_]
}

object ErrorHandler {
  private var errorHandler: ErrorHandler = new DefaultErrorHandler

  /** Replaces the error handler to be used
    *
    * @param handler The new error handler
    */
  def setErrorHandler(handler: ErrorHandler): Unit = errorHandler = handler

  /** Handles an error
    *
    * @param error The error to be handled
    * @return A response representing the error
    */
  def apply(error: Error): Response[_] = errorHandler(error)
}

trait Error
case object NotFoundError extends Error
final case class BodyParsingException(cause: Throwable)
    extends Exception(cause)
    with Error
final case class HandlerException(error: Throwable)
    extends Exception(error)
    with Error

class DefaultErrorHandler extends ErrorHandler with Responses with Logger {
  private val config = ConfigFactory.load()
  private val writeExceptionToResponse =
    config.getBoolean("koapi.http.writeExceptionToResponse")

  override def apply(e: Error): Response[_] =
    e match {
      case NotFoundError => NotFound()
      case BodyParsingException(error) =>
        logger.error("Couldn't parse body", error)
        BadRequest()
      case HandlerException(error) =>
        logger.error("Handling request failed with exception", error)
        val response = InternalServerError()
        if (writeExceptionToResponse) {
          val stringWriter = new StringWriter
          val printWriter = new PrintWriter(stringWriter)
          error.printStackTrace(printWriter)
          response.copy(body = stringWriter.toString)
          printWriter.close()
          stringWriter.close()
        }
        response
      case error =>
        logger.error("Encountered error", error)
        InternalServerError()
    }
}
