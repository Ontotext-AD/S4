using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net;
using System.IO;


namespace SynchServiceSamples
{
    class MainClass
    {
        private const String GATE_XML = "application/gate+xml";
        private const String GATE_JSON = "application/gate+json";

        //The URL of the Self Service Semantic Suite Online Processing Service Endpoint
        private static String endpointUrl = "https://s4.ontotext.com/online-processing/item/";
        //The shop item which will be used for processing
        private static String shopItemId = "2";

        //API Keys
        //TODO set your own credentials generated from s4.ontotext.com
        private static String keyId = "<your-credentials-here>";
        private static String password = "<your-credentials-here>";


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
            pr.mimeType = ("text/plain");
            processRequest(pr, GATE_JSON);
        }

        /**
	    * Process a tweet as an embedded document
	    * Request output as GATE JSON and set some custom annotation selectors
	    */
        private static void processTweet()
        {
            ProcessingRequest pr = new ProcessingRequest();
            pr.document = ("" +
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
            pr.mimeType = ("text/x-json-twitter");
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
            pr.mimeType = ("text/html");
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
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(endpointUrl + shopItemId);
            request.Credentials = nc;
            // Console.WriteLine("\n\nRequest to Url is sent.Waiting for response...");
            //HttpWebRequest myHttpWebRequest = (HttpWebRequest)myWebRequest;

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
