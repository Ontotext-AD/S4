/*
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

package com.ontotext.s4.api.annotator.uimafit;

import com.ontotext.s4.api.converter.S4DocumentToUimaCasConverter;
import com.ontotext.s4.api.restclient.S4ClientBuilder;
import com.ontotext.s4.api.util.Preconditions;
import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A UIMA Annotator component responsible for translating the S4 Document annotations into native UIMA types.
 * NOTE: This is a UIMA compontent suitable for usage with uimaFIT library and executing pipilines through
 * java code.
 * @see com.ontotext.s4.api.example.S4UimaPipeline
 *
 * Component parameters:
 * <ul>
 *      <li><code>S4_SERVICE_ENDPOINT</code> - The type of service called to annotate your document,</li>
 *      <li><code>API_KEY_ID</code> - The api key id to access the service.</li>
 *      <li><code>API_PASSWORD</code> - The api password to access the service.</li>
 *      <li><code>GENERATE_TYPESYSTEM</code> - When set to "true" dynamically generates type system as xml descriptors and java classes for the types that are included
 *      after the S4 service has done its job. WARNING: This parameter should be avoided it most cases. Use only if you think your type system is not up to date.</li>
 * </ul>
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-03-04
 */
public class S4DocumentUimaFitAnnotator extends JCasAnnotator_ImplBase {

    public static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentUimaFitAnnotator.class);

    public static final String PARAM_S4_SERVICE_ENDPOINT = "S4_SERVICE_ENDPOINT";

    public static final String PARAM_S4_API_KEY_ID = "API_KEY_ID";

    public static final String PARAM_S4_API_PASSWORD = "API_PASSWORD";

    public static final String PARAM_GENERATE_TYPESYSTEM = "GENERATE_TYPESYSTEM";


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

    @ConfigurationParameter(name = PARAM_GENERATE_TYPESYSTEM,
            mandatory = false,
            description = "Dynamically generate type system as xml descriptors and java classes for the types.")
    public String generateTypeSystemFlag = "false";

    /**
     * Converter class instance responsible for conversion logic between S4 Document responses and native UIMA types.
     */
    private S4DocumentToUimaCasConverter s4CasConverter;

    /**
     * The S4 services RESTful client.
     */
    private S4ServiceClient restClient;

    /**
     * A list of UIMA type system descriptions used to accumulate the full UIMA type system based on the set of documents
     * you send to the S4 service. This is used only in the cases where we want to have fully up to date set of S4
     * annotations being translated later into UIMA type xml descriptors and corresponding java classes. In regular usage
     * this type of preprocessing should be avoided due to not fully updating the UIMA type system. It will work meaningfully
     * only for your current set of documents being launched through the pipeline.
     * WARNING: This is activated only when the PARAM_GENERATE_TYPESYSTEM parameter is set, otherwise it is ignored. If
     * you launch you pipeline in this setup in won't convert your annotations it will only regenerate the type system.
     * This is a preprocessing step used only in exceptional cases
     */
    public static List<TypeSystemDescription> cachedTypeSystemDescriptions = new ArrayList<>();

    /**
     * Validate component parameters.
     *
     * @throws ResourceInitializationException
     */
    private void checkConfigParams() throws ResourceInitializationException {
        if (serviceEndpoint == null || Preconditions.isNullOrEmpty(apiKeyId) || Preconditions.isNullOrEmpty(apiPassword)) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.CONFIG_SETTING_ABSENT,
                    new Object[] { PARAM_S4_SERVICE_ENDPOINT + ", " + PARAM_S4_API_KEY_ID + ", "+
                            PARAM_S4_API_PASSWORD
                    });
        }
    }

    /**
     * Initialize S4 client and validate component parameters. Used in the initialize() step of the component.
     *
     * @throws ResourceInitializationException
     */
    void initExternalResources() throws ResourceInitializationException {
        checkConfigParams();
        this.restClient = S4ClientBuilder.newClientInstance()
                .withS4Endpoint(serviceEndpoint)
                .withApiKeyId(apiKeyId)
                .withApiPassword(apiPassword)
                .build();
    }

    private void transformFetchedDocument(JCas cas) {
        //Get the name of the service from the S4 endpoint url
        final String serviceType = serviceEndpoint.substring(serviceEndpoint.lastIndexOf("/") + 1);

        // Fetch and annotate the raw document and return the S4 document containing the annotations
        final AnnotatedDocument s4Document = fetchS4AnnotatedDocument(cas);

        //Instantiate an annotation converter passing to it the S4 document
        this.s4CasConverter = S4DocumentToUimaCasConverter.newInstance(s4Document);

        //If the PARAM_GENERATE_TYPESYSTEM is set tell the annotator to regenerate the UIMA type system
        if (generateTypeSystemFlag.equals("true")) {
            TypeSystemDescription typeSystemDescription = s4CasConverter.inferCasTypeSystem(serviceType);
            cachedTypeSystemDescriptions.add(typeSystemDescription);
            return;
        }

        //Convert S4 annotations to native UIMA types
        s4CasConverter.convertAnnotations(cas, serviceType);
    }

    /**
     * Fetch S4 document.
     *
     * @param cas CAS document with set raw document text
     * @return document with S4 annotations
     */
    AnnotatedDocument fetchS4AnnotatedDocument(JCas cas) {
        /*
         * Get document text from the JCas object.
         * NOTE: the text has to be set in a CollectionReader component used earlier in the pipeline. This is not a
         * responsibility of the Annotator component.
         */
        String documentText = cas.getDocumentText();
        if (!Preconditions.isNullOrEmpty(documentText)) {
            //Fetch and annotate raw document text
            return restClient.annotateDocument(documentText, SupportedMimeType.PLAINTEXT);
        }
        return null;
    }

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        transformFetchedDocument(cas);
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        initExternalResources();
    }
}
