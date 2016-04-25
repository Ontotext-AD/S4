package com.ontotext.s4.api.annotator.uimafit;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.google.gson.Gson;
import com.ontotext.s4.api.util.FileUtils;
import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.Annotation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;

/**
 * Mocked version of {@link S4DocumentUimaFitAnnotator}. No S4 client is used and json response with annotations
 * is fetched from a test file.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 16-Apr-2016
 */
public class MockS4DocumentUimaFitAnnotator extends S4DocumentUimaFitAnnotator {

    public MockS4DocumentUimaFitAnnotator() {

    }

    @Override
    void initExternalResources() throws ResourceInitializationException {
        // do not initialize S4 Client while testing
    }

    @Override
    AnnotatedDocument fetchS4AnnotatedDocument(JCas cas) {
        AnnotatedDocument doc = new AnnotatedDocument();

        Map<String, List<Annotation>> ents = new HashMap<String, List<Annotation>>();

        long[] offsets = {0, 12};
        Annotation ann1 = new Annotation(offsets);

        Map<String, Object> feats = new HashMap<>();
        feats.put("type", "Person");
        feats.put("inst", "http://dbpedia.org/resource/Barack_Obama");
        feats.put("class", "http://dbpedia.org/ontology/Person");
        feats.put("string", "Barack Obama");
        String[] subs = {"http://dbpedia.org/ontology/Thing", "http://dbpedia.org/ontology/Person"};
        feats.put("", subs);
        String[] label = {"Barack Obama"};
        feats.put("preferredLabel", label);

        ann1.features = feats;
        List<Annotation> anns = new ArrayList<Annotation>();
        anns.add(ann1);
        ents.put("Person", anns);

        doc.text = "Barack Obama awarded ten Oxford students today.";
        doc.entities = ents;
        return doc;
    }

}
