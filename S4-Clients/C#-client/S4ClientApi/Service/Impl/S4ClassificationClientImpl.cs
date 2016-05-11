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
using Ontotext.S4.catalog;
using Ontotext.S4.client;
using Ontotext.S4.common;
using Ontotext.S4.ontotext.service;
using Ontotext.S4.service;
using System;
using System.Collections.Generic;
using System.IO;
using System.Json;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace S4ClientApi.Service.Impl
{
    public class S4ClassificationClientImpl : S4AbstractClientImpl, IS4ClassificationClientImpl
    {
        /// <summary>
        /// Construct a <code>IS4ClassificationClientImpl</code> accessing a specific processing pipeline on the s4.ontotext.com platform using the given credentials.
        /// </summary>
        /// <param name="item">the {@link ServiceDescriptor} which represents the processing pipeline which will be used</param>
        /// <param name="apiKey">API key ID for authentication</param>
        /// <param name="keySecret">corresponding password</param>
        public S4ClassificationClientImpl(ServiceDescriptor item, String apiKey, String keySecret) : base(item, apiKey, keySecret)
        {
        }

        /// <summary>
        /// Construct a <code>IS4ClassificationClientImpl</code> accessing a specific processing pipeline on the s4.ontotext.com platform using the given credentials.
        /// </summary>
        /// <param name="endpoint">the URL of the pipeline which will be used for processing</param>
        /// <param name="apiKey">apiKeyId API key ID for authentication</param>
        /// <param name="keySecret">corresponding password</param>
        public S4ClassificationClientImpl(Uri endpoint, String apiKey, String keySecret) : base(endpoint, apiKey, keySecret)
        {
        }

        /// <summary>
        /// Classifies a single document with the specified MIME type. Returns an object
        /// which allows for convenient access to the classification information for the document.
        /// </summary>
        /// <param name="documentText">the document content to classify</param>
        /// <param name="documentMimeType"> the MIME type of the document which will be classified</param>
        /// <returns>A {@link ClassifiedDocument} containing the original content as well as the classifications produced</returns>
        public ClassifiedDocument classifyDocument(String documentText, SupportedMimeType documentMimeType)
        {
            ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
            return classifyRequest(rq);
        }

        /// <summary>
        /// Classifies a single document publicly available under a given URL.
        /// Returns an object which allows for convenient access to the classification information for the document
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be classified</param>
        /// <returns>A {@link ClassifiedDocument} which allows for convenient programmatic access to the classified document</returns>
        public ClassifiedDocument classifyDocument(Uri documentUrl, SupportedMimeType documentMimeType)
        {
            ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
            return classifyRequest(rq);
        }

        /// <summary>
        /// Classifies the contents of a single file with the specified MIME type. Returns an object
        /// which allows for convenient access to the classification information for the document.
        /// </summary>
        /// <param name="documentContent">the file whose contents will be classified</param>
        /// <param name="documentEncoding">the encoding of the document file</param>
        /// <param name="documentMimeType">the MIME type of the document to classified</param>
        /// <returns>A {@link ClassifiedDocument} containing the original content as well as the classifications produced</returns>
        public ClassifiedDocument classifyDocument(FileStream documentContent, String documentEncoding, SupportedMimeType documentMimeType)
        {
            String documentPath = Path.GetFullPath(documentContent.Name);

            if (!File.Exists(documentPath))
            {
                throw new IOException("File " + documentPath.ToString() + " is not readable.");
            }
            MemoryStream stream = new MemoryStream();
            using (BinaryWriter writer = new BinaryWriter(stream))
            {
                writer.Write(File.ReadAllBytes(documentPath));
            }
            byte[] bytes = stream.ToArray();
            String content = Encoding.UTF8.GetString(bytes);

            return classifyDocument(content, documentMimeType);
        }
        
        /// <summary>
        /// Classifies a single document and returns an {@link InputStream} from
        /// which the contents of the serialized classified document can be read
        /// </summary>
        /// <param name="documentText">the contents of the document which will be classified</param>
        /// <param name="documentMimeType">the MIME type of the file which will be classified</param>
        /// <returns>an {@link InputStream} from which the serialization of the classified document can be read</returns>
        public Stream classifyDocumentAsStream(String documentText, SupportedMimeType documentMimeType)
        {
            ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);

            try
            {
                return client.requestForStream("", "POST", rq, constructHeaders(ResponseFormat.JSON));
            }
            catch (HttpClientException e)
            {
                String msg = handleErrors(e);
                throw new S4ServiceClientException(msg == null ? e.Message : msg.ToString());
            }
        }

        /// <summary>
        /// Classifies a single document publicly available under a given URL.
        /// Returns the classification information for the document serialized into the specified format
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be classified</param>
        /// <returns>Аn {@link InputStream} from where the serialized output can be read</returns>
        public Stream classifyDocumentAsStream(Uri documentUrl, SupportedMimeType documentMimeType)
        {
            ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
            try
            {
                return client.requestForStream("", "POST", rq, constructHeaders(ResponseFormat.JSON));
            }
            catch (HttpClientException e)
            {
                String msg = handleErrors(e);
                throw new S4ServiceClientException(msg == null ? e.Message : msg, e);
            }
        }

        /// <summary>
        /// Classifies the contents of a single file returning an {@link InputStream}
        /// from which the classification information can be read
        /// </summary>
        /// <param name="documentContent">the file which will be classified</param>
        /// <param name="documentEncoding">the encoding of the file which will be classified</param>
        /// <param name="documentMimeType">the MIME type of the file which will be classified</param>
        public Stream classifyDocumentAsStream(FileStream documentContent,
                String documentEncoding, SupportedMimeType documentMimeType)
        {
            String documentPath = Path.GetFullPath(documentContent.Name);
            if (!documentContent.CanRead)
            {
                throw new IOException("File " + documentPath + " is not readable.");
            }

            Byte[] buff;
            buff = File.ReadAllBytes(documentPath);
            Encoding encode = System.Text.Encoding.GetEncoding(documentEncoding);
            String content = new UTF8Encoding().GetString(buff);
            return classifyDocumentAsStream(content, documentMimeType);
        }

        /// <summary>
        /// This low level method allows the user to explicitly specify all the parameters sent to the service.
        /// This is done by constructing the appropriate ServiceRequest object. Returns the classification information for the document
        /// </summary>
        /// <param name="rq">the request which will be sent</param>
        /// <param name="serializationFormat">the format in which to output the classified document</param>
        /// <returns>Аn {@link InputStream} for the serialization of the classified document in the specified format</returns>
        private Stream classifyRequestForStream(ServiceRequest rq, ResponseFormat serializationFormat)
        {
            try
            {
                return client.requestForStream("", "POST", rq, constructHeaders(serializationFormat));
            }
            catch (HttpClientException e)
            {
                String msg = handleErrors(e);
                throw new S4ServiceClientException(msg == null ? e.Message : msg, e);
            }
        }

        /// <summary>
        /// This low level method allows the user to specify every parameter explicitly by setting the
        /// properties of the OnlineService request object. Returns an object which wraps the classification information for the document.
        /// </summary>
        /// <param name="rq">the request which will be sent to the service</param>
        /// <returns>Аn {@link ClassifiedDocument} containing the original content as well as the annotations produced</returns>
        private ClassifiedDocument classifyRequest(ServiceRequest rq)
        {
            try
            {
                return client.request("", "POST", new ClassifiedDocument(), rq, constructHeaders(ResponseFormat.JSON));
            }
            catch (HttpClientException e)
            {
                String msg = handleErrors(e);
                throw new S4ServiceClientException(msg == null ? e.Message : msg, e);
            }
        }
    }
}
