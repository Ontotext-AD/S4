@Grab(group='org.codehaus.groovy.modules.http-builder',
	module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import org.apache.http.entity.FileEntity
import org.apache.http.util.EntityUtils

def onlineServiceEndpoint = 'https://text.s4.ontotext.com/'
def shopItemId = 'twitie'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint + shopItemId)
client.auth.basic(keyId, password)

def builder = new groovy.json.JsonBuilder();
builder(
	document : "Tiruchirappalli is the " +	//set the document to process in the request body
				"fourth largest city in the Indian state of " +
				"Tamil Nadu and is the administrative headquarters " +
				"of Tiruchirappalli District. Its recorded " +
				"history begins in the 3rd century BC, " +
				"when it was under the rule of the Cholas. " +
				"The city has also been ruled by the Pandyas, " +
				"Pallavas, Vijayanagar Empire, Nayak Dynasty, " +
				"the Carnatic state and the British. " +
				"It played a crucial role in the Carnatic Wars " +
				"(1746–63) between the British and the French " +
				"East India companies. During British rule, the city " +
				"was popular for the Trichinopoly cigar, its unique brand " +
				"of cheroot. Monuments include the Rockfort (pictured), the " +
				"Ranganathaswamy temple and the Jambukeswarar temple. " +
				"It is an important educational centre in Tamil Nadu, " +
				"housing nationally recognised institutions such as the " +
				"Indian Institute of Management and the National " +
				"Institute of Technology.",
	mimeType : "text/plain" //specify that the document should be treated as plain text
	);

println(builder.toPrettyString())
def response = client.post(
	body : builder.toString(), //set request body
	requestContentType : "application/json",  //request body is always JSON, application/json is the only valid value for the Content-Type header
	contentType : "application/gate+json") //request GATE JSON output
println response.getStatusLine()
println response.data.text