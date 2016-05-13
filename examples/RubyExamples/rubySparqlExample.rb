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

# IMPORTANT!!! Please note the packages below, needed for this example IMPORTANT!!!
require "typhoeus"
require "json"
require "zlib"
require "stringio"


endpoint = "https://lod.s4.ontotext.com/v1/FactForge/sparql"
api_key = "<s4-api-key>"
key_secret = "<s4-key-secret>"
headers = {"Accept" => "application/sparql-results+json",
           "Content-Type" => "application/x-www-form-urlencoded"}

data = "query=PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
    "PREFIX dbpedia: <http://dbpedia.org/resource/>\n" +
    "PREFIX dbp-ont: <http://dbpedia.org/ontology/>\n" +
    "PREFIX geo-ont: <http://www.geonames.org/ontology#>\n" +
    "PREFIX umbel-sc: <http://umbel.org/umbel/sc/>\n\n" +
    "SELECT DISTINCT ?Company ?Location\nWHERE {\n" +
    "    ?Company rdf:type dbp-ont:Company ;\n" +
    "             dbp-ont:industry dbpedia:Computer_software ;\n" +
    "             dbp-ont:foundationPlace ?Location .\n" +
    "    ?Location geo-ont:parentFeature ?o.\n" +
    "    ?o geo-ont:parentCountry dbpedia:United_States .\n} limit 5"

hydra = Typhoeus::Hydra.hydra
req = Typhoeus::Request.new(endpoint,
    method: :post,
    userpwd: api_key + ":" + key_secret, 
    body: data,
    headers: headers)
hydra.queue(req)
hydra.run
response = req.response

if response.headers["Content-Encoding"] == "gzip"
    gz = Zlib::GzipReader.new(StringIO.new(response.body.to_s))    
    puts gz.read, "\n"
else
    puts response.body, "\n"
end

# Response Code
print "Status Code: ", response.code, "\n\n"

# Response Headers
puts "Headers: "
response.headers.each do |type, header|
    print type, ": ", header, "\n"
end