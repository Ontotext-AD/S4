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

endpoint = "https://text.s4.ontotext.com/v1/news"
api_key = "<your-credentials-here>"
key_secret = "<your-credentials-here>"
headers = {"Accept" => "application/json",
           "Content-Type" => "application/json",
           "Accept-Encoding" => "gzip"}
data = {
    "document" => "Tiruchirappalli is the " +
                "fourth largest city in the Indian state of " +
                "Tamil Nadu and is the administrative headquarters " +
                "of Tiruchirappalli District. Its recorded " +
                "history begins in the 3rd century BC, " +
                "when it was under the rule of the Cholas. " +
                "The city has also been ruled by the Pandyas, " +
                "Pallavas, Vijayanagar Empire, Nayak Dynasty, " +
                "the Carnatic state and the British. " +
                "It played a crucial role in the Carnatic Wars " +
                "between the British and the French " +
                "East India companies. During British rule, the city " +
                "was popular for the Trichinopoly cigar, its unique brand " +
                "of cheroot. Monuments include the Rockfort (pictured), the " +
                "Ranganathaswamy temple and the Jambukeswarar temple. " +
                "It is an important educational centre in Tamil Nadu, " +
                "housing nationally recognised institutions such as the " +
                "Indian Institute of Management and the National " +
                "Institute of Technology.",
    "documentType" => "text/plain"
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