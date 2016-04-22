package com.ontotext.s4.api.annotator.uimafit;

import com.ontotext.s4.service.AnnotatedDocument;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Mocked version of {@link S4DocumentUimaFitAnnotator}. No S4 client is used and json response with annotations
 * is fetched from a test file.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 16-Apr-2016
 */
public class MockS4DocumentUimaFitAnnotator extends S4DocumentUimaFitAnnotator {

    @Override
    void initExternalResources() throws ResourceInitializationException {
        // do not initialize S4 Client while testing
    }

    @Override
    AnnotatedDocument fetchS4AnnotatedDocument(JCas cas) {
        // read src/test/resources/s4_news_result.json and parse to AnnotatedDocument
        return null;
    }

}
