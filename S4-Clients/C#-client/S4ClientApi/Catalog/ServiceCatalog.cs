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

namespace Ontotext.S4.catalog
{
    /// <summary>
    /// S4 Service Catalog
    /// </summary>
    public class ServiceCatalog
    {
        /// <summary>
        /// Base URL for the S4 endpoint
        /// </summary>
        public const String S4_SERVICE_ENDPOINT_URL = "https://text.s4.ontotext.com/v1/";

        /// <summary>
        /// 
        /// </summary>
        /// <param name="itemName">Service ID ('twitie', 'news', 'sbt' or 'news-classifier')</param>
        /// <returns>A ServiceDescriptor object</returns>
        public static ServiceDescriptor getItem(String itemName)
        {
            if (itemName == null)
            {
                throw new ArgumentException("No service id provided (expected one of: 'twitie', 'news', 'sbt' or 'news-classifier')");
            }
            ServiceDescriptor item = new ServiceDescriptor();
            switch (itemName.ToLower())
            {
                case "twitie":
                {
                    item.Name = "TwitIE";
                    item.ServiceUrl = S4_SERVICE_ENDPOINT_URL + "twitie";
                    item.Description = "The Twitter analytics service of S4 is based on the TwitIE open source "
                    + "information extraction pipeline by the GATE platform.";
                    return item;
                }
                case "sbt":
                {
                    item.Name = "SBT";
                    item.ServiceUrl = S4_SERVICE_ENDPOINT_URL + "sbt";
                    item.Description = "The Semantic Biomedical Tagger (SBT) has a built-in capability to recognize "
                    + "133 biomedical entity types and semantically link them to the knowledge base systems";
                    return item;
                }
                case "news":
                {
                    item.Name = "news";
                    item.ServiceUrl = S4_SERVICE_ENDPOINT_URL + "news";
                    item.Description = "The News Annotation service retrieves various types of entities from texts "
                    + "as well as the relations between them.";
                    return item;
                }
                case "news-classifier":
                {
                    item.Name = "news-classifier";
                    item.ServiceUrl = S4_SERVICE_ENDPOINT_URL + "news-classifier";
                    item.Description = "The News classifier service assigns each input document a category,"
                    + "based on the 17 top-level categories of IPTC Subject Reference System";
                    return item;
                }
                default:
                {
                    throw new NotSupportedException("Unsupported service id (" + itemName + "). For custom service endpoints, please use ServiceDescriptor class directly");
                }
            }
        }
    }
}
