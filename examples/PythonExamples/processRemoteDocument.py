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
# You should have received a copy of the GNU Lesser General Public License along
# with this library; if not, write to the Free Software Foundation, Inc.,
# 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

import urllib2
import json
from StringIO import StringIO
import gzip
from array import *

endpointUrl = "https://text.s4.ontotext.com/v1/"
serviceId = "twitie"
keyId = "<your-credentials-here>"
password = "<your-credentials-here>"

# create a password manager
password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()

# Add the username and password.
password_mgr.add_password(None, endpointUrl, keyId ,password)
handler = urllib2.HTTPBasicAuthHandler(password_mgr)

# create "opener" (OpenerDirector instance)
opener = urllib2.build_opener(handler)

# Install the opener.
# Now all calls to urllib2.urlopen use our opener.
urllib2.install_opener(opener)


data = {
	"documentUrl" : "http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car",
	"documentType" : "text/html",
}

#json serialize
jsonData = json.dumps(data)
print(jsonData)

headers = {
                'Accept' : "application/json",
				'Content-type': "application/json",
				'Accept-Encoding':"gzip",
}

#Prepare request
request = urllib2.Request(endpointUrl+serviceId,jsonData,headers)

response=urllib2.urlopen(request)

if response.info().get('Content-Encoding') == 'gzip':
    buf = StringIO( response.read())
    f = gzip.GzipFile(fileobj=buf)
    data = f.read()
else:
    data = response.read()

# Getting response
print data


# Getting the code
print "\n\n\nThis gets the code: ", response.code

# Get the Headers. 
print "The Headers are: ", response.info()
