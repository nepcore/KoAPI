Error Handling
==============

| :project_name:s default error handling approach is to respond with a ``404 Not Found`` if no handler is available for the requested route, ``400 Bad Request`` if the ``BodyReader`` defined for the handler reported a problem and ``500 Internal Server Error`` if anything throws an unexpected exception in the process of handling a request
| While this behaviour should be fine for most use cases, you might need or want to customize it
| To do so, you need to implement the ``ErrorHandler`` trait which has exactly one method taking an error representation and returning a response
| You can change the ``ErrorHandler`` being used by passing the desired handler to ``ErrorHandler.setErrorHandler``
