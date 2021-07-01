Modules
=======

.. toctree::
   :hidden:

   modules/finagle-http
   modules/aws-api-gateway
   modules/play-json
   modules/testutils


| :project_name: is built to be modular, this also means there are different types of modules
| For example some provide different ways to receive requests while others provide ways to work with data formats like JSON


Entry Points
------------
Entry points are ways to take requests and send out responses, for example a module hooking :project_name: up to a standalone HTTP server or a cloud platform like AWS would be an entry point


finagle-http
^^^^^^^^^^^^
:doc:`modules/finagle-http` provides a standalone HTTP server based on Twitters finagle


aws-api-gateway
^^^^^^^^^^^^^^^
:doc:`modules/aws-api-gateway` provides a request handler that can be deployed to AWS Lambda and used with AWS API Gateway


Data Formats
------------
Modules that add support for data formats usually implement one or more ``BodyReader``\ s and ``BodyWriter``\ s


play-json
^^^^^^^^^
:doc:`modules/play-json` provides ``BodyWriter``\ s and ``BodyReader``\ s to handle json using the `Play JSON library <https://github.com/playframework/play-json>`_

Miscellaneous
-------------

testutils
^^^^^^^^^
:doc:`modules/testutils` provides utilities to make unit testing your controllers easy
