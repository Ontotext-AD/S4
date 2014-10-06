/*
 * Copyright (c) 2014
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    class S4ServiceClient
    {
        public const String SPARQL_ACCEPT_HEADER="application/sparql-results+json";
        public const String SPARQL_CONTENT_TYPE = "application/x-www-form-urlencoded";
        public const String APPLICATION_JSON_HEADER = "application/json";
      
        //The URL of the Self Service Semantic Suite Online LOD Server Endpoint
        private String endpointUrl;

        public String EndpointUrl
        {
            get { return endpointUrl; }
            set { endpointUrl = value; }
        }

        //API Keys
        //TODO set your own credentials generated from s4.ontotext.com
        private String keyId;
        private String password;


        private static NetworkCredential nc;

        public S4ServiceClient(String endpointURL, String keyId, String password)
        {
            this.endpointUrl = endpointURL;
            this.keyId = keyId;
            this.password = password;
            setupContext();
        }



        /**
        * Sets up a HTTPContext for authentication
        */
        private void setupContext()
        {
            // Call the constructor  to create an instance of NetworkCredential with the  
            // specified key id and password.
            nc = new NetworkCredential(keyId, password);
        }

        /**
        * Sends a test request to the service
        * @return if the service is available
        */
        public bool testEndpoint()
        {
            // Create a WebRequest with the specified URL. 
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(endpointUrl.Substring(0,endpointUrl.LastIndexOf("/")));
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
        * Serialize a ProcessingRequest and send it to Self Service Semantic Suite Online Processing Service
        * @param pr the processing request to send
        * @param acceptType the type of output we want to produce
        */
        public String processRequest(String body, string acceptType,String contentType)
        {

            // Create a WebRequest with the specified URL. 
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(endpointUrl);

            //set gzip decompression
            request.AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate;

            //set credentials
            request.Credentials = nc;

            // Set the content type of the data being posted.
            request.ContentType = contentType;
            request.Accept = acceptType;
            request.Headers.Set("Accept-Encoding", "gzip");

            // Set the 'Method' property of the 'HttpWebRequest' to 'POST'.
            request.Method = "POST";

            String postData = body;
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
                        return getContent(response);
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
            return null;
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
