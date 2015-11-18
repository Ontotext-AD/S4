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


endpoint = ("https://rdf.s4.ontotext.com/<user-id>/<databaseName>/"
            + "repositories/<repoName>/statements")
api_key = "<your-credentials-here>"
key_secret = "<your-credentials-here>"
query = """PREFIX dc: <http://purl.org/dc/elements/1.1/>
INSERT DATA {<http://example/egbook> dc:title \"This is an example title\"}"""

headers = {
    "Content-Type": "application/x-www-form-urlencoded"
}

req = requests.post(
    endpoint, headers=headers,
    data="update="+query, auth=(api_key, key_secret))

# Response status code
print ("Request Code: {}\n".format(req.status_code))

# Response Headers
head = dict(req.headers)
print ("Headers: ")
for each in head:
    print (each.capitalize(), ": ", head[each])
