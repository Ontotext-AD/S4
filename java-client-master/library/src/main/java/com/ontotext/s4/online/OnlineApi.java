/*
 * Copyright (c) 2014 Ontotext AD
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.client.RestClient;
import com.ontotext.s4.client.RestClientException;
import com.ontotext.s4.item.Item;

/**
 * Main entry point for the S4 online API to send individual
 * documents for annotation by a particular pipeline and receive the
 * results immediately.
 * 
 */
public class OnlineApi {

  private RestClient client;

  /**
   * Construct an <code>OnlineApi</code> accessing a specific processing
   * pipeline on the s4.ontotext.com platform using the given
   * credentials.
   * 
   * @param item the {@link Item} returned from the {@link Shop} which
   *          represents the processing pipeline which will be used
   * @param apiKeyId API key ID for authentication
   * @param apiPassword corresponding password
   */
  public OnlineApi(Item item, String apiKeyId, String apiPassword) {
    URL endpoint;
    try {
      endpoint = new URL(item.onlineUrl);
    } catch(MalformedURLException e) {
      throw new IllegalArgumentException(
              "Invalid shop item specified. No API endpoint specified.", e);
    }
    this.client = new RestClient(endpoint, apiKeyId, apiPassword);
  }

  /**
   * Construct an <code>OnlineApi</code> accessing a specific processing
   * pipeline on the s4.ontotext.com platform using the given
   * credentials.
   * 
   * @param endpoint the URL of the pipeline which will be used for
   *          processing
   * @param apiKeyId apiKeyId API key ID for authentication
   * @param apiPassword corresponding password
   */
  public OnlineApi(URL endpoint, String apiKeyId, String apiPassword) {
    this.client = new RestClient(endpoint, apiKeyId, apiPassword);
  }

  /**
   * Annotates a single document with the specified MIME type. Returns
   * an object which allows for convenient access to the annotations in
   * the annotated document.
   * 
   * @param documentText the document content to annotate
   * @param documentMimeType the MIME type of the document which will be
   *          annotated
   * @return an {@link AnnotatedDocument} containing the original
   *         content as well as the annotations produced
   * @throws OnlineApiException
   */
  public AnnotatedDocument annotateDocument(String documentText,
          SupportedMimeType documentMimeType) throws OnlineApiException {
    OnlineServiceRequest rq =
            new OnlineServiceRequest(documentText, documentMimeType, null);
    return processRequest(rq, true);
  }

  /**
   * Annotates the contents of a single file with the specified MIME
   * type. Returns an object which allows for convenient access to the
   * annotations in the annotated document.
   * 
   * @param documentContent the file whose contents will be annotated
   * @param documentEncoding the encoding of the document file
   * @param documentMimeType the MIME type of the document to annotated
   * @return an {@link AnnotatedDocument} containing the original
   *         content as well as the annotations produced
   * @throws IOException
   * @throws OnlineApiException
   */
  public AnnotatedDocument annotateFileContents(File documentContent,
          Charset documentEncoding, SupportedMimeType documentMimeType)
          throws IOException, OnlineApiException {

    Path documentPath = documentContent.toPath();
    if(!Files.isReadable(documentPath)) {
      throw new IOException("File " + documentPath.toString()
              + " is not readable.");
    }
    ByteBuffer buff;
    buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
    String content = documentEncoding.decode(buff).toString();

    return annotateDocument(content, documentMimeType);
  }

  /**
   * Annotates a single document publicly available under a given URL.
   * Returns an object which allows for convenient access to the
   * annotations in the annotated document
   * 
   * @param documentUrl the publicly accessible URL from where the
   *          document will be downloaded
   * @param documentMimeType the MIME type of the document which will be
   *          annotated
   * @return an {@link AnnotatedDocument} which allows for convenient
   *         programmatic access to the annotated document
   * @throws OnlineApiException
   */
  public AnnotatedDocument annotateDocumentFromUrl(URL documentUrl,
          SupportedMimeType documentMimeType) throws OnlineApiException {
    OnlineServiceRequest rq =
            new OnlineServiceRequest(documentUrl, documentMimeType, null);
    return processRequest(rq, true);
  }

  /**
   * Annotates a single document and returns an {@link InputStream} from
   * which the contents of the serialized annotated document can be read
   * 
   * @param documentText the contents of the document which will be
   *          annotated
   * @param documentMimeType the MIME type of the file which will be
   *          annotated
   * @param serializationFormat the format which will be used for
   *          serialization of the annotated document
   * @return an {@link InputStream} from which the serialization of the
   *         annotated document can be read
   * @throws OnlineApiException
   */
  public InputStream annotateDocumentAsStream(String documentText,
          SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
          throws OnlineApiException {

    OnlineServiceRequest rq =
            new OnlineServiceRequest(documentText, documentMimeType, null);
    try {
      return client.requestForStream("", "POST", rq, "Accept",
              serializationFormat.acceptHeader);
    } catch(RestClientException e) {
      JsonNode response = e.getResponse();
      if(response == null) {
        throw new OnlineApiException(e.getMessage(), e);
      }
      JsonNode msg = response.get("message");
      throw new OnlineApiException(msg == null ? e.getMessage() : msg.asText(),
              e);
    }
  }

  /**
   * Annotates the contents of a single file returning an
   * {@link InputStream} from which the annotated content can be read
   * 
   * @param documentContent the file which will be annotated
   * @param documentEncoding the encoding of the file which will be
   *          annotated
   * @param documentMimeType the MIME type of the file which will be
   *          annotated
   * @param serializationFormat the serialization format used for the
   *          annotated content
   * @return an {@link InputStream} from which
   * @throws IOException if there are problems reading the contents of
   *           the file
   * @throws OnlineApiException
   */
  public InputStream annotateFileContentsAsStream(File documentContent,
          Charset documentEncoding, SupportedMimeType documentMimeType,
          ResponseFormat serializationFormat) throws IOException,
          OnlineApiException {

    Path documentPath = documentContent.toPath();
    if(!Files.isReadable(documentPath)) {
      throw new IOException("File " + documentPath.toString()
              + " is not readable.");
    }
    ByteBuffer buff;
    buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
    String content = documentEncoding.decode(buff).toString();
    return annotateDocumentAsStream(content, documentMimeType,
            serializationFormat);
  }

  /**
   * Annotates a single document publicly available under a given URL.
   * Returns the annotated document serialized into the specified format
   * 
   * @param documentUrl the publicly accessible URL from where the
   *          document will be downloaded
   * @param documentMimeType the MIME type of the document which will be
   *          annotated
   * @param serializationFormat the serialization format of the output
   * @return an {@link InputStream} from where the serialized output can
   *         be read
   * @throws OnlineApiException
   */
  public InputStream annotateDocumentFromUrlAsStream(URL documentUrl,
          SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
          throws OnlineApiException {

    OnlineServiceRequest rq =
            new OnlineServiceRequest(documentUrl, documentMimeType, null);
    try {
      return client.requestForStream("", "POST", rq, "Accept",
              serializationFormat.acceptHeader);
    } catch(RestClientException e) {
      JsonNode response = e.getResponse();
      if(response == null) {
        throw new OnlineApiException(e.getMessage(), e);
      }
      JsonNode msg = response.get("message");
      throw new OnlineApiException(msg == null ? e.getMessage() : msg.asText(),
              e);
    }
  }

  /**
   * This low level method allows the user to explicitly specify all the
   * parameters sent to the service. This is done by constructing the
   * appropriate OnlineServiceRequest object. Returns the contents of
   * the annotated document
   * 
   * @param rq the request which will be sent
   * @param serializationFormat the format in which to output the
   *          annotated document
   * @param requestCompression whether to allow GZIP compression for
   *          large documents
   * @return an{@link InputStream} for the serilization of the annotated
   *         document in the specified format
   * @throws OnlineApiException
   */
  public InputStream processRequestForStream(OnlineServiceRequest rq,
          ResponseFormat serializationFormat, boolean requestCompression)
          throws OnlineApiException {

    try {
      if(requestCompression) {
        return client.requestForStream("", "POST", rq, "Accept",
                serializationFormat.acceptHeader, "Accept-Encoding", "gzip");
      } else {
        return client.requestForStream("", "POST", rq, "Accept",
                serializationFormat.acceptHeader);
      }
    } catch(RestClientException e) {
      JsonNode response = e.getResponse();
      if(response == null) {
        throw new OnlineApiException(e.getMessage(), e);
      }
      JsonNode msg = response.get("message");
      throw new OnlineApiException(msg == null ? e.getMessage() : msg.asText(),
              e);
    }
  }

  /**
   * This low level method allows the user to specify every parameter
   * explicitly by setting the properties of the OnlineService request
   * object. Returns an object which wraps the annotated document.
   * 
   * @param rq the request which will be sent to the service
   * @param requestCompression whether to allow GZIP compression for
   *          large documents
   * @return an {@link AnnotatedDocument} containing the original
   *         content as well as the annotations produced
   * @throws OnlineApiException
   */
  public AnnotatedDocument processRequest(OnlineServiceRequest rq,
          boolean requestCompression) throws OnlineApiException {
    try {
      if(requestCompression) {
        return client.request("", "POST",
                new TypeReference<AnnotatedDocument>() {
                }, rq, "Accept", ResponseFormat.JSON.acceptHeader,
                "Accept-Encoding", "gzip");
      } else {
        return client.request("", "POST",
                new TypeReference<AnnotatedDocument>() {
                }, rq, "Accept", ResponseFormat.JSON.acceptHeader);
      }
    } catch(RestClientException e) {
      JsonNode response = e.getResponse();
      if(response == null) {
        throw new OnlineApiException(e.getMessage(), e);
      }
      JsonNode msg = response.get("message");
      throw new OnlineApiException(msg == null ? e.getMessage() : msg.asText(),
              e);
    }
  }

}
