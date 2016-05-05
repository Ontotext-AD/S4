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


endpoint = "https://rdf.s4.ontotext.com/<user-id>/<databaseName>/repositories/<repoName>/statements"
data = "update=PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
"INSERT Data{ <http://example/egbook> dc:title  \"This is an example title\" }"
api_key = "<your-credentials-here>"
key_secret = "<your-credentials-here>"
headers = {"Content-Type"=> "application/x-www-form-urlencoded"}

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