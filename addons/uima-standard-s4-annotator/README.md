UIMA standard S4 Annotator
=============================

This component provides access to the [S4][3] text analytics services directly from the UIMA platform. The UIMA S4 Annotator component
is implemented as an Annotator type component which can be used in provided UIMA GUI tools for processing documents and it also 
acts as a local proxy to the remotely accessible [RESTful services of S4][4], hiding the complexity of the underlying technologies and
communication protocols. For portability we have packaged this component as a PEAR package. The PEAR can be installed and you can 
integrate its component in any UIMA processing pipeline regardless of the context and it does not have any requirements or assumptions 
about the type of pre-processing or post-processing of the textual data being annotated. It can be used standalone of in a [Collection
Processing Engine (CPE)][2] pipeline.

## Documentation
- [Users guide][1]

[1]: http://docs.s4.ontotext.com/display/S4docs/UIMA+Annotator
[2]: http://uima.apache.org/downloads/releaseDocs/2.3.0-incubating/docs/html/tutorials_and_users_guides/tutorials_and_users_guides.html#ugr.tug.cpe
[3]: http://s4.ontotext.com/
[4]: http://docs.s4.ontotext.com/display/S4docs/Service+Endpoints