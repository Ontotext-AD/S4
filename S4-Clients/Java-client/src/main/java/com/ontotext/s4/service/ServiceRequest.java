/*
 * S4 Java client library
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
package com.ontotext.s4.service;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class representing the request format for the S4 online API.
 * 
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
   * Annotation selector expressions representing the annotations to
   * return.
   */
  @JsonInclude(Include.NON_NULL)
  private List<String> annotationSelectors;

  /**
   * Construct a request for the online service to annotate a document
   * provided as part of the request.
   * 
   * @param document the content to process.
   * @param type the MIME type that the service should use to parse the
   *          document.
   * @param annotationSelectors annotations to return. Leave as
   *          <code>null</code> to use the default selectors recommended
   *          by the pipeline provider.
   */
  public ServiceRequest(String document, SupportedMimeType type,
          List<AnnotationSelector> annotationSelectors) {
    this.document = document;
    this.documentType = type.value;
    if(annotationSelectors != null) {
      this.annotationSelectors = new LinkedList<String>();
      for(AnnotationSelector as : annotationSelectors) {
        this.annotationSelectors.add(as.toString());
      }
    }
  }

  /**
   * Construct a request for the online service to annotate a document
   * it downloads directly from a remote URL.
   * 
   * @param documentUrl the URL from which the document should be
   *          downloaded. This must be accessible to the service so it
   *          must not require authentication credentials etc. (but it
   *          may be, for example, a pre-signed Amazon S3 URL).
   * @param type the MIME type that the service should use to parse the
   *          document.
   * @param annotationSelectors annotations to return. Leave as
   *          <code>null</code> to use the default selectors recommended
   *          by the pipeline provider.
   */
  public ServiceRequest(URL documentUrl, SupportedMimeType type,
          List<AnnotationSelector> annotationSelectors) {
    this.documentUrl = documentUrl.toString();
    this.documentType = type.value;
    if(annotationSelectors != null) {
      this.annotationSelectors = new LinkedList<String>();
      for(AnnotationSelector as : annotationSelectors) {
        this.annotationSelectors.add(as.toString());
      }
    }
  }

  /**
   * @return the text of the document to annotate.
   */
  public String getDocument() {
    return document;
  }

  /**
   * @return the URL of the document to annotate.
   */
  public String getDocumentUrl() {
    return documentUrl;
  }

  /**
   * @return the MIME type that will be used to interpret the document.
   */
  public String getDocumentType() {
    return documentType;
  }

  /**
   * @return annotation selectors denoting the annotations to return. If
   *         <code>null</code> the default selectors specified by the
   *         pipeline provider will be used.
   */
  public List<String> getAnnotationSelectors() {
    return annotationSelectors;
  }
}
