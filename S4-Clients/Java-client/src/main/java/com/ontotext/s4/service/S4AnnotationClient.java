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

import com.ontotext.s4.model.annotation.AnnotatedDocument;
import com.ontotext.s4.service.util.ResponseFormat;
import com.ontotext.s4.service.util.S4ServiceClientException;
import com.ontotext.s4.service.util.SupportedMimeType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public interface S4AnnotationClient extends S4AbstractClient {
    /**
     * Annotates a single document with the specified MIME type. Returns an object which allows
     * for convenient access to the annotations in the annotated document.
     *
     * @param documentText the document content to annotate
     * @param documentMimeType the MIME type of the document which will be annotated
     * @return An {@link AnnotatedDocument} containing the original content as well as the annotations produced
     * @throws S4ServiceClientException Error
     */
    public AnnotatedDocument annotateDocument(
            String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException;

    /**
     * Annotates a document publicly available under a given URL and tags and categorizes any images inside.
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be annotated
     * @param imageTagging The boolean flag to allow/deny image tagging of the document
     * @param imageCategorization The boolean flag to allow/deny image categorization of the document
     * @return An {@link AnnotatedDocument} object with the processed image tags and categories
     * @throws S4ServiceClientException Error
     */
    public AnnotatedDocument annotateDocument(
            URL documentUrl, SupportedMimeType documentMimeType, boolean imageTagging, boolean imageCategorization)
            throws S4ServiceClientException;

    /**
     * Annotates the contents of a single file with the specified MIME type. Returns an object which allows
     * for convenient access to the annotations in the annotated document.
     *
     * @param documentFile the file containing the data to be annotated
     * @param documentEncoding the encoding of the document file
     * @param documentMimeType the MIME type of the document to annotated content as well as the annotations produced
     * @return An {@link AnnotatedDocument} which allows for convenient access to the annotations in the annotated document.
     * @throws IOException Error
     * @throws S4ServiceClientException Error
     */
    public AnnotatedDocument annotateDocument(
            File documentFile, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException, S4ServiceClientException;

    /**
     * Annotates a single document publicly available under a given URL. Returns an object which allows
     * for convenient access to the annotations in the annotated document
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be annotated
     * @return An {@link AnnotatedDocument} which allows for convenient programmatic access to the annotated document
     * @throws S4ServiceClientException Error
     */
    public AnnotatedDocument annotateDocument(
            URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException;

    /**
     * Annotates a single document and returns an {@link InputStream} from
     * which the contents of the serialized annotated document can be read
     *
     * @param documentText the contents of the document which will be annotated
     * @param documentMimeType the MIME type of the file which will be annotated
     * @param serializationFormat the format which will be used for serialization of the annotated document
     * @return An {@link InputStream} from which the serialization of the annotated document can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream annotateDocumentAsStream(
            String documentText, SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
            throws S4ServiceClientException;

    /**
     * Annotates the contents of a single file returning an
     * {@link InputStream} from which the annotated content can be read
     *
     * @param documentFile the file which will be annotated
     * @param documentEncoding the encoding of the file which will be annotated
     * @param documentMimeType the MIME type of the file which will be annotated
     * @param serializationFormat the serialization format used for the annotated content
     * @return An {@link InputStream} from which the serialization of the annotated document can be read
     * @throws IOException if there are problems reading the contents of the file
     * @throws S4ServiceClientException Error
     */
    public InputStream annotateDocumentAsStream(
            File documentFile, Charset documentEncoding, SupportedMimeType documentMimeType,
                ResponseFormat serializationFormat)
            throws IOException, S4ServiceClientException;

    /**
     * Annotates a single document publicly available under a given URL.
     * Returns An {@link InputStream} from which the annotated content can be read
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be annotated
     * @param serializationFormat the serialization format of the output
     * @return An {@link InputStream} from where the serialized output can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream annotateDocumentAsStream(
            URL documentUrl, SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
            throws S4ServiceClientException;

    /**
     * Annotates a single document publicly available under a given URL and
     * categorizes and tags (if specified) any images inside.
     * Returns an {@link InputStream} from which the annotated content can be read
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be annotated
     * @param serializationFormat the serialization format of the output
     * @param imageTagging The boolean flag to allow/deny image tagging of the document
     * @param imageCategorization The boolean flag to allow/deny image categorization of the document
     * @return An {@link InputStream} from where the serialized output can be read
     * @throws S4ServiceClientException Error
     */
    public InputStream annotateDocumentAsStream(
            URL documentUrl, SupportedMimeType documentMimeType, ResponseFormat serializationFormat,
                boolean imageTagging, boolean imageCategorization)
            throws S4ServiceClientException;
}
