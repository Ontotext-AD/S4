#!/usr/bin/env python
# -*- coding: utf-8 -*-
import urllib2
import urllib
import json
from array import *

endpointUrl = "https://text.s4.ontotext.com/v1/"
ItemId = "twitie"
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

annotationSelectorsArray = [":", "Original markups:"]

data = {
	"document" : "{\"text\":\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\","
		+ "\"lang\":\"en\",\"entities\":{\"symbols\":[],"
		+ "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\",\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\",\"url\":\"http://t.co/pK7t8AD7Xf\"}],"
		+ "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}],"
		+ "\"user_mentions\":[]},"
		+ "\"id\":502743846716207104,"
		+ "\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\","
		+ "\"id_str\":\"502743846716207104\"}",
	"documentType" : "text/x-json-twitter",
	"annotationSelectors":annotationSelectorsArray
}

#json serialize
jsonData = json.dumps(data)
print(jsonData)

#prepare headers
headers = {
                'Accept' : "application/gate+json",
				'Content-type': "application/json",
				'Accept-Encoding':"gzip",
}

#Prepare request
request = urllib2.Request(endpointUrl+ItemId,jsonData,headers)

response=urllib2.urlopen(request)

# Getting response
print json.dumps(response.read())

# Getting the code
print "\n\n\nThis gets the code: ", response.code

# Get the Headers. 
print "The Headers are: ", response.info()
