Usage: Loader <options>
 -chunksize <arg>   The number of statements, sent on each transaction
                    (optional). Defaults to 50000
 -graph <arg>       The named graph to load the data in (optional)
 -i <arg>           Input RDF dump file or folder
 -r                 Traverse recursively the input folder (optional)
 -u <arg>           Credentials for accessing the repository service
                    (optional)
 -url <arg>         The URL of the Sesame/RDF4J repository
 
 Example:
 
 Loader.cmd -url https://rdf.s4.ontotext.com/xyz/repositories/dpaas -u key:secret -i D:\\prj\\data
 