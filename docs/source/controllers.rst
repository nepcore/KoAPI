Controllers
===========

| Controllers are the main way of defining handlers for your HTTP routes
| A controller generally needs to implement the ``Controller`` trait which also gives you access to helper functions for writing handlers and building responses

Handling Requests
-----------------

Throughout this documentation you will often see handlers being defined similar to something like ``val handler: Action[NoBody] = get(/) { request => Ok() }``

Here the called method ``get`` defines that this handler is for requests using the ``GET`` method, similar functions exist for other HTTP methods as well

The first parameter passed to ``get`` (here ``/``) defines the route on which the handler will react, more info on defining routes can be found in the `Routes`_ section below

The second parameter is a function defining how the request should be handled, it gets the request as an input parameter and has to return a response

``Ok()`` is a helper for creating a ``200 OK`` response, similar helpers exist for most HTTP response codes, these also allow passing a body and headers and if you need a custom response code you can use ``Response()``

| The type ``Action[NoBody]`` specifies that we don't need the request body
| By using a different type instead of ``NoBody`` you can get access to the body in your handler
| You can use any type for your body as long as a ``BodyReader`` for it is available
| ``BodyReader``\ s for ``String`` are always available, for most other data types you'll want to take a look at :ref:`Body Readers`

The reason you'll often see handlers being assigned to ``val``\ s is that the methods for creating handlers return the handler, meaning that you can easily unit test your application by calling the handler with a request as input value, so for the above example you'd be able to call ``handler(request)`` and check if the returned response matches your expectations


Routes
------

When defining a handler you need to define what route it should react on and since you don't always have static routes :project_name: uses a dsl to define which parts of a route are static and which aren't

| For example let's say you want to have a route to get details about a user under ``/user/{id}`` where ``{id}`` is the user id
| This means we have 2 route parts, a static ``user`` part and a part that we don't know ahead of time
| In :project_name: you would define the static part as ``"user"`` and assuming that the user id is always a number it would be defined as ``IntParam("id")`` where ``"id"`` is the name under which we want to access the value in the handler later on
| Route parts are chained together using ``/``, so the above example would become ``"user" / IntParam("id")``

You can also add a leading ``/`` to your routes, this is a question of preference and does not have any effect on functionality
