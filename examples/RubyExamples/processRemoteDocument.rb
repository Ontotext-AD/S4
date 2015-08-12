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


endpoint = "https://text.s4.ontotext.com/v1"
service = "/twitie"
key = "<api-key>"
secret = "<api-secret>"
headers = {'Accept' => "application/json",
                'Content-Type'=> "application/json",
                'Accept-Encoding'=>"gzip"}
data = {
    "documentUrl" => "http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car",
    "documentType" => "text/html",
}
jsonData = data.to_json

hydra = Typhoeus::Hydra.hydra
req = Typhoeus::Request.new(endpoint+service,
    method: :post,
    userpwd: key + ":" + secret, 
    body: jsonData,
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