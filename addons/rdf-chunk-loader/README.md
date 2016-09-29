RDF chunk loader CLI
=============================

This unitility component facilitates RDF data loading from big dumps or from multiple files. The loading of big files is performance optimised by chunking the data flow into smaller transactions.


## Usage

```
Loader <options>
 -chunksize <arg>   The number of statements, sent on each transaction
                    (optional). Defaults to 50000
 -graph <arg>       The named graph to load the data in (optional)
 -i <arg>           Input RDF dump file or folder
 -r                 Traverse recursively the input folder (optional)
 -u <arg>           Credentials for accessing the repository service, column separated
                    (optional)
 -url <arg>         The URL of the Sesame/RDF4J repository
```

## Download

- [Binary distribution](http://ontotext-ad.github.io/S4/addons/rdf-chunk-loader/distrib/dbaas-loader-bin.zip)
