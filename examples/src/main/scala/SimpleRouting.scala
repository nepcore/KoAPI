import koapi.Controller
import koapi.routing.dsl._

class SimpleController extends Controller {
  val index: Action[String] = get(/) { _ => Ok() }

  val static: Action[String] = get("hello") { _ => Ok("Hello, world!") }

  val pathParam: Action[String] = get("hello" / StringParam("name")) {
    request => Ok(s"Hello, ${request.pathParams("name").getString()}")
  }

  val body: Action[String] = post("hello") { request =>
    request.body.map(body => Ok(s"Hello, $body!")).getOrElse(BadRequest())
  }
}
