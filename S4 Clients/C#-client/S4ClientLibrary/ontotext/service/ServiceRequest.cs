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
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using Ontotext.S4.common;

namespace Ontotext.S4.service
{
    /// <summary>
    /// 
    /// </summary>
    public class ServiceRequest
    {
        private String Document;
        private String DocumentUrl;
        private String DocumentType;
        private List<String> AnnotationSelectors;
        public String document
        {
            get { return Document; }
            set { Document = value; }
        }
        public String documentUrl
        {
            get { return DocumentUrl; }
            set { DocumentUrl = value; }
        }
        public String documentType
        {
            get { return DocumentType; }
            set { DocumentType = value; }
        }
        public List<String> annotationSelectors
        {
            get { return AnnotationSelectors; }
            set { AnnotationSelectors = value; }
        }

        /// <summary>
        /// Construct a request for the online service to annotate a document
        /// provided as part of the request.
        /// </summary>
        /// <param name="document">the content to process.</param>
        /// <param name="type">the MIME type that the service should use to parse the document.</param>
        /// <param name="annotationSelectors">annotations to return. Leave as <code>null</code> to use the default selectors recommended by the pipeline provider.</param>
        public ServiceRequest(String document, SupportedMimeType type,
                List<AnnotationSelector> annotationSelectors)
        {
            this.document = document;
            this.documentType = type.ToStringValue();
            if (annotationSelectors != null)
            {
                this.annotationSelectors = new List<String>();
                foreach (AnnotationSelector aSel in annotationSelectors)
                {
                    this.annotationSelectors.Add(aSel.ToString());
                }
            }
        }

        /// <summary>
        /// Construct a request for the online service to annotate a document
        /// it downloads directly from a remote URL.
        /// </summary>
        /// <param name="documentUrl">the URL from which the document should be
        /// downloaded. This must be accessible to the service so it
        /// must not require authentication credentials etc. (but it
        /// may be, for example, a pre-signed Amazon S3 URL).</param>
        /// <param name="type">the MIME type that the service should use to parse the
        /// document.</param>
        /// <param name="annotationSelectors">annotations to return. Leave as
        /// <code>null</code> to use the default selectors recommended
        /// by the pipeline provider.</param>
        public ServiceRequest(Uri documentUrl, SupportedMimeType type,
                List<AnnotationSelector> annotationSelectors)
        {
            this.documentUrl = documentUrl.ToString();
            this.documentType = type.ToStringValue();
            if (annotationSelectors != null)
            {
                this.annotationSelectors = new List<string>();
                foreach (AnnotationSelector aSel in annotationSelectors)
                {
                    this.annotationSelectors.Add(aSel.ToString());
                }
            }
        }

        
    }
}
