/*
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
    class MainClass
    {
        private static String apiKey = "<s4-api-key>";
        private static String keySecret = "<s4-key-secret>";
        
        public static void Main(string[] args)
        {
            // Process inline document
            Console.WriteLine(processInlineDocument("<your-text-here>"));

            // Process remote HTML document
            Console.WriteLine(processRemoteDocument("<document-url-here>"));

            // Process images from remote HTML document
            Console.WriteLine(processImages("<document-url-here>"));

            // Process Tweets
            String tweet = "<tweet-here>";
            Console.WriteLine(processTweet(tweet));

            String query = "SELECT * WHERE { ?S ?P ?O } LIMIT 10";
            String update = "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + "\r\n" + "INSERT DATA { <http://example/egbook> dc:title \"This is an example title\"}";

            String userID = "<user-id>";
            String dbName = "<db-id>";
            String repo = "<repo-name>";

            // Access the Knowledge Graph
            Console.WriteLine(sparqlSelectFromKnowledgeGraph(query));

            // SPARQL SELECT (GDBaaS)
            Console.WriteLine(sparqlSelectFromGDBaaS(query, userID, dbName, repo));
            
            // SPARQL UPDATE (GDBaaS)
            sparqlUpdate(update, userID, dbName, repo);

            // Create new repo in GDBaaS
            String newRepo = "<repo-name>";
            String config = "{\"repositoryID\": \"" + newRepo + "\", \"label\": \"Description of my repository\", \"ruleset\": \"owl-horst-optimized\"}";
            Console.WriteLine(createRepository(config, userID, dbName, newRepo));
            
            Console.Read();
        }

        public static String processTweet(String tweet)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/json";
                client.Headers[HttpRequestHeader.Accept] = "application/json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://text.s4.ontotext.com/v1/twitie", "POST", "{\"document\": \"" + tweet + "\", \"documentType\": \"text/x-json-twitter\"}");
            }
            return result;
        }

        public static String processInlineDocument(String text)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/json";
                client.Headers[HttpRequestHeader.Accept] = "application/json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://text.s4.ontotext.com/v1/news", "POST", "{\"document\": \"" + text + "\", \"documentType\": \"text/plain\"}");
            }
            return result;
        }

        public static String processRemoteDocument(String documentURL)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/json";
                client.Headers[HttpRequestHeader.Accept] = "application/json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://text.s4.ontotext.com/v1/news", "POST", "{\"documentUrl\": \"" + documentURL + "\", \"documentType\": \"text/html\"}");
            }
            return result;
        }

        public static String processImages(String documentURL)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/json";
                client.Headers[HttpRequestHeader.Accept] = "application/json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://text.s4.ontotext.com/v1/news", "POST", "{\"documentUrl\": \"" + documentURL + "\"," +
                    "\"documentType\": \"text/html\", \"imageTagging\": true, \"imageCategorization\": true}");
            }
            return result;
        }
        
        public static String sparqlSelectFromKnowledgeGraph(String query)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/x-www-form-urlencoded";
                client.Headers[HttpRequestHeader.Accept] = "application/sparql-results+json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://lod.s4.ontotext.com/v1/FactForge/sparql", "POST", "query=" + query);
            }
            return result;
        }

        public static String createRepository(String config, String userID, String databaseID, String newRepoName)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/json";
                client.Headers[HttpRequestHeader.Accept] = "application/json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://rdf.s4.ontotext.com/" + userID + "/" + databaseID + "/repositories/" + newRepoName, "PUT", config);
            }
            return result;
        }

        public static String sparqlSelectFromGDBaaS(String query, String userID, String databaseID, String repoName)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/x-www-form-urlencoded";
                client.Headers[HttpRequestHeader.Accept] = "application/sparql-results+json";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://rdf.s4.ontotext.com/" + userID + "/" + databaseID + "/repositories/" + repoName, "POST", "query=" + query);
            }
            return result;
        }

        public static String sparqlUpdate(String update, String userID, String databaseID, String repoName)
        {
            String result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/x-www-form-urlencoded";
                String credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes(apiKey + ":" + keySecret));
                client.Headers[HttpRequestHeader.Authorization] = "Basic " + credentials;
                result = client.UploadString("https://rdf.s4.ontotext.com/" + userID + "/" + databaseID + "/repositories/" + repoName + "/statements", "POST", "update=" + update);
            }
            return result;
        }
    }
}
