play-json
=========

| This module provides ``BodyReader``\ s and ``BodyWriter``\ s for working with JSON bodies
| Anything for which a ``Reads`` or ``Writes`` respectively is available (including the Play JSON representations of the JSON structure) is supported by this module
| All you need to do is import ``koapi.body.JsonBodyReader._`` and/or ``koapi.body.JsonBodyWriter._`` in your controller

You can add this module to your sbt project by adding ``libraryDependencies ++= "koapi" %% "play-json" % ":project_version:"`` to your ``build.sbt`` file
