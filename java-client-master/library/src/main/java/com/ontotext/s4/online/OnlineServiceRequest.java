/*
 * Copyright (c) 2014  Ontotext AD
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ontotext.s4.online;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class representing the request format for the S4 online API.
 * 
 */
public class OnlineServiceRequest {

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
  private String mimeType;

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
  public OnlineServiceRequest(String document, SupportedMimeType type,
          List<AnnotationSelector> annotationSelectors) {
    this.document = document;
    this.mimeType = type.value;
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
  public OnlineServiceRequest(URL documentUrl, SupportedMimeType type,
          List<AnnotationSelector> annotationSelectors) {
    this.documentUrl = documentUrl.toString();
    this.mimeType = type.value;
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
  public String getMimeType() {
    return mimeType;
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
