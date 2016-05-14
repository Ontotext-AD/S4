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

import requests
import json


api_key = "<s4-api-key>"
key_secret = "<s4-key-secret>"

endpoint = ("https://rdf.s4.ontotext.com/<user-id>/<db-id>" +
            "/repositories/<repo-name>")

data = {"repositoryID": "<repo-name>",  # Same as <repo-name> in endpoint
        "label": "Description of my repository",
        "ruleset": "owl-horst-optimized"}
jsonData = json.dumps(data)

headers = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}

req = requests.put(
    endpoint, headers=headers,
    data=jsonData, auth=(api_key, key_secret))

# Response status code
print ("Request Code: {}\n".format(req.status_code))

# Response Headers
head = dict(req.headers)
print ("Headers: ")
for each in head:
    print (each.capitalize(), ": ", head[each])
