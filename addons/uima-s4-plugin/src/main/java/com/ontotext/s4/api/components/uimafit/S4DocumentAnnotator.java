package com.ontotext.s4.api.components.uimafit;

import com.ontotext.s4.api.converter.S4DocumentToUimaCasConverter;
import com.ontotext.s4.api.restclient.S4ClientBuilder;
import com.ontotext.s4.api.util.Preconditions;
import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;
import org.apache.commons.lang.StringUtils;
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
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-03-04
 */
public class S4DocumentAnnotator extends JCasAnnotator_ImplBase {

    public static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentAnnotator.class);

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
    public String generateTypeSystemFlag;

    private S4DocumentToUimaCasConverter s4CasConverter;

    private S4ServiceClient restClient;

    public static List<TypeSystemDescription> cachedTypeSystemDescriptions = new ArrayList<>();

    public List<TypeSystemDescription> getCachedTypeSystemDescriptions() {
        return cachedTypeSystemDescriptions;
    }

    private void checkConfigParams() throws ResourceInitializationException {
        if (serviceEndpoint == null || Preconditions.isNullOrEmpty(apiKeyId) || Preconditions.isNullOrEmpty(apiPassword)) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.CONFIG_SETTING_ABSENT,
                    new Object[] { PARAM_S4_SERVICE_ENDPOINT + ", " + PARAM_S4_API_KEY_ID + ", "+
                            PARAM_S4_API_PASSWORD
                    });
        }
    }

    private void initExternalResources() throws ResourceInitializationException {
        checkConfigParams();
        this.restClient = S4ClientBuilder.newClientInstance()
                .withS4Endpoint(serviceEndpoint)
                .withApiKeyId(apiKeyId)
                .withApiPassword(apiPassword)
                .build();
    }

    private void transformFetchedDocument(JCas cas) {
        final String serviceType = StringUtils.substringAfterLast(serviceEndpoint, "/");

        final AnnotatedDocument s4Document = fetchS4AnnotatedDocument(cas);
        this.s4CasConverter = S4DocumentToUimaCasConverter.newInstance(s4Document);
        if (generateTypeSystemFlag.equals(PARAM_GENERATE_TYPESYSTEM)) {
            TypeSystemDescription typeSystemDescription = s4CasConverter.inferCasTypeSystem(serviceType);
            cachedTypeSystemDescriptions.add(typeSystemDescription);
            return;
        }
        s4CasConverter.convertAnnotations(cas, serviceType);
    }

    private AnnotatedDocument fetchS4AnnotatedDocument(JCas cas) {
        String documentText = cas.getDocumentText();
        if (!Preconditions.isNullOrEmpty(documentText)) {
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
