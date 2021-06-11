package koapi.routing

import scala.language.implicitConversions

package object dsl {
  implicit def routePart(part: String): IndexedSeq[RoutePart] =
    IndexedSeq(SimpleRoutePart(part))
  implicit def routePart(part: RouteParam): IndexedSeq[RoutePart] =
    IndexedSeq(ParametrizedRoutePart(part))
  implicit class FromRoutePart(part1: String) {
    def /(part2: String): IndexedSeq[RoutePart] =
      IndexedSeq(SimpleRoutePart(part1), SimpleRoutePart(part2))
    def /(part2: RouteParam): IndexedSeq[RoutePart] =
      IndexedSeq(SimpleRoutePart(part1), ParametrizedRoutePart(part2))
  }
  implicit class FromRouteParam(part1: RouteParam) {
    def /(part2: String): IndexedSeq[RoutePart] =
      IndexedSeq(ParametrizedRoutePart(part1), SimpleRoutePart(part2))
    def /(part2: RouteParam): IndexedSeq[RoutePart] =
      IndexedSeq(ParametrizedRoutePart(part1), ParametrizedRoutePart(part2))
  }
  implicit class FromRouteParts(parts: IndexedSeq[RoutePart]) {
    def /(part2: String): IndexedSeq[RoutePart] =
      parts :+ SimpleRoutePart(part2)
    def /(part2: RouteParam): IndexedSeq[RoutePart] =
      parts :+ ParametrizedRoutePart(part2)
  }

  def / = IndexedSeq.empty[RoutePart]
}
