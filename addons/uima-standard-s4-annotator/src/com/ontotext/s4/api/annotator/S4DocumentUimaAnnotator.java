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
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-03-12
 */
public class S4DocumentUimaAnnotator extends JCasAnnotator_ImplBase {

    public static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentUimaAnnotator.class);

    public static final String PARAM_S4_SERVICE_ENDPOINT = "S4_SERVICE_ENDPOINT";

    public static final String PARAM_S4_API_KEY_ID = "API_KEY_ID";

    public static final String PARAM_S4_API_PASSWORD = "API_PASSWORD";


    public String serviceEndpoint;

    public String apiKeyId;

    public String apiPassword;

    private S4DocumentToUimaCasConverter s4CasConverter;

    private S4ServiceClient restClient;

    private AnnotatedDocument fetchS4AnnotatedDocument(JCas cas) {
        String documentText = cas.getDocumentText();
        if (!Preconditions.isNullOrEmpty(documentText)) {
            return restClient.annotateDocument(documentText, SupportedMimeType.PLAINTEXT);
        }
        return null;
    }

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        final String serviceType = serviceEndpoint.substring(serviceEndpoint.lastIndexOf("/") + 1);
        final AnnotatedDocument s4Document = fetchS4AnnotatedDocument(cas);
        this.s4CasConverter = S4DocumentToUimaCasConverter.newInstance(s4Document);
        s4CasConverter.convertAnnotations(cas, serviceType);
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        this.serviceEndpoint = (String) getContext().getConfigParameterValue(PARAM_S4_SERVICE_ENDPOINT);
        this.apiKeyId = (String) getContext().getConfigParameterValue(PARAM_S4_API_KEY_ID);
        this.apiPassword = (String) getContext().getConfigParameterValue(PARAM_S4_API_PASSWORD);
        this.restClient = S4ClientBuilder.newClientInstance()
                .withS4Endpoint(serviceEndpoint)
                .withApiKeyId(apiKeyId)
                .withApiPassword(apiPassword)
                .build();
    }
}
