Configuration
=============

Some aspects of :project_name: can be changed using the configuration

You can either have an ``application.conf`` file in your projects resouces, or you can override the config file or even single values using command line switches or environment variables, all of this is part of `Lightbends configuration library <https://github.com/lightbend/config>`_ and details can be found in the repositories readme

Here is a list of things you can configure in :project_name:

+-------------------------------------+---------+--------------+----------------------------------------------------------------------------------------------------------------------------------------+
| Config entry                        | Default | Module       | Effect                                                                                                                                 |
+=====================================+=========+==============+========================================================================================================================================+
| koapi.router.autoDiscover           | true    | core         | | If set to true the class path will automatically be scanned for all implementations of the Controller trait at startup               |
|                                     |         |              | | This can be slow however, so especially in short running environments like AWS Lambda this is not recommended                        |
+-------------------------------------+---------+--------------+----------------------------------------------------------------------------------------------------------------------------------------+
| koapi.router.controllers            | []      | core         | | If ``koapi.router.autoDiscover`` is set to false, this is used to find the Controllers to be loaded                                  |
|                                     |         |              | | Should be set to a list of fully qualified class names                                                                               |
+-------------------------------------+---------+--------------+----------------------------------------------------------------------------------------------------------------------------------------+
| koapi.http.writeExceptionToResponse | false   | core         | | If set to true and using the default error handler, any Exceptions thrown by your handlers will be printed to the HTTP response body |
|                                     |         |              | | This *can* have security implications in a production environment                                                                    |
+-------------------------------------+---------+--------------+----------------------------------------------------------------------------------------------------------------------------------------+
| koapi.http.port                     | 9000    | finagle-http | The port on which the server will be accessible                                                                                        |
+-------------------------------------+---------+--------------+----------------------------------------------------------------------------------------------------------------------------------------+
| koapi.http.bindIp                   | 0.0.0.0 | finagle-http | The IP to bind to, only relevant when the system has more than one IP but you only want the application to be available on one of them |
+-------------------------------------+---------+--------------+----------------------------------------------------------------------------------------------------------------------------------------+
