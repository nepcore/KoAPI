package koapi.body
import java.nio.charset.Charset

trait BodyWriter[T] {
  def apply(obj: T): Array[Byte]
}

trait BodyReader[T] {
  def apply(body: Array[Byte]): Option[T]
}

trait DefaultBodyWriters {
  implicit val byteArrayBodyWriter: BodyWriter[Array[Byte]] = bytes => bytes
  implicit val stringBodyWriter: BodyWriter[String] = _.getBytes()
  implicit def optionBodyWriter[T](implicit
      writer: BodyWriter[T]
  ): BodyWriter[Option[T]] = _.map(writer(_)).getOrElse(Array.emptyByteArray)
  implicit val noneBodyWriter: BodyWriter[Option[Nothing]] = _ =>
    Array.emptyByteArray
}

object BodyWriter extends DefaultBodyWriters

trait DefaultBodyReaders {
  implicit val byteArrayBodyReader: BodyReader[Array[Byte]] = bytes =>
    Some(bytes)
  implicit val stringBodyReader: BodyReader[String] =
    bytes => Some(new String(bytes, Charset.forName("UTF-8")))
  implicit val nothingBodyReader: BodyReader[None.type] = _ => None
}

object BodyReader extends DefaultBodyReaders {
  def nonEmpty[T](body: Array[Byte])(f: Array[Byte] => Option[T]): Option[T] =
    body match {
      case bytes if bytes.length == 0 => None
      case bytes                      => f(bytes)
    }
}
