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


endpoint = "https://text.s4.ontotext.com/v1/twitie"
api_key = "<your-credentials-here>"
key_secret = "<your-credentials-here>"
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