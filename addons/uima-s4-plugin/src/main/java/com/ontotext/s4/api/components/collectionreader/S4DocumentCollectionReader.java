/*
* Copyright (c) 2015
*
* This file is part of the s4.ontotext.com REST client library, and is
* licensed under the Apache License, Version 2.0 (the "License");
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

package com.ontotext.s4.api.components.collectionreader;

import com.ontotext.s4.api.converter.S4DocumentToUimaCasConverter;
import com.ontotext.s4.api.restclient.S4ClientBuilder;
import com.ontotext.s4.api.util.FileUtils;
import com.ontotext.s4.api.util.Preconditions;
import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;
import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.ConfigurationParameterFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import static org.apache.uima.fit.factory.ConfigurationParameterFactory.ConfigurationData;

/**
* A collection reader which automatically converts S4 annotations into UIMA data structures
* so that they can be used by other UIMA processing components.
*
*/
public class S4DocumentCollectionReader extends CasCollectionReader_ImplBase {

    public static final Logger LOG = LoggerFactory.getLogger(S4DocumentCollectionReader.class);

    public static final String PARAM_S4_SERVICE_ENDPOINT = "S4_SERVICE_ENDPOINT";

    public static final String PARAM_S4_API_KEY_ID = "API_KEY_ID";

    public static final String PARAM_S4_API_PASSWORD = "API_PASSWORD";

    public static final String PARAM_SOURCE_TEXT_FILE_PATH = "SOURCE_TEXT_FILE_PATH";


    @ConfigurationParameter(name = PARAM_S4_SERVICE_ENDPOINT,
            mandatory = true,
            description = "The type of service called to annotate your document.")
    public String serviceEndpoint;

    @ConfigurationParameter(name = PARAM_S4_API_KEY_ID,
            mandatory = true,
            description = "The api key id to access the service.")
    public String apiKeyId;

    @ConfigurationParameter(name = PARAM_S4_API_PASSWORD,
            mandatory = true,
            description = "The api password to access the service.")
    public String apiPassword;

    @ConfigurationParameter(name = PARAM_SOURCE_TEXT_FILE_PATH,
            mandatory = true,
            description = "The file path leading to the source text files to be processed")
    public String rawTextFilePath;


    private S4DocumentToUimaCasConverter s4CasConverter;

    private S4ServiceClient restClient;

    private List<File> rawTextFiles;

    private Iterator<File> rawTextFilesIterator;

    private int documentsFetched;

    private int totalDocuments;

    private Object[] parameters;


    /**
     * Create a collection reader description corresponding to the provided configuration data.
     *
     * @param confData Any configuration data values in the standard UIMAfit format
     * @return a new <code>CollectionReaderDescription</code> instance suitable for using a pipeline directly
     *    or serializing to disk as XML
     * @throws org.apache.uima.resource.ResourceInitializationException
     */
    public CollectionReaderDescription createDescription(Object... confData) throws ResourceInitializationException {
        this.parameters = confData;
        initExternalResources();
        final AnnotatedDocument s4Document = fetchS4AnnotatedDocument(rawTextFiles.get(0));
        this.s4CasConverter = new S4DocumentToUimaCasConverter(s4Document);
        s4CasConverter.inferCasTypeSystem(s4Document.entities);
        TypeSystemDescription tsd;
        try {
            tsd = s4CasConverter.getTypeSystemDescription();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }

        return CollectionReaderFactory.createReaderDescription(S4DocumentCollectionReader.class, tsd, parameters);
    }

    private void getConfigurationParams(Object... confData) throws ResourceInitializationException {
        ConfigurationData confDataParsed = ConfigurationParameterFactory.createConfigurationData(confData);
        // since we do not yet have a reader, we need to semi-manually parse the params
        for (int i = 0; i < confDataParsed.configurationParameters.length; i++) {
            String paramName = confDataParsed.configurationParameters[i].getName();
            Object value = confDataParsed.configurationValues[i];
            switch (paramName) {
                case PARAM_S4_SERVICE_ENDPOINT:
                    this.serviceEndpoint = (String) value;
                    break;
                case PARAM_S4_API_KEY_ID:
                    this.apiKeyId = (String) value;
                    break;
                case PARAM_S4_API_PASSWORD:
                    this.apiPassword = (String) value;
                    break;
                case PARAM_SOURCE_TEXT_FILE_PATH:
                    this.rawTextFilePath = (String) value;
                    break;
            }
        }

        if (serviceEndpoint == null || Preconditions.isNullOrEmpty(apiKeyId)
                || Preconditions.isNullOrEmpty(apiPassword) || Preconditions.isNullOrEmpty(rawTextFilePath)) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.CONFIG_SETTING_ABSENT,
                    new Object[] { PARAM_S4_SERVICE_ENDPOINT + ", " + PARAM_S4_API_KEY_ID + ", "+
                            PARAM_S4_API_PASSWORD + ", " + PARAM_SOURCE_TEXT_FILE_PATH
                    });
        }
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        initExternalResources();
        this.rawTextFilesIterator = rawTextFiles.listIterator();
        this.documentsFetched = 0;
        this.totalDocuments = rawTextFiles.size();
    }

    private void initExternalResources() throws ResourceInitializationException {
        getConfigurationParams(parameters);
        this.rawTextFiles = FileUtils.listRawTextFiles(rawTextFilePath);
        this.restClient = S4ClientBuilder.newClientInstance()
                .withS4Endpoint(serviceEndpoint)
                .withApiKeyId(apiKeyId)
                .withApiPassword(apiPassword)
                .build();
    }

    private void transformFetchedDocument(File rawTextFile, CAS... cas) {
        final AnnotatedDocument s4Document = fetchS4AnnotatedDocument(rawTextFile);
        this.s4CasConverter = new S4DocumentToUimaCasConverter(s4Document);
        s4CasConverter.convertAnnotations();
        s4CasConverter.setSourceDocumentText();
        cas[0] = s4CasConverter.getConvertedDocument();
    }

    private AnnotatedDocument fetchS4AnnotatedDocument(File rawTextFile) {
        String rawText = null;
        try {
            rawText = FileUtils.readFileToString(rawTextFile.getPath(), Charset.defaultCharset());
        } catch (IOException e) {
            LOG.error("Cannot read raw text document -> " + rawTextFile.getPath(), e);
        }

        return restClient.annotateDocument(rawText, SupportedMimeType.PLAINTEXT);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
     */
    public void getNext(CAS cas) throws IOException, CollectionException {
        documentsFetched++;
        transformFetchedDocument(rawTextFilesIterator.next(), cas);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#hasNext()
     */
    public boolean hasNext() throws IOException, CollectionException {
        return rawTextFilesIterator.hasNext();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
     */
    public Progress[] getProgress() {
        return new Progress[] { new ProgressImpl(documentsFetched, totalDocuments, Progress.ENTITIES) };
    }
}
