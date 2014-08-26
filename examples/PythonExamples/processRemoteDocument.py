#!/usr/bin/env python
# -*- coding: utf-8 -*-
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
