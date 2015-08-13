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

# Warning! The following script uses information from example.rdf
# which is uploaded in the repo. Make sure you have both this file and
# example.rdf in the same directory before running this

import requests


endpoint = ("https://rdf.s4.ontotext.com/<user-id>/<database>/"
            + "repositories/<repo-id>/statements")
key = '<api-key>'
secret = '<api-secret>'
query = "Select * {?s ?p ?o} limit 50"

headers = {'Content-Type': 'application/rdf+xml;charset=UTF-8'}

with open('example.rdf', 'rb') as data_file:
    req = requests.post(
        endpoint, headers=headers, data=data_file, auth=(key, secret))

response = req.content.decode('utf-8')
print (response, '\n')

# Response status code
print ('Request Code: {}\n'.format(req.status_code))

# Response Headers
head = dict(req.headers)
print ('Headers: ')
for each in head:
    print (each.capitalize(), ": ", head[each])
