/*
 * S4 C# client library
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
using System.Net;
using System.IO;
using System.Net.Http;
using System.Json;
using System.Xml.Serialization;
using Ontotext.S4.client;
using Ontotext.S4.service;
using System.IO.Compression;
using Newtonsoft.Json;
using System.Net.Http.Headers;
using System.Xml;
using System.Web.Script.Serialization;

namespace Ontotext.S4.client
{
    /// <summary>
    /// Client responsible for communication with the S4 API.Handles authentication, serialization and deserialization of JSON request and response bodies.
    /// </summary>
    public class HttpClient
    {
        /// <summary>
        /// The standard base URI for the S4 API.
        /// </summary>
        public static Uri DEFAULT_BASE_URL;

        /// <summary>
        /// The HTTP basic authentication header that will be appended to all requests.
        /// </summary>
        private static NetworkCredential nc;

        /// <summary>
        /// The base URL that will be used to resolve any relative request URIs.
        /// </summary>
        private Uri baseUrl;

        /// <summary>
        /// Create a client that uses the {@link #DEFAULT_BASE_URL default base URL}. 
        /// </summary>
        /// <param name="apiKey">API key identifier for authentication</param>
        /// <param name="keySecret">API key password</param>
        public HttpClient(String apiKey, String keySecret)
            : this(DEFAULT_BASE_URL, apiKey, keySecret)
        {
        }

        /// <summary>
        /// Create a client using a specified base URL (for advanced use only - the default URL will work for all normal cases).
        /// </summary>
        /// <param name="url">API base URL</param>
        /// <param name="apiKey">API key identifier for authentication</param>
        /// <param name="keySecret">API key password</param>
        public HttpClient(Uri url, String apiKey, String keySecret)
        {
            try
            {
                DEFAULT_BASE_URL = new Uri("https://text.s4.ontotext.com/");
            }
            catch (Exception e)
            {
                // can't happen
                throw new InvalidOperationException(e.Message);
            }
            baseUrl = url;
            try
            {
                // HTTP header is "Basic base64(username:password)"
                nc = new NetworkCredential(apiKey, keySecret);
            }
            catch (Exception e)
            {
                // should never happen
                throw new Exception("Claims not to support UTF-8 encoding..." + e.Message);
            }
        }


        public Uri getBaseUrl()
        {
            return baseUrl;
        }

        /// <summary>
        /// Make an API request and parse the JSON response into a new object.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="target">the URL to request (relative URLs will resolve against the {@link #getBaseUrl() base URL}).</param>
        /// <param name="method">the request method (GET, POST, DELETE, etc.)</param>
        /// <param name="responseType"></param>
        /// <param name="requestBody">the object that should be serialized to JSON as  the request body. If <code>null</code> no request body is sent</param>
        /// <param name="extraHeaders">any additional HTTP headers, specified as an alternating sequence of header names and values</param>
        /// <returns>for a successful response, the deserialized response body, or <code>null</code> for a 201 response</returns>
        public T request<T>(String target, String method, T responseType, ServiceRequest requestBody, Dictionary<String, String> extraHeaders)
        {
            try
            {
                HttpWebResponse connection = sendRequest(target, method, requestBody, extraHeaders);
                return readResponseOrError(connection, responseType);
            }
            catch (IOException e)
            {
                throw new HttpClientException(e.Message);
            }
        }

        /// <summary>
        /// Make an API request and return the raw data from the response as an InputStream.
        /// </summary>
        /// <param name="target">the URL to request (relative URLs will resolve against the {@link #getBaseUrl() base URL}).</param>
        /// <param name="method">the request method (GET, POST, DELETE, etc.)</param>
        /// <param name="requestBody">the object that should be serialized to JSON as the request body. If <code>null</code> no request body is sent</param>
        /// <param name="extraHeaders">any additional HTTP headers, specified as an alternating sequence of header names and values</param>
        /// <returns>for a successful response, the response stream, or <code>null</code> for a 201 response</returns>
        public Stream requestForStream(String target, String method, ServiceRequest requestBody, Dictionary<String, String> extraHeaders)
        {
            try
            {
                HttpWebResponse connection = sendRequest(target, method, requestBody, extraHeaders);
                HttpStatusCode responseCode = connection.StatusCode;
                if (responseCode.Equals(HttpStatusCode.NoContent))
                {
                    // successful response with no content
                    return null;
                }
                else if ((int)responseCode >= 400)
                {
                    readError(connection);
                    return null; // not reachable, readError always throws exception
                }
                else if ((int)responseCode >= 300)
                {
                    // redirect - all redirects we care about from the S4
                    // APIs are 303. We have to follow them manually to make
                    // authentication work properly.
                    String location = connection.Headers.Get("Location");
                    // consume body
                    Stream stream = connection.GetResponseStream();
                    stream.SetLength(0);

                    stream.Close();
                    // follow the redirect
                    return requestForStream(location, method, requestBody, extraHeaders);
                }
                else
                {
                    return connection.GetResponseStream();
                }
            }
            catch (Exception e)
            {
                throw new HttpClientException(e.Message);
            }

        }
        /// <summary>
        /// Handles the sending side of an HTTP request, returning a connection from which the response (or error) can be read.
        /// </summary>
        /// <param name="target"></param>
        /// <param name="method"></param>
        /// <param name="requestBody"></param>
        /// <param name="extraHeaders"></param>
        /// <returns></returns>
        private HttpWebResponse sendRequest(String target, String method, ServiceRequest requestBody, Dictionary<String, String> extraHeaders)
        {
            Uri requestUrl = new Uri(baseUrl, target);
            HttpWebRequest connection = (HttpWebRequest)WebRequest.Create(requestUrl);

            connection.Method = method;
            connection.AllowAutoRedirect = false;
            connection.Credentials = nc;
            

            if (extraHeaders != null)
            {
                foreach (KeyValuePair<string, string> entry in extraHeaders)
                {
                    if (entry.Key.Contains("Accept"))
                    {
                        connection.Accept = entry.Value;
                        continue;
                    }
                    connection.Headers.Add(entry.Key, entry.Value);
                }
            }

            if (requestBody != null)
            {
                connection.ContentType = "application/json";
                MemoryStream output = new MemoryStream();
                try
                {
                    var serializer = new JavaScriptSerializer();
                    var serializedResult = serializer.Serialize(requestBody);

                    String postData = serializedResult;
                    UTF8Encoding encoding = new UTF8Encoding();
                    byte[] byte1 = encoding.GetBytes(postData);

                    // Set the content length of the string being posted.
                    connection.ContentLength = byte1.Length;

                    Stream RequestStream = connection.GetRequestStream();

                    RequestStream.Write(byte1, 0, byte1.Length);

                    // Close the Stream object.
                    RequestStream.Close();
                }
                finally
                {
                    output.Close();
                }
            }

            HttpWebResponse response = null;
            try
            {
                response = (HttpWebResponse)connection.GetResponse();
            }
            catch (WebException we)
            {
                Console.WriteLine(we.Message);
            }
            return response;
        }

        /// <summary>
        /// Read a response or error message from the given connection, handling any 303 redirect responses.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="connection"></param>
        /// <param name="responseType"></param>
        /// <returns></returns>
        private T readResponseOrError<T>(HttpWebResponse connection, T responseType)
        {
            return readResponseOrError(connection, responseType, true);
        }

        /// <summary>
        /// Read a response or error message from the given connection, handling any 303 redirect responses
        /// if <code>followRedirects</code> is true.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="connection"></param>
        /// <param name="responseType"></param>
        /// <param name="followRedirects"></param>
        /// <returns></returns>
        private T readResponseOrError<T>(HttpWebResponse connection, T responseType, Boolean followRedirects)
        {
            StreamReader stream = null;
            try
            {
                HttpStatusCode responseCode = connection.StatusCode;
                if (responseCode == HttpStatusCode.NoContent)
                {
                    // successful response with no content
                    return default(T);
                }
                String encoding = connection.ContentEncoding;
                if ("gzip".Equals(encoding))
                {
                    stream = new StreamReader(new GZipStream(connection.GetResponseStream(), CompressionLevel.Fastest));
                }
                else
                {
                    stream = new StreamReader(connection.GetResponseStream());
                }

                if ((int)responseCode < 300 || (int)responseCode >= 400 || !followRedirects)
                {
                    try
                    {
                        //return MAPPER.readValue(stream, responseType);
                        return JsonConvert.DeserializeObject<T>(stream.ReadToEnd());
                    }
                    finally
                    {
                        stream.Close();
                    }
                }
                else
                {
                    // redirect - all redirects we care about from the S4
                    // APIs are 303. We have to follow them manually to make
                    // authentication work properly.
                    String location = connection.Headers.Get("Location");
                    // consume body
                    // follow the redirect
                    return get(location, responseType);
                }
            }
            catch (Exception e)
            {
                Console.Out.WriteLine(e.Message);
                readError(connection);
                return default(T); // unreachable, as readError always throws exception
            }
        }

        /// <summary>
        /// Read a response or error message from the given connection, and update the state of the given object.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="connection"></param>
        /// <param name="responseObject"></param>
        private void readResponseOrErrorForUpdate<T>(HttpWebResponse connection, T responseObject)
        {
            StreamReader stream = null;
            try
            {
                if (connection.StatusCode == HttpStatusCode.NoContent)
                {
                    // successful response with no content
                    return;
                }
                stream = new StreamReader(connection.GetResponseStream());
                try
                {

                    JsonConvert.DeserializeObject<T>(stream.ReadToEnd());
                }
                finally
                {
                    stream.Close();
                }
            }
            catch (Exception e)
            {
                readError(connection);
                Console.WriteLine(e.Message);
            }
        }

        /// <summary>
        /// Read an error response from the given connection and throw a suitable {@link HttpClientException}.
        /// This method always throws an exception, it will never return normally.
        /// </summary>
        /// <param name="connection"></param>
        private void readError(HttpWebResponse connection)
        {
            Stream stream;
            try
            {
                String encoding = connection.ContentEncoding;
                if ("gzip".Equals(encoding.ToLower()))
                {
                    stream = new GZipStream(connection.GetResponseStream(), CompressionLevel.NoCompression);
                }
                else
                {
                    stream = connection.GetResponseStream();
                }

                StreamReader reader = new StreamReader(stream, Encoding.UTF8);

                try
                {
                    JsonObject errorNode = null;
                    XmlReader xml = null;
                    if (connection.ContentType.Contains("json"))
                    {
                        errorNode = JsonConvert.DeserializeObject<JsonObject>((string)reader.ReadToEnd());
                    }
                    else if (connection.ContentType.Contains("xml"))
                    {
                        xml = XmlReader.Create((string)reader.ReadToEnd());
                    }

                    throw new HttpClientException("Server returned response code " + connection.StatusCode);
                }
                finally
                {
                    reader.Close();
                }
            }
            catch (HttpClientException httpe)
            {
                Console.WriteLine(httpe.Message);
                throw httpe;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw e;
            }
        }

        /// <summary>
        /// Perform an HTTP GET request, parsing the JSON response to create a new object.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="target">the URL to request (relative URLs will resolve against the {@link #getBaseUrl() base URL}).</param>
        /// <param name="responseType">the Java type corresponding to a successful response message for this URL</param>
        /// <returns>for a successful response, the deserialized response body, or <code>null</code> for a 201 response</returns>
        public T get<T>(String target, T responseType)
        {
            return request(target, "GET", responseType, null, null);
        }
    }
}
