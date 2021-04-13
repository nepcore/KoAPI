package koapi.models.http

/** HTTP Request Methods */
object Method extends Enumeration {
  type Method = Value
  val GET, POST, PUT, DELETE, HEAD, TRACE, OPTIONS, PATCH = Value
}
