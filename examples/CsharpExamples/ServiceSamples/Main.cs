using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net;
using System.IO;


namespace ServiceSamples
{
    class MainClass
    {

        //API Keys
        //TODO set your own credentials generated from s4.ontotext.com
        private static String keyId = "<your-credentials-here>";
        private static String password = "<your-credentials-here>";

        private static S4ServiceClient apiExecutor;


        static void Main(string[] args)
        {
            apiExecutor = new S4ServiceClient("https://text.s4.ontotext.com/v1/twitie", keyId, password);

            //send a test GET request to the endpoint
            Console.Out.WriteLine("Testing endpoint...");
            if (!apiExecutor.testEndpoint())
            {
                return;
            }
            Console.Out.WriteLine("\n\n\n");

            //Process three different requests with different sets of options
            Console.Out.WriteLine("Processing inline document...");
            processInlineDocument();
            Console.Out.WriteLine("\n\n\n");

            Console.Out.WriteLine("Processing a remote document...");
            processRemoteDocument();
            Console.Out.WriteLine("\n\n\n");

            Console.Out.WriteLine("Processing a Tweet message...");
            processTweet();

            apiExecutor.EndpointUrl = "https://lod.s4.ontotext.com/v1/FactForge/sparql";

            processSparql();

            Console.Read();
        }

        /**
	    * Send a request with embedded plain text document,
	    * Request output as GATE JSON and use the defauls set of annotation selectors.
	    */
        private static void processInlineDocument()
        {
            ProcessingRequest pr = new ProcessingRequest();
            pr.document = ("Tiruchirappalli is the " +
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
            pr.documentType = ("text/plain");
            apiExecutor.processRequest(pr.toJSON(),S4ServiceClient.APPLICATION_JSON_HEADER,S4ServiceClient.APPLICATION_JSON_HEADER);
        }

        /**
	    * Process a tweet as an embedded document
	    * Request output as GATE JSON and set some custom annotation selectors
	    */
        private static void processTweet()
        {
            ProcessingRequest pr = new ProcessingRequest();
            pr.document = (
				"{\"text\":\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\","
				+ "\"lang\":\"en\",\"entities\":{\"symbols\":[],"
				+ "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\",\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\",\"url\":\"http://t.co/pK7t8AD7Xf\"}],"
				+ "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}],"
				+ "\"user_mentions\":[]},"
				+ "\"id\":502743846716207104,"
				+ "\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\","
				+ "\"id_str\":\"502743846716207104\"}");
            pr.documentType = ("text/x-json-twitter");
            pr.annotationSelectors = (new String[] { ":", "Original markups:" });
            apiExecutor.processRequest(pr.toJSON(), S4ServiceClient.APPLICATION_JSON_HEADER, S4ServiceClient.APPLICATION_JSON_HEADER);
        }

        /**
        * Process an HTML document by supplying its publicly accessible URL
        * Request output as GATE XML and use the default set of annotation selectors.
        */
        private static void processRemoteDocument()
        {
            ProcessingRequest pr = new ProcessingRequest();
            pr.documentUrl = ("http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car");
            pr.documentType = ("text/html");
            apiExecutor.processRequest(pr.toJSON(), S4ServiceClient.APPLICATION_JSON_HEADER, S4ServiceClient.APPLICATION_JSON_HEADER);
        }

        /**
        * Executes one SPARQL query 
        */
        private static String processSparql()
        {
            String query="PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
           + "PREFIX dbpedia: <http://dbpedia.org/resource/>\n"
           + "PREFIX dbp-ont: <http://dbpedia.org/ontology/>\n"
           + "PREFIX geo-ont: <http://www.geonames.org/ontology#>\n"
           + "PREFIX umbel-sc: <http://umbel.org/umbel/sc/>\n\n"
           + "SELECT DISTINCT ?Company ?Location\nWHERE {\n"
           + "    ?Company rdf:type dbp-ont:Company ;\n"
           + "             dbp-ont:industry dbpedia:Computer_software ;\n"
           + "             dbp-ont:foundationPlace ?Location .\n"
           + "    ?Location geo-ont:parentFeature ?o.\n"
           + "    ?o geo-ont:parentCountry dbpedia:United_States .\n} limit 5";
            query = System.Web.HttpUtility.UrlEncode(query, Encoding.UTF8);
            return apiExecutor.processRequest("query=" + query, S4ServiceClient.SPARQL_ACCEPT_HEADER, S4ServiceClient.SPARQL_CONTENT_TYPE);
        }
    }
}
