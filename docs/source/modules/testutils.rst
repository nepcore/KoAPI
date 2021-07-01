testutils
=========

This module provides helpers to make unit testing controllers easier

You can add this module to your sbt project by adding ``libraryDependencies ++= "koapi" %% "testutils" % ":project_version:" % Test`` to your ``build.sbt`` file

Usage
^^^^^

To test your controller, instanciate it extending the ``TestController`` trait like in the following example:

.. code:: scala

   val controller = new MyController() with TestController

Then you can call the ``simulate`` function on this instance which takes the basic request information and routes it within this controller instance, returning the ``Response`` object your controller returns for the request or, should there be no handler for the request, throwing an exception
