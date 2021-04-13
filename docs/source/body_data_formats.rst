Body Data Formats
=================

.. _Body Readers:

Body Readers
------------

| Body readers allow to completely decouple the parsing of body data from the actual application logic
| Defining a body reader means implementing the ``BodyReader`` trait which defines one function that takes a byte array as input and returns an instance of the target type


Body Writers
------------

| Body writers allow to completely decouple the serializing of data structures from the actual application logic
| Defining a body writer means implementing the ``BodyWriter`` trait which defines one function that takes the data structure to be serialized and returns a byte array representation
