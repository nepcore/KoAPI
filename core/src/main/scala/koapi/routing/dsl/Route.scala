package koapi.routing.dsl

import koapi.models.{Request, Response}
import koapi.models.http.Method

sealed trait RouteParam[T] {
  def name: String
  def fromString(part: String): T
}
final case class StringParam(name: String) extends RouteParam[String] {
  override def fromString(part: String): String = part
}
final case class IntParam(name: String) extends RouteParam[Int] {
  override def fromString(part: String): Int = part.toInt
}
final case class LongParam(name: String) extends RouteParam[Long] {
  override def fromString(part: String): Long = part.toLong
}
final case class BooleanParam(name: String) extends RouteParam[Boolean] {
  override def fromString(part: String): Boolean = part.toBoolean
}

sealed trait RoutePart
final case class SimpleRoutePart(part: String) extends RoutePart
final case class ParametrizedRoutePart(part: RouteParam[_]) extends RoutePart

final case class Route[T](
    method: Method.Value,
    parts: IndexedSeq[RoutePart],
    handler: Request[T] => Response[_]
) {
  val params: IndexedSeq[RouteParam[_]] = parts.map {
    case part: ParametrizedRoutePart => Seq(part.part)
    case _                           => Seq.empty
  }.flatten
  def /(part: RoutePart): Route[T] = copy(parts = parts :+ part)
}
