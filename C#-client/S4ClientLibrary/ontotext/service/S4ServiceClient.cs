/*
 * S4 C# client library
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Data;
using System.Json;
using System.Runtime.InteropServices;

using Ontotext.S4.client;
using Ontotext.S4.catalog;
using Ontotext.S4.service;
using Ontotext.S4.common;

using Newtonsoft.Json;
using System.Net;


namespace Ontotext.S4.service
{
    /// <summary>
    /// 
    /// </summary>
    public class S4ServiceClient
    {
        private HttpClient client;

        /// <summary>
        /// Construct a <code>S4ServiceClient</code> accessing a specific processing pipeline on the s4.ontotext.com platform using the given credentials.
        /// </summary>
        /// <param name="item">the {@link ServiceDescriptor} which represents the processing pipeline which will be used</param>
        /// <param name="apiKeyId">API key ID for authentication</param>
        /// <param name="apiPassword">corresponding password</param>
        public S4ServiceClient(ServiceDescriptor item, String apiKeyId, String apiPassword)
        {
            Uri endpoint;
            try
            {
                endpoint = new Uri(item.onlineUrl);
            }
            catch (Exception e)
            {
                throw new ArgumentException(
                        "Invalid ServiceDescriptor specified. No API endpoint specified." + e.Message);
            }
            this.client = new HttpClient(endpoint, apiKeyId, apiPassword);
        }

        /// <summary>
        /// Construct a <code>S4ServiceClient</code> accessing a specific processing
        /// pipeline on the s4.ontotext.com platform using the given
        /// credentials.
        /// </summary>
        /// <param name="endpoint">the URL of the pipeline which will be used for processing</param>
        /// <param name="apiKeyId">apiKeyId API key ID for authentication</param>
        /// <param name="apiPassword">corresponding password</param>
        public S4ServiceClient(Uri endpoint, String apiKeyId, String apiPassword)
        {
            this.client = new HttpClient(endpoint, apiKeyId, apiPassword);
        }

        /// <summary>
        /// Annotates a single document with the specified MIME type. Returns
        /// an object which allows for convenient access to the annotations in
        /// the annotated document.
        /// </summary>
        /// <param name="documentText">the document content to annotate</param>
        /// <param name="documentMimeType"> the MIME type of the document which will beannotated</param>
        /// <returns>an {@link AnnotatedDocument} containing the original content as well as the annotations produced</returns>
        public AnnotatedDocument annotateDocument(String documentText,
                SupportedMimeType documentMimeType)
        {
            ServiceRequest rq =
                    new ServiceRequest(documentText, documentMimeType, null);
            return processRequest(rq, true);
        }

        /// <summary>
        /// Annotates the contents of a single file with the specified MIME
        /// type. Returns an object which allows for convenient access to the
        /// annotations in the annotated document.
        /// </summary>
        /// <param name="documentContent">the file whose contents will be annotated</param>
        /// <param name="documentEncoding">the encoding of the document file</param>
        /// <param name="documentMimeType">the MIME type of the document to annotated</param>
        /// <returns>an {@link AnnotatedDocument} containing the original content as well as the annotations produced</returns>
        public AnnotatedDocument annotateFileContents(FileStream documentContent,
                String documentEncoding, SupportedMimeType documentMimeType)
        {
            String documentPath = Path.GetFullPath(documentContent.Name);

            if (!File.Exists(documentPath))
            {
                throw new IOException("File " + documentPath.ToString()
                        + " is not readable.");
            }
            MemoryStream stream = new MemoryStream();
            using (BinaryWriter writer = new BinaryWriter(stream))
            {
                writer.Write(File.ReadAllBytes(documentPath));
            }
            byte[] bytes = stream.ToArray();
            String content = Encoding.UTF8.GetString(bytes);

            return annotateDocument(content, documentMimeType);
        }

        /// <summary>
        /// Annotates a single document publicly available under a given URL.
        /// Returns an object which allows for convenient access to the
        /// annotations in the annotated document
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be annotated</param>
        /// <returns>an {@link AnnotatedDocument} which allows for convenient programmatic access to the annotated document</returns>
        public AnnotatedDocument annotateDocumentFromUrl(Uri documentUrl,
                SupportedMimeType documentMimeType)
        {
            ServiceRequest rq =
                    new ServiceRequest(documentUrl, documentMimeType, null);
            return processRequest(rq, true);
        }

        /// <summary>
        ///Annotates a single document and returns an {@link InputStream} from
        /// which the contents of the serialized annotated document can be read
        /// </summary>
        /// <param name="documentText">the contents of the document which will be annotated</param>
        /// <param name="documentMimeType">the MIME type of the file which will be annotated</param>
        /// <param name="serializationFormat">the format which will be used for serialization of the annotated document</param>
        /// <returns>an {@link InputStream} from which the serialization of the annotated document can be read</returns>
        public Stream annotateDocumentAsStream(String documentText,
                SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
        {

            ServiceRequest rq =
                    new ServiceRequest(documentText, documentMimeType, null);
            WebHeaderCollection headers = new WebHeaderCollection();
            headers.Add("Accept", serializationFormat.ToStringValue());
            try
            {
                return client.requestForStream("", "POST", rq, headers);
            }
            catch (HttpClientException e)
            {
                JsonObject response = e.getResponse();
                if (response == null)
                {
                    throw new S4ServiceClientException(e.Message);
                }
                String msg = response.ToString();
                throw new S4ServiceClientException(msg == null ? e.Message : msg.ToString());
            }
        }

        /// <summary>
        /// Annotates the contents of a single file returning an
        /// {@link InputStream} from which the annotated content can be read
        /// </summary>
        /// <param name="documentContent">the file which will be annotated</param>
        /// <param name="documentEncoding">the encoding of the file which will be annotated</param>
        /// <param name="documentMimeType">the MIME type of the file which will be annotated</param>
        /// <param name="serializationFormat">the serialization format used for the annotated content</param>
        /// <returns>an {@link InputStream} from which</returns>
        public Stream annotateFileContentsAsStream(FileStream documentContent,
                String documentEncoding, SupportedMimeType documentMimeType,
                ResponseFormat serializationFormat)
        {

            String documentPath = Path.GetFullPath(documentContent.Name);
            if (documentContent.CanRead)
            {
                throw new IOException("File " + documentPath
                        + " is not readable.");
            }
            Byte[] buff;
            buff = File.ReadAllBytes(documentPath);
            Encoding encode = System.Text.Encoding.GetEncoding(documentEncoding);
            String content = new UTF8Encoding().GetString(buff);
            return annotateDocumentAsStream(content, documentMimeType,
                    serializationFormat);
        }

        /// <summary>
        ///Annotates a single document publicly available under a given URL.
        ///Returns the annotated document serialized into the specified format
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be annotated</param>
        /// <param name="serializationFormat">the serialization format of the output</param>
        /// <returns>an {@link InputStream} from where the serialized output can be read</returns>
        public Stream annotateDocumentFromUrlAsStream(Uri documentUrl,
                SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
        {

            ServiceRequest rq =
                    new ServiceRequest(documentUrl, documentMimeType, null);
            try
            {
                WebHeaderCollection collection = new WebHeaderCollection();
                collection.Set(HttpRequestHeader.Accept, serializationFormat.ToString());
                return client.requestForStream("", "POST", rq, collection);
            }
            catch (HttpClientException e)
            {
                JsonObject response = e.getResponse();
                if (response == null)
                {
                    throw new S4ServiceClientException(e.Message, e);
                }
                String msg = response.ToString();
                throw new S4ServiceClientException(msg == null ? e.Message : msg,
                        e);
            }
        }

        /// <summary>
        /// This low level method allows the user to explicitly specify all the
        /// parameters sent to the service. This is done by constructing the
        /// appropriate ServiceRequest object. Returns the contents of
        /// the annotated document
        /// </summary>
        /// <param name="rq">the request which will be sent</param>
        /// <param name="serializationFormat">the format in which to output the annotated document</param>
        /// <param name="requestCompression">whether to allow GZIP compression for large documents</param>
        /// <returns>an{@link InputStream} for the serialization of the annotated document in the specified format</returns>
        public Stream processRequestForStream(ServiceRequest rq,
                ResponseFormat serializationFormat, Boolean requestCompression)
        {

            try
            {
                WebHeaderCollection collection = new WebHeaderCollection();
                collection.Set(HttpRequestHeader.Accept, serializationFormat.ToString());
                if (requestCompression)
                {
                    collection.Set(HttpRequestHeader.AcceptEncoding, "gzip");
                }

                return client.requestForStream("", "POST", rq, collection);
            }
            catch (HttpClientException e)
            {
                JsonObject response = e.getResponse();
                if (response == null)
                {
                    throw new S4ServiceClientException(e.Message);
                }
                String msg = response.ToString();
                throw new S4ServiceClientException(msg == null ? e.Message : msg,
                        e);
            }
        }

        /// <summary>
        /// This low level method allows the user to specify every parameter
        /// explicitly by setting the properties of the OnlineService request
        /// object. Returns an object which wraps the annotated document.
        /// </summary>
        /// <param name="rq">the request which will be sent to the service</param>
        /// <param name="requestCompression">whether to allow GZIP compression for large documents</param>
        /// <returns>an {@link AnnotatedDocument} containing the original content as well as the annotations produced</returns>
        public AnnotatedDocument processRequest(ServiceRequest rq,
                Boolean requestCompression)
        {
            try
            {
                WebHeaderCollection collection = new WebHeaderCollection();
                collection.Set(HttpRequestHeader.Accept, ResponseFormat.JSON.ToString());

                if (requestCompression)
                {
                    collection.Set(HttpRequestHeader.AcceptEncoding, ResponseFormat.JSON.ToString());

                }

                return client.request("", "POST",
                            new AnnotatedDocument(), rq, collection);
            }
            catch (HttpClientException e)
            {
                JsonObject response = e.getResponse();
                if (response == null)
                {
                    throw new S4ServiceClientException(e.Message, e);
                }
                String msg = response.ToString();
                throw new S4ServiceClientException(msg == null ? e.Message : msg,
                        e);
            }
        }

        public static void main(String[] args)
        {
            if (args == null
                    || args.Length == 0)
            {
                printUsageAndTerminate(null);
            }
            Parameters parameters = new Parameters(args);
            String serviceID = parameters.getValue("service");
            if (serviceID == null)
            {
                printUsageAndTerminate("No service name provided");

            }
            ServiceDescriptor service = null;
            try
            {
                service = ServiceCatalog.getItem(serviceID);
            }
            catch (NotSupportedException e)
            {
                printUsageAndTerminate("Unsupported service '" + serviceID + '\'');
            }
            SupportedMimeType mimetype = SupportedMimeType.PLAINTEXT;
            if (parameters.getValue("dtype") != null)
            {
                try
                {
                    mimetype = NumToEnum<SupportedMimeType>(parameters.getValue("dtype").ToString());
                }
                catch (ArgumentException e)
                {
                    printUsageAndTerminate("Unsupported document type (dtype) : " + parameters.getValue("dtype"));
                }
            }
            String inFile = parameters.getValue("file");
            String url = parameters.getValue("url");
            String outFile = parameters.getValue("out", "result.txt");

            if (inFile != null)
            {
                if (false == new FileStream(inFile, FileMode.OpenOrCreate).CanRead)
                {
                    printUsageAndTerminate("Input file is not found : " + inFile);
                }
            }
            else
            {
                if (url == null)
                {
                    printUsageAndTerminate("Neither input file, nor remote URL provided");
                }
            }

            Dictionary<String, String> creds = readCredentials(parameters);
            if (false == creds.ContainsKey("apikey")
                    || false == creds.ContainsKey("secret"))
            {
                printUsageAndTerminate("No credentials details found");
            }

            S4ServiceClient client = new S4ServiceClient(service, creds["apikey"], creds["secret"]);

            try
            {
                Stream resultData = (inFile != null) ?
                        client.annotateFileContentsAsStream(new FileStream(inFile, FileMode.OpenOrCreate), CharSet.Unicode.ToString(), mimetype, ResponseFormat.JSON)
                        : client.annotateDocumentFromUrlAsStream(new Uri(url), mimetype, ResponseFormat.JSON);

                FileStream outStream = new FileStream(outFile, FileMode.Create);
                resultData.CopyTo(outStream);

                outStream.Close();
                resultData.Close();
            }
            catch (IOException ioe)
            {
                Console.WriteLine(ioe.Message);
                Environment.Exit(1);
            }

        }

        public static T NumToEnum<T>(String number)
        {
            return (T)Enum.ToObject(typeof(T), number);
        }


        private static void printUsageAndTerminate(String error)
        {
            if (error != null)
            {
                Console.WriteLine(error);
            }
            Console.WriteLine("Usage: S4ClientService parameter1=value1 parameter2=value2 ...");
            Console.WriteLine("Parameters:");
            Console.WriteLine("  service - the service id to be used (one of:'TwitIE', 'SBT' and 'news')");
            Console.WriteLine("  file    - input file path");
            Console.WriteLine("  url     - input document URL");
            Console.WriteLine("  dtype   - the MIME type of the document (one of:'text/plain', 'text/html', 'application/xml', 'text/xml', 'text/x-pubmed', 'text/x-pubmed', 'text/x-cochrane', 'text/x-mediawiki', 'text/x-json-twitter')");
            Console.WriteLine("  out     - result file name. Defaults to 'result.txt'");
            Console.WriteLine("  apikey  - the api key if credentials file is not used");
            Console.WriteLine("  secret  - the api secret if credentials file is not used");
            Console.WriteLine("  creds   - credentails file path (if apikey and secret parameters are not used)");
            Environment.Exit(1);

        }

        private static Dictionary<String, String> readCredentials(Parameters parameters)
        {
            Dictionary<String, String> dictionary = new Dictionary<String, String>();
            if (parameters.getValue("apikey") != null)
            {
                if (parameters.getValue("secret") == null)
                {
                    printUsageAndTerminate("API key secret not provided");
                }
                dictionary.Add("apikey", parameters.getValue("apikey"));
                dictionary.Add("secret", parameters.getValue("secret"));
                return dictionary;
            }
            String credsFile = "s4credentials.properties";
            if (parameters.getValue("creds") != null)
            {
                credsFile = parameters.getValue("creds");
            }
            if (new FileStream(credsFile, FileMode.OpenOrCreate).CanRead)
            {
                try
                {
                    foreach (KeyValuePair<String, String> pair in load(new FileStream(credsFile, FileMode.OpenOrCreate)))
                    {
                        dictionary.Add(pair.Key, pair.Value);
                    }
                }
                catch (IOException ex)
                {
                    printUsageAndTerminate("Error reading credentials file: " + ex.Message);
                }
            }
            else
            {

                try
                {
                    foreach (KeyValuePair<String, String> pair in load(new FileStream(credsFile, FileMode.Open)))
                    {

                    }
                }
                catch (IOException ioe)
                {
                    printUsageAndTerminate("Error reading credentials file: " + ioe.Message);
                }
            }
            var data = new Dictionary<string, string>();
            foreach (var row in File.ReadAllLines(credsFile))
                data.Add(row.Split('=')[0], string.Join("=", row.Split('=').Skip(1).ToArray()));
            return data;
        }

        private static IEnumerable<KeyValuePair<String, String>> load(FileStream fileStream)
        {
            var data = new Dictionary<string, string>();
            foreach (var row in File.ReadAllLines(fileStream.Name))
                data.Add(row.Split('=')[0], string.Join("=", row.Split('=').Skip(1).ToArray()));
            return data;
        }

        public static Dictionary<string, string> GetProperties(string path)
        {
            string fileData = "";
            using (StreamReader sr = new StreamReader(path))
            {
                fileData = sr.ReadToEnd().Replace("\r", "");
            }
            Dictionary<string, string> Properties = new Dictionary<string, string>();
            string[] kvp;
            string[] records = fileData.Split("\n".ToCharArray());
            foreach (string record in records)
            {
                kvp = record.Split("=".ToCharArray());
                Properties.Add(kvp[0], kvp[1]);
            }
            return Properties;
        }




    }
}
