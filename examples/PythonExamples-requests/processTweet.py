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
from array import *

endpoint = "https://text.s4.ontotext.com/v1"
service = "/twitie"
key = 's4ts9m036a8b'
secret = '6lucecm2t73558v'
annotationSelectorsArray = [":", "Original markups:"]

document = ("{\"text\":\"Nearly 200,000 people have been killed in #Syria "
            + "since the start of the conflict in 2011, according to "
            + "the U.N. http://t.co/pK7t8AD7Xf\","
            + "\"lang\":\"en\",\"entities\":{\"symbols\":[],"
            + "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\","
            + "\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\","
            + "\"url\":\"http://t.co/pK7t8AD7Xf\"}],"
            + "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}],"
            + "\"user_mentions\":[]},"
            + "\"id\":502743846716207104,"
            + "\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\","
            + "\"id_str\":\"502743846716207104\"}")
data = {
    "document": document,
    "documentType": "text/x-json-twitter",
    "annotationSelectors": annotationSelectorsArray
}
json_data = json.dumps(data)
headers = {
    'Accept': "application/json",
    'Content-type': "application/json",
    'Accept-Encoding': "gzip"
}

req = requests.post(
    endpoint + service, auth=(key, secret),
    data=json_data, headers=headers)

print (req.content)

# Response status code
print ('Request Code: {}\n'.format(req.status_code))

# Response Headers
head = dict(req.headers)
print ('Headers: ')
for each in head:
    print (each.capitalize(), ": ", head[each])
