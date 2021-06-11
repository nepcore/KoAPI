Getting Started
===============

The following line adds :project_name:s standalone implementation to your project, for other options see :doc:`modules`

.. code:: scala

    libraryDependencies ++= "koapi" %% "finagle-http" % ":project_version:"

Now you're ready to write some code

First we'll write a simple handler that will simply respond with ``Hello world!``

.. code:: scala

    import koapi.Controller
    import koapi.routing.dsl._
    
    class HelloWorldController extends Controller {
      val helloWorld: Action[NoBody] = get("hello") { _ => Ok("Hello world!") }
    }

If you run your project now you'll be able to send a request to ``/hello`` and you will get ``Hello world!`` as response

Sometimes you might want to take some user input in different ways though, so let's add a route that takes a name as a path parameter and greets that person

.. code:: scala

    val helloPathParam: Action[NoBody] = get("hello" / StringParam("name")) { request =>
      val name = request.pathParams("name").getString()
      Ok(s"Hello $name!")
    }

You can also use the HTTP body as a source for data

.. code:: scala

    val helloBody: Action[String] = post("hello") { request =>
      request.body.map { name =>
        Ok(s"Hello $name!")
      }.getOrElse(BadRequest("I need your name to be able greet you"))
    }

| However, you'll likely not be using plain text bodies, usually your request bodies tend to contain data encoded in a format like JSON
| Using the right :doc:`modules` you don't have to worry about that either though
| Suppose you want to accept a JSON body, you could use the play-json module to be able to do something like the following

.. code:: scala

    import koapi.body.JsonBodyWriter._
    import koapi.body.JsonBodyReader._
    import play.api.libs.json.{Json, JsObject}

    val helloJson: Action[JsObject] = post("hello") { request =>
      request.body.map { json =>
        Ok(Json.obj("greeting" -> s"Hello ${(json \ "name").as[String]}!"))
      }.getOrElse(BadRequest(Json.obj("error" -> "I need your name to be able greet you")))
    }

| This should be all you need to get started using :project_name:
| Everything else can be found right here in the documentation as well
