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
import org.apache.uima.cas.ArrayFS;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class S4DocumentToUimaCasConverter implements UimaCasConverter {

	private static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentToUimaCasConverter.class);

    private TypeSystemDescription tsd;

    private AnnotatedDocument startDocument;

    private CAS endDocument;

    private ArrayFS annotationFeatureStructures;

    private int featureStructureArrayCapacity;

    public AnnotatedDocument getStartDocument() {
        return startDocument;
    }

    public CAS getConvertedDocument() {
        return endDocument;
    }

    public S4DocumentToUimaCasConverter(AnnotatedDocument startDocument) {
        try {
            this.tsd = TypeSystemDescriptionFactory.createTypeSystemDescription();
        } catch (ResourceInitializationException e) {
            LOG.error("Error when creating default type system", e);
        }
        this.startDocument = startDocument;
    }


    public TypeSystemDescription getTypeSystemDescription() {
        return this.tsd;
    }

    @Override
	public void convertAnnotations() {
		Map<String, List<Annotation>> entities = this.startDocument.entities;
        int featureStructureArrayIndex = 0;

        inferCasTypeSystem(entities);

        try {
            this.endDocument = CasCreationUtils.createCas(tsd, null, null);
        } catch (ResourceInitializationException e) {
            e.printStackTrace();
        }

        TypeSystem typeSystem = endDocument.getTypeSystem();

        this.featureStructureArrayCapacity = entities.size();
        this.annotationFeatureStructures = endDocument.createArrayFS(featureStructureArrayCapacity);

        for (Map.Entry<String, List<Annotation>> entityEntry : entities.entrySet()) {
            String annotationName = entityEntry.getKey();
            annotationName = removeDashes(annotationName);
            List<Annotation> annotations = entityEntry.getValue();
            for (Annotation ann : annotations) {
                Type type = typeSystem.getType(annotationName);
                LOG.info("Get Type -> " + type);
                AnnotationFS afs = endDocument.createAnnotation(type, (int)ann.startOffset, (int)ann.endOffset);
                endDocument.addFsToIndexes(afs);
                if (featureStructureArrayIndex + 1 == featureStructureArrayCapacity) {
                    resizeArrayFS(featureStructureArrayCapacity * 2, annotationFeatureStructures);
                }
                annotationFeatureStructures.set(featureStructureArrayIndex++, afs);
            }
		}
		endDocument.addFsToIndexes(annotationFeatureStructures);
	}

    public void inferCasTypeSystem(Map<String, List<Annotation>> entities) {
        for (String typeName : entities.keySet()) {
            //UIMA Annotations are not allowed to contain dashes
            typeName = removeDashes(typeName);
            tsd.addType(typeName, "Automatically generated type for " + typeName, "uima.tcas.Annotation");
            LOG.info("Inserted new type -> " + typeName);
        }
    }

    /**
     * Removes dashes from UIMA Annotations because they are not allowed to contain dashes.
     * 
     * @param typeName
     * @return
     */
    private String removeDashes(String typeName) {
        if (typeName.contains("-")) {
            typeName = typeName.replaceAll("-", "_");
        }
        return typeName;
    }

    @Override
    public void setSourceDocumentText() {
        endDocument.setSofaDataString(startDocument.text, "text/plain");
    }

    private void resizeArrayFS(int newCapacity, ArrayFS originalArray) {
        ArrayFS biggerArrayFS = endDocument.createArrayFS(newCapacity);
        biggerArrayFS.copyFromArray(originalArray.toArray(), 0, 0, originalArray.size());
        this.annotationFeatureStructures = biggerArrayFS;
        this.featureStructureArrayCapacity = annotationFeatureStructures.size();
    }
}
