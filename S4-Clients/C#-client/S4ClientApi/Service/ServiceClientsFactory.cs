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
using S4ClientApi.Service.Impl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace S4ClientApi.Service
{
    class ServiceClientsFactory
    {
        /// <summary>
        /// Constructs an {@link IS4AnnotationClientImpl} to be used with the text annotation services ('news', 'news-de', 'sbt', 'twitie', etc.)
        /// </summary>
        /// <param name="service">The service name to be used </param>
        /// <param name="apiKey">Your S4 API Key</param>
        /// <param name="keySecret">Your S4 Key Secret</param>
        /// <returns> An {@link IS4AnnotationClientImpl} to access the S4 text annotation services</returns>
        public static IS4AnnotationClientImpl createAnnotationClient(String service, String apiKey, String keySecret)
        {
            return createAnnotationClient(ServiceCatalog.getItem(service), apiKey, keySecret);
        }

        /// <summary>
        /// Constructs an {@link IS4AnnotationClientImpl} to be used with the text annotation services ('news', 'news-de', 'sbt', 'twitie', etc.)
        /// </summary>
        /// <param name="serviceDescriptor">Wrap object for the service URL</param>
        /// <param name="apiKey">Your S4 API Key</param>
        /// <param name="keySecret">Your S4 Key Secret</param>
        /// <returns>An {@link IS4AnnotationClientImpl} to access the S4 text annotation 'news', 'news-de', 'sbt' or 'twitie' services</returns>
        public static IS4AnnotationClientImpl createAnnotationClient(ServiceDescriptor serviceDescriptor, String apiKey, String keySecret)
        {
            return new S4AnnotationClientImpl(serviceDescriptor, apiKey, keySecret);
        }

        /// <summary>
        /// Constructs an {@link IS4AnnotationClientImpl} to be used with the text annotation services ('news', 'news-de', 'sbt', 'twitie', etc.)
        /// </summary>
        /// <param name="serviceURL">The service endpoint URL</param>
        /// <param name="apiKey">Your S4 API Key</param>
        /// <param name="keySecret">Your S4 Key Secret</param>
        /// <returns>An {@link IS4AnnotationClientImpl} to access the S4 text annotation 'news', 'news-de', 'sbt' or 'twitie' services</returns>
        public static IS4AnnotationClientImpl createAnnotationClient(Uri serviceURL, String apiKey, String keySecret)
        {
            return new S4AnnotationClientImpl(serviceURL, apiKey, keySecret);
        }

        /// <summary>
        /// Constructs an {@link IS4ClassificationClientImpl} to be used with the text classification service ('news-classifier')
        /// </summary>
        /// <param name="apiKey">Your S4 Api Key</param>
        /// <param name="keySecret">Your S4 Key Secret</param>
        /// <returns>An {@link IS4ClassificationClientImpl} to access the S4 'news-classifier' service</returns>
        public static IS4ClassificationClientImpl createClassificationClient(String apiKey, String keySecret)
        {
            return new S4ClassificationClientImpl(ServiceCatalog.getItem("news-classifier"), apiKey, keySecret);
        }

        /// <summary>
        /// Constructs an {@link IS4ClassificationClientImpl} to be used with the text classification service ('news-classifier')
        /// </summary>
        /// <param name="serviceURL">The URL of the classification service</param>
        /// <param name="apiKey">Your S4 Api Key</param>
        /// <param name="keySecret">Your S4 Key Secret</param>
        /// <returns>An {@link IS4ClassificationClientImpl} to access the S4 'news-classifier' service</returns>
        public static IS4ClassificationClientImpl createClassificationClient(Uri serviceURL, String apiKey, String keySecret)
        {
            return new S4ClassificationClientImpl(serviceURL, apiKey, keySecret);
        }
    }
}
