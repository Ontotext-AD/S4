import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import s4.RESTService.APIRequestExecutor;
import tableCreator.TableCreator;
import model.ProcessingRequest;

public class Main {

	private static APIRequestExecutor apiExecutor;
	private static String keyId = "<your-credentials-here>";
	private static String password = "<your-credentials-here>";
	
	public static void main(String[] args) {
		apiExecutor=new APIRequestExecutor("https://text.s4.ontotext.com/v1/twitie", keyId, password);
		//send a test GET request to the endpoint
		System.out.println("Testing endpoint...");
		if(!apiExecutor.testEndpoint()) {
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
		
		
		
		//change URL to SPARQL end-point
		apiExecutor.setEndpointUrl("https://lod.s4.ontotext.com/v1/FactForge/sparql");
		
		System.out.println("\n\n\n");		
		//Software companies founded in the US
		processSparql();
				
		
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
		try {
			apiExecutor.processRequest(pr.toJSON(), APIRequestExecutor.APPLICATION_JSON_HEADER,APIRequestExecutor.APPLICATION_JSON_HEADER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
		try {
			apiExecutor.processRequest(pr.toJSON(), APIRequestExecutor.APPLICATION_JSON_HEADER,APIRequestExecutor.APPLICATION_JSON_HEADER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	/**
	 * Process an HTML document by supplying its publicly accessible URL
	 * Request output as GATE XML and use the default set of annotation selectors.
	 */
	private static void processRemoteDocument() {
		ProcessingRequest pr = new ProcessingRequest();
		pr.setDocumentUrl("http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car");
		pr.setDocumentType("text/html");
		try {
			apiExecutor.processRequest(pr.toJSON(), APIRequestExecutor.APPLICATION_JSON_HEADER,APIRequestExecutor.APPLICATION_JSON_HEADER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	/**
	 * Executes one SPARQL query
	 * @param query 
	 * @return
	 */
	private static void processSparql() {
		// prepare body
		String query=null;
		try {
			 query = URLEncoder.encode("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
					+ "PREFIX dbpedia: <http://dbpedia.org/resource/>\n"
					+ "PREFIX dbp-ont: <http://dbpedia.org/ontology/>\n"
					+ "PREFIX geo-ont: <http://www.geonames.org/ontology#>\n"
					+ "PREFIX umbel-sc: <http://umbel.org/umbel/sc/>\n\n"
					+ "SELECT DISTINCT ?Company ?Location\nWHERE {\n"
					+ "    ?Company rdf:type dbp-ont:Company ;\n"
					+ "             dbp-ont:industry dbpedia:Computer_software ;\n"
					+ "             dbp-ont:foundationPlace ?Location .\n"
					+ "    ?Location geo-ont:parentFeature ?o.\n"
					+ "    ?o geo-ont:parentCountry dbpedia:United_States .\n}", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String response= apiExecutor.processRequest("query=" + query,APIRequestExecutor.SPARQL_JSON_HEADER,APIRequestExecutor.SPARQL_URLENCODED);
		System.out.println(TableCreator.createTableFromJSON(response));
	}
	
	
	
}
