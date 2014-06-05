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
	private static String endpointUrl = "https://text.s4.ontotext.com/";
	//The shop item which will be used for processing
	private static String shopItemId = "twitie";
	
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
		pr.setMimeType("text/plain");
		processRequest(pr, GATE_JSON);		
	}
	
	/**
	 * Process a tweet as an embedded document
	 * Request output as GATE JSON and set some custom annotation selectors
	 */
	private static void processTweet() {
		ProcessingRequest pr = new ProcessingRequest();
		pr.setDocument("" +
				"{    " +
				"\"created_at\": \"Tue Jan 21 10:09:50 +0000 2014\"," +
				"    \"id\": 425570928198561800," +
				"    \"id_str\": \"425570928198561792\"," +
				"    \"text\": \"halo @shafiqah5387 kami menjual Cord Holder edisi rilakkuma, korilakkuma dan kiiroitori. Harga Rp. 30.000/2pcs aja :) http://t.co/oOJRmipac1\"," +
				"    \"source\": \"web\"," +
				"    \"truncated\": false," +
				"    \"in_reply_to_status_id\": 425565523825266700," +
				"    \"in_reply_to_status_id_str\": \"425565523825266688\"," +
				"    \"in_reply_to_user_id\": 425061303," +
				"    \"in_reply_to_user_id_str\": \"425061303\"," +
				"    \"in_reply_to_screen_name\": \"shafiqah5387\"," +
				"    \"user\": {" +
				"        \"id\": 2275508352," +
				"        \"id_str\": \"2275508352\"," +
				"        \"name\": \"Eileen by adinda\"," +
				"        \"screen_name\": \"eileenbyadinda\"," +
				"        \"location\": \"Jakarta\"," +
				"        \"url\": \"http://eileenbyadinda.tumblr.com\"," +
				"        \"description\": \"Jual berbagai tas, hijab &gadget equipment import HK & Korea. Whatsapp / sms: Adinda - 085692273224. Thank you!\"," +
				"        \"protected\": false," +
				"        \"followers_count\": 24," +
				"        \"friends_count\": 60," +
				"        \"listed_count\": 0," +
				"        \"created_at\": \"Sat Jan 04 03:48:02 +0000 2014\"," +
				"        \"favourites_count\": 0," +
				"        \"utc_offset\": 25200," +
				"        \"time_zone\": \"Bangkok\"," +
				"        \"geo_enabled\": false," +
				"        \"verified\": false," +
				"        \"statuses_count\": 24," +
				"        \"lang\": \"en\"," +
				"        \"contributors_enabled\": false," +
				"        \"is_translator\": false," +
				"        \"profile_background_color\": \"C0DEED\"," +
				"        \"profile_background_image_url\": \"http://abs.twimg.com/images/themes/theme1/bg.png\"," +
				"        \"profile_background_image_url_https\": \"https://abs.twimg.com/images/themes/theme1/bg.png\"," +
				"        \"profile_background_tile\": false," +
				"        \"profile_image_url\": \"http://pbs.twimg.com/profile_images/419315801309450240/BV9KiP3P_normal.jpeg\"," +
				"        \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/419315801309450240/BV9KiP3P_normal.jpeg\"," +
				"        \"profile_link_color\": \"0084B4\"," +
				"        \"profile_sidebar_border_color\": \"C0DEED\"," +
				"        \"profile_sidebar_fill_color\": \"DDEEF6\"," +
				"        \"profile_text_color\": \"333333\"," +
				"        \"profile_use_background_image\": true," +
				"        \"default_profile\": true," +
				"        \"default_profile_image\": false," +
				"        \"following\": null," +
				"        \"follow_request_sent\": null," +
				"        \"notifications\": null" +
				"    }," +
				"    \"geo\": null," +
				"    \"coordinates\": null," +
				"    \"place\": null," +
				"    \"contributors\": null," +
				"    \"retweet_count\": 0," +
				"    \"favorite_count\": 0," +
				"    \"entities\": {" +
				"        \"hashtags\": []," +
				"        \"symbols\": []," +
				"        \"urls\": []," +
				"        \"user_mentions\": [" +
				"            {" +
				"                \"screen_name\": \"shafiqah5387\"," +
				"                \"name\": \"¦Shafiqah Ismail¦ ?\"," +
				"                \"id\": 425061303," +
				"                \"id_str\": \"425061303\"," +
				"                \"indices\": [" +
				"                    5," +
				"                    18" +
				"                ]" +
				"            }" +
				"        ]," +
				"        \"media\": [" +
				"            {" +
				"                \"id\": 425570928206950400," +
				"                \"id_str\": \"425570928206950400\"," +
				"                \"indices\": [" +
				"                    118," +
				"                    140" +
				"                ], " +
				"               \"media_url\": \"http://pbs.twimg.com/media/BefugSKCMAAGunF.jpg\"," +
				"                \"media_url_https\": \"https://pbs.twimg.com/media/BefugSKCMAAGunF.jpg\"," +
				"                \"url\": \"http://t.co/oOJRmipac1\"," +
				"                \"display_url\": \"pic.twitter.com/oOJRmipac1\"," +
				"                \"expanded_url\": \"http://twitter.com/eileenbyadinda/status/425570928198561792/photo/1\"," +
				"                \"type\": \"photo\"," +
				"                \"sizes\": {" +
				"                    \"small\": {" +
				"                        \"w\": 340," +
				"                        \"h\": 331," +
				"                        \"resize\": \"fit\"" +
				"                    }," +
				"                    \"large\": {" +
				"                        \"w\": 585," +
				"                        \"h\": 570," +
				"                        \"resize\": \"fit\"" +
				"                    }," +
				"                    \"thumb\": {" +
				"                        \"w\": 150," +
				"                        \"h\": 150," +
				"                        \"resize\": \"crop\"" +
				"                    }," +
				"                    \"medium\": {" +
				"                        \"w\": 585," +
				"                        \"h\": 570," +
				"                        \"resize\": \"fit\"" +
				"                    }" +
				"                }" +
				"            }" +
				"        ]" +
				"    }," +
				"    \"favorited\": false," +
				"    \"retweeted\": false," +
				"    \"possibly_sensitive\": false," +
				"    \"filter_level\": \"medium\"," +
				"    \"lang\": \"id\"" +
				"}");
		pr.setMimeType("text/x-json-twitter");
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
		pr.setMimeType("text/html");
		processRequest(pr, GATE_XML);		
	}


	/**
	 * Serialize a ProcessingRequest and send it to Self Service Semantic Suite Online Processing Service
	 * @param pr the processing request to send
	 * @param acceptType the type of output we want to produce
	 */
	private static void processRequest(ProcessingRequest pr, String acceptType) {
		HttpPost post = new HttpPost(endpointUrl + shopItemId);
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
