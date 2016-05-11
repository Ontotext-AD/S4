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
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using Ontotext.S4.common;

namespace Ontotext.S4.service
{
    /// <summary>
    /// Class representing the request format for the S4 online API.
    /// </summary>
    public class ServiceRequest
    {
        private String Document;
        private String DocumentUrl;
        private String DocumentType;
        private bool ImageTagging;
        private bool ImageCategorization;


        /// <summary>
        /// The document text to annotate.
        /// </summary>
        public String document
        {
            get { return Document; }
            set { Document = value; }
        }

        /// <summary>
        /// The URL of a remote document to annotate.
        /// </summary>
        public String documentUrl
        {
            get { return DocumentUrl; }
            set { DocumentUrl = value; }
        }

        /// <summary>
        /// The MIME type that the document should be parsed as by the service.
        /// </summary>
        public String documentType
        {
            get { return DocumentType; }
            set { DocumentType = value; }
        }

        /// <summary>
        /// The boolean flag to allow/deny image tagging of the document
        /// </summary>
        public bool imageTagging
        {
            get { return ImageTagging; }
            set { ImageTagging = value; }
        }

        /// <summary>
        /// The boolean flag to allow/deny image categorization of the document
        /// </summary>
        public bool imageCategorization
        {
            get { return ImageCategorization; }
            set { ImageCategorization = value; }
        }

        public ServiceRequest()
        {

        }

        /// <summary>
        /// Construct a request for the online service to annotate or classify a document provided as part of the request.
        /// </summary>
        /// <param name="document">the content to process.</param>
        /// <param name="type">the MIME type that the service should use to parse the document.</param>
        public ServiceRequest(String document, SupportedMimeType type)
        {
            this.Document = document;
            this.DocumentType = type.ToStringValue();
            this.ImageTagging = false;
            this.ImageCategorization = false;
        }

        /// <summary>
        /// Construct a request for the online service to annotate or classify a document downloaded directly from a remote URL
        /// </summary>
        /// <param name="documentUrl">the URL from which the document should be downloaded</param>
        /// <param name="type">the MIME type that the service should use to parse the document</param>
        public ServiceRequest(Uri documentUrl, SupportedMimeType type)
        {
            this.DocumentUrl = documentUrl.ToString();
            this.DocumentType = type.ToStringValue();
            this.ImageTagging = false;
            this.ImageCategorization = false;
        }

        /// <summary>
        /// Construct a request for the online service to annotate or classify a document downloaded directly from a remote URL
        /// </summary>
        /// <param name="documentUrl">the URL from which the document should be downloaded</param>
        /// <param name="type">the MIME type that the service should use to parse the document</param>
        /// <param name="imageTagging">The boolean flag to allow/deny image tagging of the document</param>
        /// <param name="imageCategorization">The boolean flag to allow/deny image categorization of the document</param>
        public ServiceRequest(Uri documentUrl, SupportedMimeType type, bool imageTagging, bool imageCategorization)
        {
            this.DocumentUrl = documentUrl.ToString();
            this.DocumentType = type.ToStringValue();
            this.ImageTagging = imageTagging;
            this.ImageCategorization = imageCategorization;
        }
    }
}
