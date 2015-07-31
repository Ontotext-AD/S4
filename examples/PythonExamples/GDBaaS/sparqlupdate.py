#opyright  2013, 2014, Ontotext AD
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
from StringIO import StringIO
import gzip
import json

endpointUrl = "https://rdf.s4.ontotext.com/<user-id>/<db>/repositories/<repository>/statements"
keyId = "<api-key>"
password = "<api-secret>"

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

data = ("PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"+
"INSERT Data{ <http://example/egbook> dc:title  \"This is an example title\" }");

data=urllib2.quote(data);
data="update="+data;

print data;

headers = {
                'Content-type': "application/x-www-form-urlencoded"
}

#Prepare request
request = urllib2.Request(endpointUrl,data,headers)

response=urllib2.urlopen(request)

# Getting response
print response.read();


# Getting the code
print "\n\n\nThis gets the code: ", response.code

# Get the Headers.
print "The Headers are: ", response.info()

