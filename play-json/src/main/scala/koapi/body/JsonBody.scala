package koapi.body

import koapi.routing.BodyParsingException
import play.api.libs.json.{JsValue, Json, Writes, Reads}
import scala.util.Try

object JsonBodyWriter {
  implicit val jsonBodyWriter: BodyWriter[JsValue] = _.toString.getBytes
  implicit def jsonWritesBodyWriter[T](implicit
      writes: Writes[T]
  ): BodyWriter[T] = obj => Json.toJson(obj).toString.getBytes
}

object JsonBodyReader {
  implicit val jsValueBodyReader: BodyReader[JsValue] =
    BodyReader.nonEmpty(_) { body =>
      Try(Json.parse(body)).fold(
        error => throw new BodyParsingException(error),
        ok => Some(ok)
      )
    }

  implicit def jsonReadsBodyReader[T](implicit reads: Reads[T]): BodyReader[T] =
    BodyReader.nonEmpty(_) { body =>
      Try(Json.parse(body).as[T]).fold(
        error => throw new BodyParsingException(error),
        ok => Some(ok)
      )
    }
}
