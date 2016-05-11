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
using Ontotext.S4.ontotext.service;
using Ontotext.S4.service;

namespace S4ClientApi.Service.Impl
{
    public interface IS4ClassificationClientImpl : IS4AbstractClientImpl
    {

        /// <summary>
        /// Classifies a single document publicly available under a given URL.
        /// Returns an object which allows for convenient access to the classification information for the document
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be classified</param>
        /// <returns>A {@link ClassifiedDocument} which allows for convenient programmatic access to the classified document</returns>
        ClassifiedDocument classifyDocument(Uri documentUrl, SupportedMimeType documentMimeType);

        /// <summary>
        /// Classifies a single document with the specified MIME type. Returns an object
        /// which allows for convenient access to the classification information for the document.
        /// </summary>
        /// <param name="documentText">the document content to classify</param>
        /// <param name="documentMimeType"> the MIME type of the document which will be classified</param>
        /// <returns>A {@link ClassifiedDocument} containing the original content as well as the classifications produced</returns>
        ClassifiedDocument classifyDocument(string documentText, SupportedMimeType documentMimeType);

        /// <summary>
        /// Classifies the contents of a single file with the specified MIME type. Returns an object
        /// which allows for convenient access to the classification information for the document.
        /// </summary>
        /// <param name="documentContent">the file whose contents will be classified</param>
        /// <param name="documentEncoding">the encoding of the document file</param>
        /// <param name="documentMimeType">the MIME type of the document to classified</param>
        /// <returns>A {@link ClassifiedDocument} containing the original content as well as the classifications produced</returns>
        ClassifiedDocument classifyDocument(FileStream documentContent, string documentEncoding, SupportedMimeType documentMimeType);

        /// <summary>
        /// Classifies a single document publicly available under a given URL.
        /// Returns the classification information for the document serialized into the specified format
        /// </summary>
        /// <param name="documentUrl">the publicly accessible URL from where the document will be downloaded</param>
        /// <param name="documentMimeType">the MIME type of the document which will be classified</param>
        /// <returns>Аn {@link InputStream} from where the serialized output can be read</returns>
        Stream classifyDocumentAsStream(Uri documentUrl, SupportedMimeType documentMimeType);

        /// <summary>
        /// Classifies a single document and returns an {@link InputStream} from
        /// which the contents of the serialized classified document can be read
        /// </summary>
        /// <param name="documentText">the contents of the document which will be classified</param>
        /// <param name="documentMimeType">the MIME type of the file which will be classified</param>
        /// <returns>an {@link InputStream} from which the serialization of the classified document can be read</returns>
        Stream classifyDocumentAsStream(string documentText, SupportedMimeType documentMimeType);

        /// <summary>
        /// Classifies the contents of a single file returning an {@link InputStream}
        /// from which the classification information can be read
        /// </summary>
        /// <param name="documentContent">the file which will be classified</param>
        /// <param name="documentEncoding">the encoding of the file which will be classified</param>
        /// <param name="documentMimeType">the MIME type of the file which will be classified</param>
        Stream classifyDocumentAsStream(FileStream documentContent, string documentEncoding, SupportedMimeType documentMimeType);
    }
}