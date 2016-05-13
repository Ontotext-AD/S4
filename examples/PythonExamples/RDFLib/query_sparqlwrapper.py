# Copyright 2016 Ontotext AD
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from SPARQLWrapper import SPARQLWrapper, JSON, XML, N3, TURTLE


sparql = SPARQLWrapper("https://rdf.s4.ontotext.com/<user-id>/" +
                       "<db-id>/repositories/<repo-name>")

sparql.setQuery("""SELECT * WHERE { ?s ?p ?o } LIMIT 10""")

# Optional - use only if your repository is not Publicly accessible
sparql.setCredentials("<s4-api-key>", "<s4-key-secret>")


# JSON example
sparql.setReturnFormat(JSON)
response = sparql.query().convert()

result = response["results"]["bindings"]
for each in result:
    print (each)


# XML example
sparql.setReturnFormat(XML)
response = sparql.query().convert().toxml()
print (response)


# N3 example
sparql.setReturnFormat(N3)
response = sparql.query().convert().decode("utf-8")
print (response)


# TURTLE example
sparql.setReturnFormat(TURTLE)
response = sparql.query().convert().decode("utf-8")
print (response)
