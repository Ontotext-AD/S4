import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import model.ProcessingRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Main {

	private static final String GATE_XML = "application/gate+xml";
	private static final String GATE_JSON = "application/gate+json";
	
	//The URL of the Self Service Semantic Suite Online Processing Service Endpoint
	private static String endpointUrl = "https://text.s4.ontotext.com/v1/";
	//The service which will be used for processing
	private static String serviceId = "twitie";
	
	//API Keys
	//TODO set your own credentials generated from s4.ontotext.com
	private static String keyId = "<your-credentials-here>";
	private static String password = "<your-credentials-here>";
	
		
	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	private static HttpClientContext ctx;


	public static void main(String[] args) {
		//setup authentication
		setupContext();
		
		//send a test GET request to the endpoint
		System.out.println("Testing endpoint...");
		if(!testEndpoint()) {
			return;
		}
		System.out.println("\n\n\n");
		
		//Process three different requests with different sets of options
		System.out.println("Processing inline document...");
		processInlineDocument();
		System.out.println("\n\n\n");
		System.out.println("Processing a remote document...");
		processRemoteDocument();
		System.out.println("\n\n\n");
		System.out.println("Processing a Tweet message...");
		processTweet();
		
	}
	
	/**
	 * Sets up a HTTPContext for authentication
	 */
	private static void setupContext() {
		ctx = HttpClientContext.create();
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(keyId, password);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, creds);
		ctx.setCredentialsProvider(credsProvider);
	}

	/**
	 * Sends a test request to the service
	 * @return if the service is available
	 */
	private static boolean testEndpoint() {
		HttpGet get = new HttpGet(endpointUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get, ctx);
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			if(statusCode != 200) {
				System.out.println("Error communicating with endpoint.");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				return false;
			} else {
				System.out.println("Endpoint returned status SUCCESS.");
				System.out.println(response.toString());
				System.out.println("Response body: ");
				System.out.println(getContent(response));
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {}
		}
		return false;
	}

	/**
	 * Send a request with embedded plain text document,
	 * Request output as GATE JSON and use the defauls set of annotation selectors.
	 */
	private static void processInlineDocument() {

		ProcessingRequest pr = new ProcessingRequest();
		pr.setDocument("Tiruchirappalli is the " +
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
				"Institute of Technology.");
		pr.setDocumentType("text/plain");
		processRequest(pr, GATE_JSON);		
	}
	
	/**
	 * Process a tweet as an embedded document
	 * Request output as GATE JSON and set some custom annotation selectors
	 */
	private static void processTweet() {
		ProcessingRequest pr = new ProcessingRequest();
		pr.setDocument(
			"{\"text\":\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\","
			+ "\"lang\":\"en\",\"entities\":{\"symbols\":[],"
			+ "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\",\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\",\"url\":\"http://t.co/pK7t8AD7Xf\"}],"
			+ "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}],"
			+ "\"user_mentions\":[]},"
			+ "\"id\":502743846716207104,"
			+ "\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\","
			+ "\"id_str\":\"502743846716207104\"}");
		pr.setDocumentType("text/x-json-twitter");
		pr.setAnnotationSelectors(new String[]{":", "Original markups:"});
		processRequest(pr, GATE_JSON);		
	}

	/**
	 * Process an HTML document by supplying its publicly accessible URL
	 * Request output as GATE XML and use the default set of annotation selectors.
	 */
	private static void processRemoteDocument() {
		ProcessingRequest pr = new ProcessingRequest();
		pr.setDocumentUrl("http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car");
		pr.setDocumentType("text/html");
		processRequest(pr, GATE_JSON);		
	}


	/**
	 * Serialize a ProcessingRequest and send it to Self Service Semantic Suite Online Processing Service
	 * @param pr the processing request to send
	 * @param acceptType the type of output we want to produce
	 */
	private static void processRequest(ProcessingRequest pr, String acceptType) {
		HttpPost post = new HttpPost(endpointUrl + serviceId);
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", acceptType);
		post.setHeader("Accept-Encoding", "gzip");
		
		String message = null;
		try {
			message = pr.toJSON();			
		} catch (IOException e) {			
			System.out.println("Error generating JSON");
			e.printStackTrace();
			return;
		}
		
		System.out.println("POST body is:");
		System.out.println(message);
		
		post.setEntity(new StringEntity(message, Charset.forName("UTF-8")));

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post, ctx);
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			switch(statusCode) {
			case 200 : {
				//Request was processed successfully
				System.out.println("SUCCESS");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 400 : {
				//Bad request, there is some problem with user input
				System.out.println("Bad request");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 403 : {
				//Problem with user authentication
				System.out.println("Error during authentication");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 404 : {
				//Not found
				System.out.println("Not found, check endpoint URL");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 406 : {
				//Not Accepted
				System.out.println("The request was not accepted. Check Accept header");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 408 : {
				//Processing this request took too long
				System.out.println("Could not process document in time");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 415 : {
				//Unsupported media type
				System.out.println("Invalid value in Content-Type header");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 500 : {
				//Internal server error
				System.out.println("Error during processing");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}			
			default : {
				System.out.println("Could not process request");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {}
		}
	}
	
	/**
	 * Helper method which collects the response's body as a string
	 * @param response the HttpResponse whose content we want to collect
	 * @return the String value of the response body
	 * @throws IllegalStateException 
	 * @throws IOException
	 */
	private static String getContent(HttpResponse response) throws IllegalStateException, IOException{
		InputStream content = response.getEntity().getContent();
		StringWriter sw = new StringWriter();
		IOUtils.copy(content, sw, "UTF-8");
		return sw.toString();
	}
}
