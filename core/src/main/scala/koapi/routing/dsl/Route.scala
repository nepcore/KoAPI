package koapi.routing.dsl

import koapi.models.{Request, Response}
import koapi.models.http.Method

sealed trait RouteParam {
  def name: String
  def isValid(part: String): Boolean
}
final case class StringParam(name: String) extends RouteParam {
  override def isValid(part: String) = true
}
final case class IntParam(name: String) extends RouteParam {
  override def isValid(part: String) = part.toIntOption.isDefined
}
final case class LongParam(name: String) extends RouteParam {
  override def isValid(part: String) = part.toLongOption.isDefined
}
final case class BooleanParam(name: String) extends RouteParam {
  override def isValid(part: String) = part.toBooleanOption.isDefined
}

final case class Param(value: String) {
  def getString(): String = value
  def getInt(): Int = value.toInt
  def getLong(): Long = value.toLong
  def getBoolean(): Boolean = value.toBoolean

  def getStringOption(): Option[String] = Some(value)
  def getIntOption(): Option[Int] = value.toIntOption
  def getLongOption(): Option[Long] = value.toLongOption
  def getBooleanOption(): Option[Boolean] = value.toBooleanOption
}

sealed trait RoutePart
final case class SimpleRoutePart(part: String) extends RoutePart
final case class ParametrizedRoutePart(part: RouteParam) extends RoutePart

final case class Route[T](
    method: Method.Value,
    parts: IndexedSeq[RoutePart],
    handler: Request[T] => Response[_]
) {
  val params: IndexedSeq[RouteParam] = parts.map {
    case part: ParametrizedRoutePart => Seq(part.part)
    case _                           => Seq.empty
  }.flatten
  def /(part: RoutePart): Route[T] = copy(parts = parts :+ part)
}
