# Copyright  2013, 2014, Ontotext AD
#
# This file is free software; you can redistribute it and/or modify it under
# the terms of the GNU Lesser General Public License as published by the Free
# Software Foundation; either version 2.1 of the License, or (at your option)
# any later version.
# This library is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
# details.
# You should have received a copy of the GNU Lesser General Public License along
# with this library; if not, write to the Free Software Foundation, Inc.,
# 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

# IMPORTANT!!! Please note the packages below, needed for this example IMPORTANT!!!
require "typhoeus"
require "json"
require "zlib"
require "stringio"


endpoint = "https://lod.s4.ontotext.com/v1/FactForge/sparql"
key = "<api-key>"
secret = "<api-secret>"
headers = {'Accept' => "application/sparql-results+xml",
           'Content-Type'=> "application/x-www-form-urlencoded"}

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
    userpwd: key+":"+secret, 
    body: data,
    headers: headers)
hydra.queue(req)
hydra.run
response = req.response

if response.headers['Content-Encoding'] == 'gzip'
    gz = Zlib::GzipReader.new(StringIO.new(response.body.to_s))    
    puts gz.read, "\n"
else
    puts response.body, "\n"
end

# Response Code
print 'Status Code: ', response.code, "\n\n"

# Response Headers
puts 'Headers: '
response.headers.each do |type, header|
    print type, ': ', header, "\n"
end