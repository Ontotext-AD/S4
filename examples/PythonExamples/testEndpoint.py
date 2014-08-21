import urllib2

endpointUrl = "https://text.s4.ontotext.com/"
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

#Prepare request
request = urllib2.Request(endpointUrl)

response=urllib2.urlopen(request)

print response.read()

# Getting the code
print "\n\n\nThis gets the code: ", response.code

# Get the Headers. 
print "The Headers are: ", response.info()
