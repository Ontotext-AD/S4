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
        private const String GATE_XML = "application/gate+xml";
        private const String GATE_JSON = "application/gate+json";

        //The URL of the Self Service Semantic Suite Online Processing Service Endpoint
        private static String endpointUrl = "https://text.s4.ontotext.com/v1/";
        //The shop item which will be used for processing
        private static String serviceId = "twitie";

        //API Keys
        //TODO set your own credentials generated from s4.ontotext.com
        private static String keyId = "s4llp1f39mp4";
        private static String password = "6363uuc478kd05m";


        private static NetworkCredential nc;


        static void Main(string[] args)
        {
            //setup authentication
            setupContext();

            //send a test GET request to the endpoint
            Console.Out.WriteLine("Testing endpoint...");
            if (!testEndpoint())
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

            Console.Read();
        }

        /**
         * Sets up a HTTPContext for authentication
         */
        private static void setupContext()
        {
            // Call the constructor  to create an instance of NetworkCredential with the  
            // specified key id and password.
            nc = new NetworkCredential(keyId, password);
        }

        /**
         * Sends a test request to the service
         * @return if the service is available
         */
        private static bool testEndpoint()
        {
            // Create a WebRequest with the specified URL. 
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(endpointUrl);
            request.Credentials = nc;
            Console.WriteLine("\n\nRequest to Url is sent.Waiting for response...");

            // Send the request and wait for a response.
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            int statusCode = (int)response.StatusCode;
            if (statusCode != 200)
            {
                Console.WriteLine("Error communicating with endpoint.");
                Console.WriteLine("Status code: " + (int)response.StatusCode + " " + response.StatusDescription);
                Console.WriteLine(response.Headers);
                Console.WriteLine(getContent(response));
                response.Close();
                return false;
            }
            else
            {
                Console.WriteLine("Endpoint returned status SUCCESS.");
                Console.WriteLine("Status code: " + (int)response.StatusCode + " " + response.StatusDescription);
                Console.WriteLine(response.Headers);
                Console.WriteLine("Response body: ");
                Console.WriteLine(getContent(response));
                response.Close();
                return true;
            }
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
            processRequest(pr, GATE_JSON);
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
            processRequest(pr, GATE_JSON);
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
            processRequest(pr, GATE_XML);
        }

        /**
         * Serialize a ProcessingRequest and send it to Self Service Semantic Suite Online Processing Service
         * @param pr the processing request to send
         * @param acceptType the type of output we want to produce
         */
        private static void processRequest(ProcessingRequest pr, string acceptType)
        {

            // Create a WebRequest with the specified URL. 
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(endpointUrl + serviceId);
            
            //set gzip decompression
            request.AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate;
            
            //set credentials
            request.Credentials = nc;

            // Set the content type of the data being posted.
            request.ContentType = "application/json";
            request.Accept = acceptType;
            request.Headers.Set("Accept-Encoding", "gzip");

            // Set the 'Method' property of the 'HttpWebRequest' to 'POST'.
            request.Method = "POST";

            String postData = pr.toJSON();
            UTF8Encoding encoding = new UTF8Encoding();
            byte[] byte1 = encoding.GetBytes(postData);

            Console.WriteLine("POST body is:");
            Console.WriteLine(postData);

            // Set the content length of the string being posted.
            request.ContentLength = byte1.Length;

            Stream RequestStream = request.GetRequestStream();

            RequestStream.Write(byte1, 0, byte1.Length);

            // Close the Stream object.
            RequestStream.Close();
            // Send the request and wait for a response.
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            //Console.WriteLine(getContent(myHttpWebResponse));

            int statusCode = (int)response.StatusCode;
            switch (statusCode)
            {
                case 200:
                    {
                        //Request was processed successfully
                        Console.WriteLine("SUCCESS");
                        Console.WriteLine("Status code: " + (int)response.StatusCode + " " + response.StatusDescription);
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 400:
                    {
                        //Bad request, there is some problem with user input
                        Console.WriteLine("Bad request");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 403:
                    {
                        //Problem with user authentication
                        Console.WriteLine("Error during authentication");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 404:
                    {
                        //Not found
                        Console.WriteLine("Not found, check endpoint URL");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 406:
                    {
                        //Not Accepted
                        Console.WriteLine("The request was not accepted. Check Accept header");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 408:
                    {
                        //Processing this request took too long
                        Console.WriteLine("Could not process document in time");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 415:
                    {
                        //Unsupported media type
                        Console.WriteLine("Invalid value in Content-Type header");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                case 500:
                    {
                        //Internal server error
                        Console.WriteLine("Error during processing");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
                default:
                    {
                        Console.WriteLine("Could not process request");
                        Console.WriteLine(response.Headers);
                        Console.WriteLine(getContent(response));
                        break;
                    }
            }
        }

        /**
	     * Helper method which collects the response's body as a strting
	     * @param response the HttpResponse whose content we want to collect
	     * @return the String value of the response body
	     */
        private static String getContent(HttpWebResponse response)
        {
            StreamReader sr = new StreamReader(response.GetResponseStream(),
            Encoding.UTF8);
            String html = sr.ReadToEnd();
            return html;
        }
    }
}
