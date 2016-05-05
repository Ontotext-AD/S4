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


endpoint = "https://text.s4.ontotext.com/v1/"
api_key = "<your-credentials-here>"
key_secret = "<your-credentials-here>"

hydra = Typhoeus::Hydra.hydra
req = Typhoeus::Request.new(endpoint, userpwd: api_key + ":" + key_secret)
hydra.queue(req)
hydra.run
response = req.response

puts response.body, "\n"

# Response Code
print "Status Code: ", response.code, "\n\n"

# Response Headers
puts "Headers: "
response.headers.each do |type, header|
    print type, ": ", header, "\n"
end