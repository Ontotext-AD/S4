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
using System.IO;
using Ontotext.S4.service;
using S4ClientApi.Service.Impl;

namespace S4ClientApi.Service
{
    public interface IS4AnnotationClientImpl : IS4AbstractClientImpl
    {
        /// <summary>
        /// Annotates a single document publicly available under a given URL.
        /// Returns an object which allows for convenient access to the annotations in the annotated document
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be annotated</param>
        /// <returns>an {@link AnnotatedDocument} which allows for convenient programmatic access to the annotated document</returns>
        AnnotatedDocument annotateDocument(Uri documentUrl, SupportedMimeType documentMimeType);

        /// <summary>
        /// Annotates a single document with the specified MIME type. Returns an object
        /// which allows for convenient access to the annotations in the annotated document.
        /// </summary>
        /// <param name="documentText">the document content to annotate</param>
        /// <param name="documentMimeType"> the MIME type of the document which will be annotated</param>
        /// <returns>An {@link AnnotatedDocument} containing the original content as well as the annotations produced</returns>
        AnnotatedDocument annotateDocument(string documentText, SupportedMimeType documentMimeType);

        /// <summary>
        /// Annotates the contents of a single file with the specified MIME type. Returns an object
        /// which allows for convenient access to the annotations in the annotated document.
        /// </summary>
        /// <param name="documentContent">the file whose contents will be annotated</param>
        /// <param name="documentEncoding">the encoding of the document file</param>
        /// <param name="documentMimeType">the MIME type of the document to annotated</param>
        /// <returns>an {@link AnnotatedDocument} containing the original content as well as the annotations produced</returns>
        AnnotatedDocument annotateDocument(FileStream documentContent, string documentEncoding, SupportedMimeType documentMimeType);

        /// <summary>
        /// Annotates a document publicly available under a given URL and tags and/or categorizes any images inside.
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be annotated</param>
        /// <returns>An {@link AnnotatedDocument} object with the processed image tags and categories</returns>
        AnnotatedDocument annotateDocument(Uri documentUrl, SupportedMimeType documentMimeType, bool imgTagging, bool imgCategorization);

        /// <summary>
        /// Annotates a single document publicly available under a given URL.
        /// Returns the annotated document serialized into the specified format
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be annotated</param>
        /// <param name="serializationFormat">the serialization format of the output</param>
        /// <returns>Аn {@link InputStream} from where the serialized output can be read</returns>
        Stream annotateDocumentAsStream(Uri documentUrl, SupportedMimeType documentMimeType, ResponseFormat serializationFormat);

        /// <summary>
        /// Annotates a single document and returns an {@link InputStream} from
        /// which the contents of the serialized annotated document can be read
        /// </summary>
        /// <param name="documentText">the contents of the document which will be annotated</param>
        /// <param name="documentMimeType">the MIME type of the file which will be annotated</param>
        /// <param name="serializationFormat">the format which will be used for serialization of the annotated document</param>
        /// <returns>an {@link InputStream} from which the serialization of the annotated document can be read</returns>
        Stream annotateDocumentAsStream(string documentText, SupportedMimeType documentMimeType, ResponseFormat serializationFormat);

        /// <summary>
        /// Annotates the contents of a single file returning an {@link InputStream} from which the annotated content can be read
        /// </summary>
        /// <param name="documentContent">the file which will be annotated</param>
        /// <param name="documentEncoding">the encoding of the file which will be annotated</param>
        /// <param name="documentMimeType">the MIME type of the file which will be annotated</param>
        /// <param name="serializationFormat">the serialization format used for the annotated content</param>
        Stream annotateDocumentAsStream(FileStream documentContent, string documentEncoding, SupportedMimeType documentMimeType, ResponseFormat serializationFormat);

        /// <summary>
        /// Annotates a document publicly available under a given URL and tags and/or categorizes any images inside.
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be annotated</param>
        /// <param name="serializationFormat">the serialization format of the output</param>
        /// <returns>Аn {@link InputStream} from where the serialized output can be read</returns>
        Stream annotateDocumentAsStream(Uri documentUrl, SupportedMimeType documentMimeType, ResponseFormat serializationFormat, bool imgTagging, bool imgCategorization);
    }
}