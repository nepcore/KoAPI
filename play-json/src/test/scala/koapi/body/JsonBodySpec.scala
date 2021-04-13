package koapi.body

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.Json
import koapi.routing.BodyParsingException

class JsonBodySpec extends AnyFlatSpec with Matchers {
  final case class Data(someKey: String, anotherKey: Long)
  object Data {
    implicit val writes = Json.writes[Data]
    implicit val reads = Json.reads[Data]
  }

  "JsonBodyWriter.jsonBodyWriter" should "convert a JsValue to a string" in {
    val json = Json.parse("""{"someKey": "some value"}""")
    Json.parse(JsonBodyWriter.jsonBodyWriter(json)) should be(json)
  }

  "JsonBodyWriter.jsonWritesBodyWriter" should "convert an instance with available writes to a json string" in {
    val value = Data("some value", 39)
    Json.parse(JsonBodyWriter.jsonWritesBodyWriter(this.Data.writes)(value)) should be(Json.toJson(value))
  }

  "JsonBodyReader.jsValueBodyReader" should "convert a string to a JsValue" in {
    val json = Json.parse("""{"someKey": "some value"}""")
    JsonBodyReader.jsValueBodyReader(json.toString.getBytes) should be(Some(json))
  }

  it should "throw a BodyParsingException if parsing fails" in {
    assertThrows[BodyParsingException] {
      JsonBodyReader.jsValueBodyReader("not json".getBytes)
    }
  }

  "JsonBodyReader.jsonReadsBodyReader" should "convert a string to a class representation" in {
    val value = Data("something", 39)
    JsonBodyReader.jsonReadsBodyReader(this.Data.reads)(Json.toJson(value).toString.getBytes) should be(Some(value))
  }

  it should "throw a BodyParsingException if parsing fails" in {
    assertThrows[BodyParsingException] {
      JsonBodyReader.jsonReadsBodyReader(this.Data.reads)("not json".getBytes)
    }
  }

  it should "throw a BodyParsingException if the parsed json doesn't fit the requested class" in {
    assertThrows[BodyParsingException] {
      JsonBodyReader.jsonReadsBodyReader(this.Data.reads)("""{"key": "value"}""".getBytes)
    }
  }
}
