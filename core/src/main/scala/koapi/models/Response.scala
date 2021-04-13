package koapi.models

import koapi.body.BodyWriter

final case class Response[T](
    status: Int,
    body: T,
    headers: Map[String, String]
)(implicit
    writer: BodyWriter[T]
) {
  def status(newStatus: Int): Response[T] = copy(status = newStatus)
  def body[X](newBody: X)(implicit newWriter: BodyWriter[X]): Response[X] =
    copy(body = newBody)
  def setHeaders(newHeaders: Map[String, String]): Response[T] =
    copy(headers = newHeaders)
  def setHeaders(newHeaders: (String, String)*): Response[T] =
    copy(headers = newHeaders.toMap)
  def addHeader(name: String, value: String): Response[T] =
    copy(headers = headers + (name -> value))
  def removeHeader(name: String): Response[T] = copy(headers = headers - name)

  override def toString: String =
    s"Status: $status, Body: ${writer(body)}, Headers: $headers"
  def written: Array[Byte] = writer(body)
}
