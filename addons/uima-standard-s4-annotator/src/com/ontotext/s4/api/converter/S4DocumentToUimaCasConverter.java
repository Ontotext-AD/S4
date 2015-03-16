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

package com.ontotext.s4.api.converter;

import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.Annotation;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.jcas.JCas;
import org.joor.Reflect;
import org.joor.ReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * A CAS converter transforming a S4 document into a UIMA CAS structure.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-19
 */
public class S4DocumentToUimaCasConverter {

    private static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentToUimaCasConverter.class);

    public static final String UIMA_ANNOTATION_TYPES_PACKAGE = "com.ontotext.s4.api.uima.types.";


    private AnnotatedDocument startDocument;

    private org.apache.uima.jcas.tcas.Annotation casAnnotation;

    /**
     * S4 Document structures with metadata.
     */
    private Map<String, List<Annotation>> entities;

    public AnnotatedDocument getStartDocument() {
        return startDocument;
    }


    private S4DocumentToUimaCasConverter(AnnotatedDocument startDocument) {
        this.startDocument = startDocument;
        this.entities = startDocument.entities;
    }

    public static S4DocumentToUimaCasConverter newInstance(AnnotatedDocument startDocument) {
        return new S4DocumentToUimaCasConverter(startDocument);
    }

    /**
     * Method implementing the whole S4-UIMA annotation conversion logic.
     *
     * @param cas the CAS object from the Annotator component
     * @param serviceType type of the S4 service: sbt, news, twitie
     */
    public void convertAnnotations(JCas cas, String serviceType) {
        for (Map.Entry<String, List<Annotation>> entityEntry : entities.entrySet()) {
            String annotationName = entityEntry.getKey();
            annotationName = UIMA_ANNOTATION_TYPES_PACKAGE + serviceType + "." + removeDashes(annotationName);
            List<Annotation> annotations = entityEntry.getValue();
            for (Annotation ann : annotations) {
                casAnnotation = getFeatureStructureForS4Annotation(cas, annotationName);
                if (casAnnotation == null) {
                    LOG.warn(annotationName + " not available");
                    return;
                }

                Type type = casAnnotation.getType();
                LOG.info(">>>>>>>>>>> Get Type -> " + type);
                casAnnotation.setBegin((int) ann.startOffset);
                casAnnotation.setEnd((int) ann.endOffset);
                Map<String, Object> features = ann.features;
                for (Map.Entry<String, Object> entry : features.entrySet()) {
                    String featureName = patchMatchingFeatureNamesWithUimaReservedKeywords(entry.getKey());
                    Feature feature = type.getFeatureByBaseName(featureName);
                    if (feature == null) {
                        continue;
                    }

                    Object value = entry.getValue();
                    String stringValue;
                    if (value != null) {
                        stringValue = value.toString();
                    } else {
                        continue;
                    }

                    casAnnotation.setFeatureValueFromString(feature, stringValue);
                }
                cas.addFsToIndexes(casAnnotation);
            }
        }
    }

    /**
     * A method acquiring the corresponding annotation type from the JCasGen generated java classes using reflection.
     *
     * @param cas the CAS object from the Annotator component
     * @param annotationName the class name of the JCasGen class
     * @return class instance cast to org.apache.uima.jcas.tcas.Annotation
     */
    private org.apache.uima.jcas.tcas.Annotation getFeatureStructureForS4Annotation(JCas cas, String annotationName) {
        try {
            return (org.apache.uima.jcas.tcas.Annotation) Reflect.on(annotationName)
                    .create(cas)
                    .get();
        } catch (ReflectException e) {
            final Throwable cause = e.getCause();
            LOG.error(cause.toString(), e);

            //Expose real error if exception is InvocationTargetException because this exception is only a wrapper for the real one.
            if (cause instanceof InvocationTargetException) {
                LOG.error(((InvocationTargetException) cause).getTargetException().toString(), cause);
            }
            return null;
        }
    }

    /**
     * A method fixing feature names matching UIMA reserved keywords like "class" or "type".
     *
     * @param feature the feature name to be patched
     * @return the new pathched feature name
     */
    private String patchMatchingFeatureNamesWithUimaReservedKeywords(String feature) {
        if (feature.equals("class")) {
            feature = "class_feature";
        } else if (feature.equals("type")) {
            feature = "type_feature";
        }
        return feature;
    }

    /**
     * Removes dashes from UIMA Annotations because they are not allowed to contain dashes.
     *
     * @param typeName the annotation name of the current annotation of the source document
     * @return the transformed annotation name suited for the UIMA typesystem
     */
    private String removeDashes(String typeName) {
        if (typeName.contains("-")) {
            typeName = typeName.replaceAll("-", "_");
        }
        return typeName;
    }
}
