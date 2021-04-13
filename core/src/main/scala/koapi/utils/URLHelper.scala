package koapi.utils

import java.net.URLDecoder

object URLHelper {
  def parseQueryParams(uri: String): Map[String, Seq[String]] = {
    val parts = uri.split("\\?")
    if (parts.length > 1) {
      val query = parts(1)
      val encoding = "UTF-8"
      query
        .split("&")
        .flatMap { param =>
          val parts = param.split("=")
          val key = URLDecoder.decode(parts(0), encoding)
          val value =
            if (parts.length > 1) URLDecoder.decode(parts(1), encoding) else ""
          Map(key -> value)
        }
        .groupBy(_._1)
        .mapValues(_.toSeq.map(_._2))
        .toMap
    } else Map.empty
  }
}
