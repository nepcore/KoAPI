finagle-http
============

| This module makes your app available on a standalone HTTP server that is accessible on port 9000 by default (check :doc:`../configuration` for how to change that)
| It also provides a main method to run your app from, however as sbt doesn't search dependencies for main methods, so you'll have to do something like ``sbt runMain koapi.fiangle.Main`` to start your application from that
| If you prefer to have the main method in your project you can do so as well, all you need to do is to instanciate ``koapi.finagle.FinagleHttpServer``

You can add this module to your sbt project by adding ``libraryDependencies ++= "koapi" %% "finagle-http" % ":project_version:"`` to your ``build.sbt`` file
