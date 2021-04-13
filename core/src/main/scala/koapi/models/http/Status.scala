package koapi.models.http

/** Common HTTP Status Codes */
object Status {
  // 2xx
  val OK = 200
  val CREATED = 201
  val ACCEPTED = 202
  val NO_CONTENT = 204

  // 3xx
  val MOVED_PERMANENTLY = 301
  val FOUND = 302
  val SEE_OTHER = 303
  val TEMPORARY_REDIRECT = 307
  val PERMANENT_REDIRECT = 308

  // 4xx
  val BAD_REQUEST = 400
  val UNAUTHORIZED = 401
  val FORBIDDEN = 403
  val NOT_FOUND = 404
  val NOT_ACCEPTABLE = 406
  val CONFLICT = 409
  val GONE = 410

  // 5xx
  val INTERNAL_SERVER_ERROR = 500
  val NOT_IMPLEMENTED = 501
  val BAD_GATEWAY = 502
  val SERVICE_UNAVAILABLE = 503
  val GATEWAY_TIMEOUT = 504
}
