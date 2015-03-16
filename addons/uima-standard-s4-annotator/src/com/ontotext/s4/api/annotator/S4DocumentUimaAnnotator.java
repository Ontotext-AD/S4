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

package com.ontotext.s4.api.annotator;

import com.ontotext.s4.api.converter.S4DocumentToUimaCasConverter;
import com.ontotext.s4.api.restclient.S4ClientBuilder;
import com.ontotext.s4.api.util.Preconditions;
import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A standard UIMA annotator used with old UIMA GUI like CVD (CAS Visual Debugger)
 * or could also be installed as a PEAR package through the GUI PEAR Installer.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-03-12
 */
public class S4DocumentUimaAnnotator extends JCasAnnotator_ImplBase {

    public static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentUimaAnnotator.class);

    public static final String PARAM_S4_SERVICE_ENDPOINT = "S4_SERVICE_ENDPOINT";

    public static final String PARAM_S4_API_KEY_ID = "API_KEY_ID";

    public static final String PARAM_S4_API_PASSWORD = "API_PASSWORD";

    /**
     * S4 service url could be of type TWITIE, NEWS, SBT
     */
    public String serviceEndpoint;

    /**
     * S4 services user api key
     */
    public String apiKeyId;

    /**
     * S4 services api secret password
     */
    public String apiPassword;

    /**
     * Converter class instance responsible for conversion logic between S4 Document responses and native UIMA types.
     */
    private S4DocumentToUimaCasConverter s4CasConverter;

    /**
     * The S4 services RESTful client.
     */
    private S4ServiceClient restClient;

    /**
     * Fetch S4 document.
     *
     * @param cas CAS document with set raw document text
     * @return document with S4 annotations
     */
    private AnnotatedDocument fetchS4AnnotatedDocument(JCas cas) {
        String documentText = cas.getDocumentText();
        if (!Preconditions.isNullOrEmpty(documentText)) {
            return restClient.annotateDocument(documentText, SupportedMimeType.PLAINTEXT);
        }
        return null;
    }

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        // Fetch and annotate the raw document and return the S4 document containing the annotations
        final AnnotatedDocument s4Document = fetchS4AnnotatedDocument(cas);

        //Instantiate an annotation converter passing to it the S4 document
        this.s4CasConverter = S4DocumentToUimaCasConverter.newInstance(s4Document);

        //Get the name of the service from the S4 endpoint url
        final String serviceType = serviceEndpoint.substring(serviceEndpoint.lastIndexOf("/"));

        //Convert S4 annotations to native UIMA types
        s4CasConverter.convertAnnotations(cas, serviceType);
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);

        //Initialize component parameter values
        this.serviceEndpoint = (String) getContext().getConfigParameterValue(PARAM_S4_SERVICE_ENDPOINT);
        this.apiKeyId = (String) getContext().getConfigParameterValue(PARAM_S4_API_KEY_ID);
        this.apiPassword = (String) getContext().getConfigParameterValue(PARAM_S4_API_PASSWORD);

        //Initialize S4 client
        this.restClient = S4ClientBuilder.newClientInstance()
                .withS4Endpoint(serviceEndpoint)
                .withApiKeyId(apiKeyId)
                .withApiPassword(apiPassword)
                .build();
    }
}
