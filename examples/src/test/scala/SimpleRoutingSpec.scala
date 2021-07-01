import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import koapi.TestController
import koapi.models.http.Method

class SimpleControllerSpec extends AnyFlatSpec with Matchers {
  "The SimpleController" should "correctly handle GET requests to /" in {
    (new SimpleController() with TestController).simulate(uri = "http://localhost/").status shouldBe 200
  }

  it should "correctly handle GET requests to /hello" in {
    val response = (new SimpleController() with TestController).simulate(uri = "http://localhost/hello")
    response.status shouldBe 200
    response.body shouldBe "Hello, world!"
  }

  it should "correctly handle GET requests to /hello/:name" in {
    val response = (new SimpleController() with TestController).simulate(uri = "http://localhost/hello/Tester")
    response.status shouldBe 200
    response.body shouldBe "Hello, Tester"
  }

  it should "correctly handle POST requests with body to /hello" in {
    val response = (new SimpleController() with TestController).simulate(
      method = Method.POST,
      uri = "http://localhost/hello",
      body = "Test"
    )
    response.status shouldBe 200
    response.body shouldBe "Hello, Test!"
  }

  it should "correctly handle POST requests without body to /hello" in {
    val response = (new SimpleController() with TestController).simulate(
      method = Method.POST,
      uri = "http://localhost/hello"
    )
    response.status shouldBe 400
  }
}
