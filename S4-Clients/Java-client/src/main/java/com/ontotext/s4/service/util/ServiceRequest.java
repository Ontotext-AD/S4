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
package com.ontotext.s4.service.util;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ontotext.s4.service.util.SupportedMimeType;

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
     * The boolean flag to allow/deny image tagging of the document
     */
    private boolean imageTagging;

    /**
     * The boolean flag to allow/deny image categorization of the document
     */
    private boolean imageCategorization;


    public ServiceRequest() {

    }

    /**
    * Construct a request for the online service to annotate or classify a document provided as part of the request.
    *
    * @param document the content to process.
    * @param type the MIME type that the service should use to parse the document.
    */
    public ServiceRequest(String document, SupportedMimeType type) {
        this.document = document;
        this.documentType = type.value;
        this.imageTagging = false;
        this.imageCategorization = false;
    }

    /**
    * Construct a request for the online service to annotate or classify a document downloaded directly from a remote URL.
    *
    * @param documentUrl the URL from which the document should be downloaded
    * @param type the MIME type that the service should use to parse the document.
    */
    public ServiceRequest(URL documentUrl, SupportedMimeType type) {
        this.documentUrl = documentUrl.toString();
        this.documentType = type.value;
        this.imageTagging = false;
        this.imageCategorization = false;
    }

    /**
     * Construct a request for the online service to annotate or classify a document downloaded directly from a remote URL.
     *
     * @param documentUrl The URL address of the document
     * @param type the MIME type that the service should use to parse the document.
     * @param imageTagging Whether to include image tagging in the response
     * @param imageCategorization Whether to include image categorization in the response
     */
    public ServiceRequest(URL documentUrl, SupportedMimeType type, boolean imageTagging, boolean imageCategorization) {
        this.documentUrl = documentUrl.toString();
        this.documentType = type.value;
        this.imageCategorization = imageCategorization;
        this.imageTagging = imageTagging;
    }


    public String getDocument() {
        return this.document;
    }
    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentUrl() {
        return this.documentUrl;
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

    public boolean getImageTagging() {
        return this.imageTagging;
    }
    public void setImageTagging(boolean imageTagging) {
        this.imageTagging = imageTagging;
    }

    public boolean getImageCategorization() {
        return this.imageCategorization;
    }
    public void setImageCategorization(boolean imageCategorization) {
        this.imageCategorization = imageCategorization;
    }
}
