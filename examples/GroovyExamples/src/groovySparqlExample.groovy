@Grab(group='org.codehaus.groovy.modules.http-builder',
	module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import org.apache.http.entity.FileEntity
import org.apache.http.util.EntityUtils
import java.net.URLEncoder

def onlineServiceEndpoint = 'https://lod.s4.ontotext.com/v1/FactForge/sparql'

def keyId = "<your-credentials-here>"
def password = "<your-credentials-here>"

def client = new RESTClient(onlineServiceEndpoint)
client.auth.basic(keyId, password)

def builder ="PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
"PREFIX dbpedia: <http://dbpedia.org/resource/> "+
"PREFIX dbp-ont: <http://dbpedia.org/ontology/> "+
"PREFIX geo-ont: <http://www.geonames.org/ontology#> "+
"PREFIX umbel-sc: <http://umbel.org/umbel/sc/> "+
"SELECT DISTINCT ?Company ?Location WHERE {  "+
"?Company rdf:type dbp-ont:Company ; "+
"dbp-ont:industry dbpedia:Computer_software ; "+
"dbp-ont:foundationPlace ?Location . "+
"?Location geo-ont:parentFeature ?o. "+
"?o geo-ont:parentCountry dbpedia:United_States . } limit 5"
def query="query="+java.net.URLEncoder.encode(builder)

def headers=["Content-Type":"application/x-www-form-urlencoded","Accept":"application/sparql-results+xml"]

print(query)
print(headers)
def response = client.post(
	body : query.toString(),
	requestContentType : "application/x-www-form-urlencoded",  
	contentType : "application/sparql-results+xml") 
println response.getStatusLine()
println response.data.text
