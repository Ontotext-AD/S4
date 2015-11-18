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
# You should have received a copy of the GNU Lesser General Public License
# along with this library; if not, write to the Free Software Foundation, Inc.,
# 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

# IMPORTANT!!! Please install the packages below, needed for this example

import requests
import json

endpoint = "https://text.s4.ontotext.com/v1/news"
api_key = "<your-credentials-here>"
key_secret = "<your-credentials-here>"
data = {
    "document": "Tiruchirappalli is the " +
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
    "documentType": "text/plain"
}
jsonData = json.dumps(data)
headers = {
    "Accept": "application/json",
    "Content-Type": "application/json",
    "Accept-Encoding": "gzip",
}

req = requests.post(
    endpoint, headers=headers,
    data=jsonData, auth=(api_key, key_secret))

response = json.loads(req.content.decode("utf-8"))
print (response, "\n")

# Response status code
print ("Request Code: {}\n".format(req.status_code))

# Response Headers
head = dict(req.headers)
print ("Headers: ")
for each in head:
    print (each.capitalize(), ": ", head[each])
