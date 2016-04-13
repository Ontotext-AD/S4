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

package com.ontotext.s4.api.converter;

import com.ontotext.s4.api.util.TypeSystemUtils;
import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.Annotation;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.joor.Reflect;
import org.joor.ReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A CAS converter transforming a S4 document into a UIMA CAS structure.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-19
 */
public class S4DocumentToUimaCasConverter {

    private static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentToUimaCasConverter.class);

    private TypeSystemDescription tsd;

    private AnnotatedDocument startDocument;

    private org.apache.uima.jcas.tcas.Annotation casAnnotation;

    /**
     * S4 Document structures with metadata.
     */
    private Map<String, List<Annotation>> entities;

    /**
     * Collection with all S4 annotations discovered on the raw document.
     */
    private Collection<List<Annotation>> documentAnnotations;

    /**
     * Names of S4 annotation types
     */
    private Set<String> annotationTypes;

    public AnnotatedDocument getStartDocument() {
        return startDocument;
    }


    private S4DocumentToUimaCasConverter(AnnotatedDocument startDocument) {
        this.startDocument = startDocument;
        this.entities = startDocument.entities;
        this.documentAnnotations = entities.values();
        this.annotationTypes = entities.keySet();
        try {
            TypeSystemDescriptionFactory.forceTypeDescriptorsScan();
            this.tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
        } catch (ResourceInitializationException e) {
            LOG.error("Error when creating default type system", e);
        }
    }

    public static S4DocumentToUimaCasConverter newInstance(AnnotatedDocument startDocument) {
        return new S4DocumentToUimaCasConverter(startDocument);
    }

    /**
     * Method implementing the whole S4-UIMA annotation conversion logic.
     *
     * @param cas the CAS object from the Annotator component
     * @param serviceType type of the S4 service: com.ontotext.s4.api.types.sbt, com.ontotext.s4.api.types.news, com.ontotext.s4.api.types.twitie
     */
    public void convertAnnotations(JCas cas, String serviceType) {
        for (Map.Entry<String, List<Annotation>> entityEntry : entities.entrySet()) {
            String annotationName = entityEntry.getKey();
            annotationName = TypeSystemUtils.UIMA_ANNOTATION_TYPES_PACKAGE + serviceType + "." + TypeSystemUtils.removeDashes(annotationName);
            List<Annotation> annotations = entityEntry.getValue();
            for (Annotation ann : annotations) {
                casAnnotation = getFeatureStructureForS4Annotation(cas, annotationName);
                if (casAnnotation == null) {
                    LOG.warn(annotationName + " not available");
                    continue;
                }

                Type type = casAnnotation.getType();
                LOG.info(">>>>>>>>>>> Get Type -> " + type);
                casAnnotation.setBegin((int) ann.startOffset);
                casAnnotation.setEnd((int) ann.endOffset);
                Map<String, Object> features = ann.features;
                for (Map.Entry<String, Object> entry : features.entrySet()) {
                    String featureName = TypeSystemUtils.patchMatchingFeatureNamesWithUimaReservedKeywords(entry.getKey());
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
            org.apache.uima.jcas.tcas.Annotation annotation = (org.apache.uima.jcas.tcas.Annotation) Reflect.on(annotationName)
                    .create(cas)
                    .get();
            return annotation;
        } catch (ReflectException e) {
            final Throwable cause = e.getCause();
//            LOG.error(cause.toString(), e);

            //Expose real error if exception is InvocationTargetException because this exception is only a wrapper for the real one.
            if (cause instanceof InvocationTargetException) {
//                LOG.error(((InvocationTargetException) cause).getTargetException().toString(), cause);
            }
            return null;
        }
    }

    /**
     * Used for dynamic type system generation or generation of such from external sources like files with the annotations
     * and their features or something else.
     *
     * @param serviceType type of the S4 service: com.ontotext.s4.api.types.sbt, com.ontotext.s4.api.types.news, com.ontotext.s4.api.types.twitie
     * @return a TypeSystemDescription used for generation of a real CAS type system
     */
    public TypeSystemDescription inferCasTypeSystem(String serviceType) {
        for (String typeName : annotationTypes) {
            //UIMA Annotations are not allowed to contain dashes
            typeName = TypeSystemUtils.removeDashes(typeName);
            TypeDescription typeDescription = tsd.addType(TypeSystemUtils.UIMA_ANNOTATION_TYPES_PACKAGE + serviceType + "."  + typeName,
                    "Automatically generated type for " + typeName, "uima.tcas.Annotation");
            LOG.info(">>>>>>>> Inserted new type -> " + typeName);
            for (List<Annotation> annList : documentAnnotations) {
                Map<String, Object> features = annList.get(0).features;
                Set<String> featureNames = features.keySet();
                for (String feature : featureNames) {
                    feature = TypeSystemUtils.patchMatchingFeatureNamesWithUimaReservedKeywords(feature);
                    typeDescription.addFeature(feature, String.format("Feature <%s> for type <%s>", feature, typeName), "uima.cas.String");
                    LOG.info("\t\tInserted feature <{}> for type -> {}", feature, typeName);
                }
                break;
            }
        }
        return tsd;
    }
}
