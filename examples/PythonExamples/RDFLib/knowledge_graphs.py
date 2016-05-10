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


# Get all countries in Europe
query = """PREFIX dbp-ont: <http://dbpedia.org/ontology/>
PREFIX geo-ont: <http://www.geonames.org/ontology#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
PREFIX dbpedia: <http://dbpedia.org/resource/>

SELECT DISTINCT ?name
WHERE {
    ?country rdf:type dbp-ont:Country ;
             skos:prefLabel ?name ;
             geo-ont:parentFeature dbpedia:Europe .
} ORDER BY ?name"""


sparql = SPARQLWrapper("https://lod.s4.ontotext.com/v1/FactForge/sparql")

sparql.setQuery(query)

# Your S4 API Credenials
sparql.setCredentials("<your-s4-api-key>", "<your-s4-key-secret>")

# Optional - uncomment line if necessary, default method is GET
# sparql.method = "POST"

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
