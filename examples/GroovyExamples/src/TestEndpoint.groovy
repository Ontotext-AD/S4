/* Self-Service Semantic Suite
Copyright (c) 2014, Ontotext AD, All rights reserved.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.
*/

@Grab(group='org.codehaus.groovy.modules.http-builder',
module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import org.apache.http.entity.FileEntity
import org.apache.http.util.EntityUtils

def onlineServiceEndpoint = 'https://text.s4.ontotext.com/v1/'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint)
client.auth.basic(keyId, password)
def response = client.get([:])
println response.getStatusLine()
println response.getData()
