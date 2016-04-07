/*
 * S4 Java client library
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
package com.ontotext.s4.service;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class representing the request format for the S4 online API.
 */
public class ServiceRequest {

    /**
    * The document text to annotate.
    */
    @JsonInclude(Include.NON_NULL)
    private String document;

    /**
    * The URL of a remote document to annotate.
    */
    @JsonInclude(Include.NON_NULL)
    private String documentUrl;

    /**
    * The MIME type that the document should be parsed as by the service.
    */
    private String documentType;

    /**
    * Construct a request for the online service to annotate or classify a document provided as part of the request.
    *
    * @param document the content to process.
    * @param type the MIME type that the service should use to parse the document.
    */
    public ServiceRequest(String document, SupportedMimeType type) {
        this.document = document;
        this.documentType = type.value;
    }

    /**
    * Construct a request for the online service to annotate or classify a document downloaded directly from a remote URL.
    *
    * @param documentUrl the URL from which the document should be
    *          downloaded. This must be accessible to the service so it
    *          must not require authentication credentials etc. (but it
    *          may be, for example, a pre-signed Amazon S3 URL).
    * @param type the MIME type that the service should use to parse the document.
    */
    public ServiceRequest(URL documentUrl, SupportedMimeType type) {
        this.documentUrl = documentUrl.toString();
        this.documentType = type.value;
    }


    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }
    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}
