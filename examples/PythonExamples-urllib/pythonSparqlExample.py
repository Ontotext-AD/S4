#!/usr/bin/env python
# -*- coding: utf-8 -*-

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

import urllib2
from StringIO import StringIO
import gzip
import json

endpointUrl = "https://lod.s4.ontotext.com/v1/FactForge/sparql"
keyId = "<your-credentials-here>"
password = "<your-credentials-here>"

# create a password manager
password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()

# Add the username and password.
password_mgr.add_password(None, endpointUrl, keyId, password)
handler = urllib2.HTTPBasicAuthHandler(password_mgr)

# create "opener" (OpenerDirector instance)
opener = urllib2.build_opener(handler)

# Install the opener.
# Now all calls to urllib2.urlopen use our opener.
urllib2.install_opener(opener)

data = ("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
        + "PREFIX dbpedia: <http://dbpedia.org/resource/>\n"
        + "PREFIX dbp-ont: <http://dbpedia.org/ontology/>\n"
        + "PREFIX geo-ont: <http://www.geonames.org/ontology#>\n"
        + "PREFIX umbel-sc: <http://umbel.org/umbel/sc/>\n\n"
        + "SELECT DISTINCT ?Company ?Location\nWHERE {\n"
        + "    ?Company rdf:type dbp-ont:Company ;\n"
        + "             dbp-ont:industry dbpedia:Computer_software ;\n"
        + "             dbp-ont:foundationPlace ?Location .\n"
        + "    ?Location geo-ont:parentFeature ?o.\n"
        + "    ?o geo-ont:parentCountry dbpedia:United_States .\n} limit 5")

data = urllib2.quote(data)
data = "query=" + data

print data

headers = {
    'Accept': "application/sparql-results+xml",
    'Content-type': "application/x-www-form-urlencoded"
}

# Prepare request
request = urllib2.Request(endpointUrl, data, headers)

response = urllib2.urlopen(request)

# Getting response
print response.read()


# Getting the code
print "\n\n\nThis gets the code: ", response.code

# Get the Headers.
print "The Headers are: ", response.info()
