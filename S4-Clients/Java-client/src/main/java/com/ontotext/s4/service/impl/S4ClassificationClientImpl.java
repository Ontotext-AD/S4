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
package com.ontotext.s4.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.client.HttpClientException;
import com.ontotext.s4.model.classification.ClassifiedDocument;
import com.ontotext.s4.service.S4ClassificationClient;
import com.ontotext.s4.service.util.S4ServiceClientException;
import com.ontotext.s4.service.util.ResponseFormat;
import com.ontotext.s4.service.util.ServiceRequest;
import com.ontotext.s4.service.util.SupportedMimeType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class S4ClassificationClientImpl extends S4AbstractClientImpl implements S4ClassificationClient {

    /**
     * Constructs a <code>S4AnnotationClient</code> for accessing a specific processing
     * pipeline on the s4.ontotext.com platform using the given credentials.
     *
     * @param item the {@link ServiceDescriptor} which represents the processing pipeline which will be used
     * @param apiKey Your S4 API Key
     * @param keySecret Your S4 Key Secret
     */
    public S4ClassificationClientImpl(ServiceDescriptor item, String apiKey, String keySecret) {
        super(item, apiKey, keySecret);
    }

    /**
     * Constructs a <code>S4AnnotationClient</code> for accessing a specific processing
     * pipeline on the s4.ontotext.com platform using the given credentials.
     *
     * @param serviceURL The service endpoint URL
     * @param apiKey Your S4 API Key
     * @param keySecret Your S4 Key Secret
     */
    public S4ClassificationClientImpl(URL serviceURL, String apiKey, String keySecret) {
        super(serviceURL, apiKey, keySecret);
    }

    /**
     * Classifies a single document with the specified MIME type. Returns an object which allows
     * for convenient access to the classification information of the document.
     *
     * @param documentText the document content to classify
     * @param documentMimeType the MIME type of the document which will be classified
     * @return A {@link ClassifiedDocument} containing the original content as well as the classifications produced
     * @throws S4ServiceClientException Error
     */
    public ClassifiedDocument classifyDocument(String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
        return classifyRequest(rq);
    }

    /**
     * Classifies the contents of a single file with the specified MIME type. Returns an object which allows
     * for convenient access to the classification information for the document.
     *
     * @param documentFile the file whose contents will be classified
     * @param documentEncoding the encoding of the document file
     * @param documentMimeType the MIME type of the document to classified content as well as the classifications produced
     * @throws IOException Error
     * @throws S4ServiceClientException Error
     */
    public ClassifiedDocument classifyDocument(File documentFile, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException, S4ServiceClientException {

        Path documentPath = documentFile.toPath();
        if(!Files.isReadable(documentPath)) {
            throw new IOException("File " + documentPath.toString()	+ " is not readable.");
        }
        ByteBuffer buff;
        buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
        String content = documentEncoding.decode(buff).toString();

        return classifyDocument(content, documentMimeType);
    }

    /**
     * Classifies a single document publicly available under a given URL. Returns an object which allows
     * for convenient access to the classifications in the classified document
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be classified
     * @return A {@link ClassifiedDocument} which allows for convenient programmatic access to the classified document
     * @throws S4ServiceClientException Error
     */
    public ClassifiedDocument classifyDocument(URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
        return classifyRequest(rq);
    }

    /**
     * Classifies a single document and returns an {@link InputStream} from
     * which the contents of the serialized annotated document can be read
     *
     * @param documentText the contents of the document which will be classified
     * @param documentMimeType the MIME type of the file which will be classified
     * @return An {@link InputStream} from which the serialization of the classified document can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream classifyDocumentAsStream(
            String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
        try {
            return client.requestForStream("", "POST", rq, constructHeaders(ResponseFormat.JSON));
        } catch(HttpClientException e) {
            JsonNode msg = handleErrors(e);
            throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
        }
    }

    /**
     * Classifies the contents of a single file returning an
     * {@link InputStream} from which the classification information can be read
     *
     * @param documentFile the file which will be classified
     * @param documentEncoding the encoding of the file which will be classified
     * @param documentMimeType the MIME type of the file which will be classified
     *
     * @return Service response raw content
     *
     * @throws IOException if there are problems reading the contents of the file
     * @throws S4ServiceClientException Error
     */
    public InputStream classifyDocumentAsStream(
            File documentFile, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException,	S4ServiceClientException {

        Path documentPath = documentFile.toPath();
        if(!Files.isReadable(documentPath)) {
            throw new IOException("File " + documentPath.toString() + " is not readable.");
        }
        ByteBuffer buff =
        		ByteBuffer.wrap(Files.readAllBytes(documentPath));
        String content = documentEncoding.decode(buff).toString();
        return classifyDocumentAsStream(content, documentMimeType);
    }

    /**
     * Classifies a single document publicly available under a given URL.
     * Returns the classified document serialized into the specified format
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be classified
     * @return An {@link InputStream} from where the serialized output can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream classifyDocumentAsStream(
            URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
        try {
            return client.requestForStream("", "POST", rq, constructHeaders(ResponseFormat.JSON));
        } catch(HttpClientException e) {
            JsonNode msg = handleErrors(e);
            throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
        }
    }

    /**
     * This low level method allows the user to specify every parameter explicitly by setting the properties
     * of the OnlineService request object. Returns an object which wraps the classified document.
     *
     * @param rq the request which will be sent
     * @return A {@link ClassifiedDocument} containing the original content as well as the annotations produced
     * @throws S4ServiceClientException Error
     */
    private ClassifiedDocument classifyRequest(ServiceRequest rq)
            throws S4ServiceClientException {

        try {
            return client.request("", "POST", new TypeReference<ClassifiedDocument>() {}, rq,
                    constructHeaders(ResponseFormat.JSON));

        } catch (HttpClientException e) {
            JsonNode msg = handleErrors(e);
            throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
        }
    }
}
