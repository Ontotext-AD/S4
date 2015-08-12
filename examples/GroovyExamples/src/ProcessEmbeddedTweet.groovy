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
def serviceId = 'twitie'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint + serviceId)
client.auth.basic(keyId, password)

def builder = new groovy.json.JsonBuilder();
builder(
	document : 
		"{\"text\":\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\","
		+ "\"lang\":\"en\",\"entities\":{\"symbols\":[],"
		+ "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\",\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\",\"url\":\"http://t.co/pK7t8AD7Xf\"}],"
		+ "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}],"
		+ "\"user_mentions\":[]},"
		+ "\"id\":502743846716207104,"
		+ "\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\","
		+ "\"id_str\":\"502743846716207104\"}",
	documentType : "text/x-json-twitter", //treat this document as twitter json
	annotationSelectors : [":", "Original markups:"] //output annotations from the default and Original markups sets
	);

println("Request body is:")
println(builder.toPrettyString())
def response = client.post(
	body : builder.toString(), //set request body
	requestContentType : "application/json",  //request body is always JSON, application/json is the only valid value for the Content-Type header
	contentType : "application/json") //request GATE JSON output
println response.getStatusLine()
println response.data.text
