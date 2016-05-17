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
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

def onlineServiceEndpoint = 'https://text.s4.ontotext.com/v1/'
def serviceId = 'twitie'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint + serviceId)
client.auth.basic(keyId, password)

def builder = new groovy.json.JsonBuilder();
builder(
        documentUrl : "http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car", //process the document available under this url
        documentType : "text/html" //treat the downloaded document as HTML
        );
println("JSON body of request: ")
println(builder.toPrettyString())
def response = client.post(
    body : builder.toString(), //the request body
    requestContentType : "application/json", //the request body is always JSON
    contentType : "application/json") //request GATE XML output
println response.getStatusLine()
println response.data.text