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

import com.ontotext.s4.model.classification.ClassifiedDocument;
import com.ontotext.s4.service.util.S4ServiceClientException;
import com.ontotext.s4.service.util.SupportedMimeType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public interface S4ClassificationClient extends S4AbstractClient {

    /**
     * Classifies a single document with the specified MIME type. Returns an object which allows
     * for convenient access to the classification information of the document.
     *
     * @param documentText the document content to classify
     * @param documentMimeType the MIME type of the document which will be classified
     * @return an {@link ClassifiedDocument} containing the original content as well as the classifications produced
     * @throws S4ServiceClientException Error
     */
    public ClassifiedDocument classifyDocument(
            String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException;

    /**
     * Classifies the contents of a single file with the specified MIME type. Returns an object which allows
     * for convenient access to the classification information for the document.
     *
     * @param documentContent the file whose contents will be classified
     * @param documentEncoding the encoding of the document file
     * @param documentMimeType the MIME type of the document to classified content as well as the classifications produced
     * @return an object which allows for convenient access to the classification information for the document.
     * @throws IOException Error
     * @throws S4ServiceClientException Error
     */
    public ClassifiedDocument classifyDocument(
            File documentContent, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException, S4ServiceClientException;

    /**
     * Classifies a single document publicly available under a given URL. Returns an object which allows
     * for convenient access to the classifications in the classified document
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be classified
     * @return an {@link ClassifiedDocument} which allows for convenient programmatic access to the classified document
     * @throws S4ServiceClientException Error
     */
    public ClassifiedDocument classifyDocument(
            URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException;

    /**
     * Classifies a single document and returns an {@link InputStream} from
     * which the contents of the serialized annotated document can be read
     *
     * @param documentText the contents of the document which will be classified
     * @param documentMimeType the MIME type of the file which will be classified
     * @return an {@link InputStream} from which the serialization of the classified document can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream classifyDocumentAsStream(
            String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException;

    /**
     * Classifies the contents of a single file returning an
     * {@link InputStream} from which the classification information can be read
     *
     * @param documentContent the file which will be classified
     * @param documentEncoding the encoding of the file which will be classified
     * @param documentMimeType the MIME type of the file which will be classified
     *
     * @return Service response raw content
     *
     * @throws IOException if there are problems reading the contents of the file
     * @throws S4ServiceClientException Error
     */
    public InputStream classifyDocumentAsStream(
            File documentContent, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException,	S4ServiceClientException;

    /**
     * Classifies a single document publicly available under a given URL.
     * Returns the classified document serialized into the specified format
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be classified
     * @return an {@link InputStream} from where the serialized output can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream classifyDocumentAsStream(
            URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException;
}
