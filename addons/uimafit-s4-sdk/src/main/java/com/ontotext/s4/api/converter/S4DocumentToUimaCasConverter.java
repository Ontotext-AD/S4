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
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.joor.Reflect;
import org.joor.ReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static final String UIMA_ANNOTATION_TYPES_PACKAGE = "com.ontotext.s4.api.uima.types.";

    private TypeSystemDescription tsd;

    private AnnotatedDocument startDocument;

    private org.apache.uima.jcas.tcas.Annotation casAnnotation;

    private Map<String, List<Annotation>> entities;

    private Collection<List<Annotation>> documentAnnotations;

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
                    Object value = entry.getValue();
                    String stringValue;
                    if (value != null) {
                        stringValue = value.toString();
                    } else {
                        continue;
                    }

                    if (feature == null) {
                        continue;
                    }
                    casAnnotation.setFeatureValueFromString(feature, stringValue);
                }
                cas.addFsToIndexes(casAnnotation);
            }
        }
    }

    private org.apache.uima.jcas.tcas.Annotation getFeatureStructureForS4Annotation(JCas cas, String annotationName) {
        // use reflection to instantiate classes of the proper type in the type system
        // usually jcas gen creates the constructor with jcas argument as the second one
        try {
            return (org.apache.uima.jcas.tcas.Annotation) Reflect.on(annotationName)
                    .create(cas)
                    .get();
        } catch (ReflectException e) {
            LOG.error(e.getCause().toString(), e);
            return null;
        }
    }

    public TypeSystemDescription inferCasTypeSystem(String serviceType) {
        for (String typeName : annotationTypes) {
            //UIMA Annotations are not allowed to contain dashes
            typeName = removeDashes(typeName);
            TypeDescription typeDescription = tsd.addType(UIMA_ANNOTATION_TYPES_PACKAGE + serviceType + "."  + typeName,
                    "Automatically generated type for " + typeName, "uima.tcas.Annotation");
            LOG.info(">>>>>>>> Inserted new type -> " + typeName);
            for (List<Annotation> annList : documentAnnotations) {
                Map<String, Object> features = annList.get(0).features;
                Set<String> featureNames = features.keySet();
                for (String feature : featureNames) {
                    feature = patchMatchingFeatureNamesWithUimaReservedKeywords(feature);
                    typeDescription.addFeature(feature, String.format("Feature <%s> for type <%s>", feature, typeName), "uima.cas.String");
                    LOG.info("\t\tInserted feature <{}> for type -> {}", feature, typeName);
                }
                break;
            }
        }
        return tsd;
    }

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
