@Grab(group='org.codehaus.groovy.modules.http-builder',
module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

def onlineServiceEndpoint = 'https://text.s4.ontotext.com/'
def shopItemId = 'twitie'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint + shopItemId)
client.auth.basic(keyId, password)

def builder = new groovy.json.JsonBuilder();
builder(
		documentUrl : "http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car", //process the document available under this url
		mimeType : "text/html" //treat the downloaded document as HTML
		);
println("JSON body of request: ")
println(builder.toPrettyString())
def response = client.post(
	body : builder.toString(), //the request body
	requestContentType : "application/json", //the request body is always JSON
	contentType : "application/gate+xml") //request GATE XML output
println response.getStatusLine()
println response.data.text