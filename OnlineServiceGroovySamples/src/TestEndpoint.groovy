@Grab(group='org.codehaus.groovy.modules.http-builder',
module='http-builder', version='0.5.2' )
import groovyx.net.http.*
import org.apache.http.entity.FileEntity
import org.apache.http.util.EntityUtils

def onlineServiceEndpoint = 'https://s4.ontotext.com/online-processing/item'

def keyId = '<your-credentials-here>'
def password = '<your-credentials-here>'

def client = new RESTClient(onlineServiceEndpoint)
client.auth.basic(keyId, password)
def response = client.get([:])
println response.getStatusLine()
println response.getData()