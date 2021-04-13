import koapi.Controller
import koapi.routing.dsl._
import koapi.body.JsonBodyWriter._
import koapi.body.JsonBodyReader._
import play.api.libs.json.Json
import play.api.libs.json.JsValue

case class GreetingRequestBody(name: String)
case class GreetingResponseBody(greeting: String)

object GreetingRequestBody {
  implicit val requestFormat = Json.format[GreetingRequestBody]
}

object GreetingResponseBody {
  implicit val responseFormat = Json.format[GreetingResponseBody]
}

class JsonController extends Controller {
  val generic: Action[JsValue] = post("json" / "hello") { request =>
    request.body
      .map { body =>
        (body \ "name")
          .asOpt[String]
          .fold(
            BadRequest(Json.obj("error" -> "no name provided"))
          ) { name => Ok(Json.obj("greeting" -> s"Hello, $name!")) }
      }
      .getOrElse(BadRequest(Json.obj("error" -> "no request body")))
  }

  val modeledBody: Action[GreetingRequestBody] = post("json" / "greet") {
    request =>
      request.body
        .map { body => Ok(GreetingResponseBody(s"Hello, ${body.name}!")) }
        .getOrElse(BadRequest(Json.obj("error" -> "no request body")))
  }
}
