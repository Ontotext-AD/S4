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
using Ontotext.S4.service;
using System;
using System.Collections.Generic;
using System.Json;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace S4ClientApi.Service.Impl
{
    public class S4AbstractClientImpl : IS4AbstractClientImpl
    {
        protected HttpClient client;
        protected bool RequestCompression;

        /// <summary>
        /// Construct a <code>S4ServiceClient</code> accessing a specific processing pipeline on the s4.ontotext.com platform using the given credentials.
        /// </summary>
        /// <param name="item">the {@link ServiceDescriptor} which represents the processing pipeline which will be used</param>
        /// <param name="apiKeyId">API key ID for authentication</param>
        /// <param name="keySecret">corresponding password</param>
        public S4AbstractClientImpl(ServiceDescriptor item, String apiKeyId, String keySecret)
        {
            Uri endpoint;
            try
            {
                endpoint = new Uri(item.ServiceUrl);
            }
            catch (Exception e)
            {
                throw new ArgumentException("Invalid ServiceDescriptor specified. No API endpoint specified." + e.Message);
            }
            this.client = new HttpClient(endpoint, apiKeyId, keySecret);
        }

        /// <summary>
        /// Construct a <code>S4ServiceClient</code> accessing a specific processing pipeline on the s4.ontotext.com platform using the given credentials.
        /// </summary>
        /// <param name="endpoint">the URL of the pipeline which will be used for processing</param>
        /// <param name="apiKeyId">apiKeyId API key ID for authentication</param>
        /// <param name="keySecret">corresponding password</param>
        public S4AbstractClientImpl(Uri endpoint, String apiKeyId, String keySecret)
        {
            this.client = new HttpClient(endpoint, apiKeyId, keySecret);
        }

        public bool requestCompression
        {
            get { return RequestCompression; }
            set { RequestCompression = value; }
        }

        protected String handleErrors(HttpClientException e)
        {
            JsonObject response = e.getResponse();
            if (response == null)
            {
                throw new S4ServiceClientException(e.Message, e);
            }
            return response.ToString();
        }

        protected Dictionary<String, String> constructHeaders(ResponseFormat serializationFormat)
        {
            Dictionary<String, String> headers = Parameters.newInstance().withValue("Accept", serializationFormat.ToStringValue());
            if (requestCompression)
            {
                headers.Add("Accept-Encoding", "gzip");
            }
            return headers;
        }
    }
}
