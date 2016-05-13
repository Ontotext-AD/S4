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


endpoint = "https://text.s4.ontotext.com/v1/twitie"
api_key = "<s4-api-key>"
key_secret = "<s4-key-secret>"
headers = {"Accept" => "application/json",
           "Content-Type" => "application/json",
           "Accept-Encoding" => "gzip"}

document = "{\"text\":\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\"," +
"\"lang\":\"en\",\"entities\":{\"symbols\":[]," +
"\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\",\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\",\"url\":\"http://t.co/pK7t8AD7Xf\"}]," +
"\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}]," +
"\"user_mentions\":[]}," + "\"id\":502743846716207104," +
"\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\"," +
"\"id_str\":\"502743846716207104\"}"

data = {
    "document" => document,
    "documentType" => "text/x-json-twitter"
}
jsonData = data.to_json

hydra = Typhoeus::Hydra.hydra
req = Typhoeus::Request.new(endpoint,
    method: :post,
    userpwd: api_key + ":" + key_secret, 
    body: jsonData,
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