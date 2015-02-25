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

/**
 * An CAS converter transforming a S4 document into a UIMA CAS document.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * <p/>
 * Date added: 2015-02-19
 */
public class S4DocumentToUimaCasConverter implements UimaCasConverter {

    private static final Logger LOG = LoggerFactory
            .getLogger(S4DocumentToUimaCasConverter.class);

    private TypeSystemDescription tsd;

    private AnnotatedDocument startDocument;

    private ArrayFS annotationFeatureStructures;

    private int featureStructureArrayCapacity;

    public AnnotatedDocument getStartDocument() {
        return startDocument;
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
    public void convertAnnotations(CAS cas) {
        Map<String, List<Annotation>> entities = this.startDocument.entities;
        int featureStructureArrayIndex = 0;

        inferCasTypeSystem(entities.keySet());
        try {
            /*
             * This is a hack allowing the CAS object to have an updated type system.
             * We are creating a new CAS by passing the new TypeSystemDescription which actually
             * should have been updated by an internal call of typeSystemInit(cas.getTypeSystem())
             * originally part of the CasInitializer interface that is now deprecated and the CollectionReader
             * is calling it internally in its implementation. The problem consists in the fact that now the
             * the typeSystemInit method of the CasInitializer_ImplBase has an empty implementation and
             * nothing changes!
             */
            LOG.info("Creating new CAS with updated typesystem...");
            cas = CasCreationUtils.createCas(tsd, null, null);
        } catch (ResourceInitializationException e) {
            LOG.info("Error creating new CAS!", e);
        }

        TypeSystem typeSystem = cas.getTypeSystem();

        this.featureStructureArrayCapacity = entities.size();
        this.annotationFeatureStructures = cas.createArrayFS(featureStructureArrayCapacity);

        for (Map.Entry<String, List<Annotation>> entityEntry : entities.entrySet()) {
            String annotationName = entityEntry.getKey();
            annotationName = removeDashes(annotationName);
            Type type = typeSystem.getType(annotationName);

            List<Annotation> annotations = entityEntry.getValue();
            LOG.info("Get Type -> " + type);
            for (Annotation ann : annotations) {
                AnnotationFS afs = cas.createAnnotation(type, (int)ann.startOffset, (int)ann.endOffset);
                cas.addFsToIndexes(afs);
                if (featureStructureArrayIndex + 1 == featureStructureArrayCapacity) {
                    resizeArrayFS(featureStructureArrayCapacity * 2, annotationFeatureStructures, cas);
                }
                annotationFeatureStructures.set(featureStructureArrayIndex++, afs);
            }
        }
        cas.addFsToIndexes(annotationFeatureStructures);
    }

    @Override
    public void inferCasTypeSystem(Iterable<String> originalTypes) {
        for (String typeName : originalTypes) {
            //UIMA Annotations are not allowed to contain dashes
            typeName = removeDashes(typeName);
            tsd.addType(typeName, "Automatically generated type for " + typeName, "uima.tcas.Annotation");
            LOG.info("Inserted new type -> " + typeName);
        }
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

    @Override
    public void setSourceDocumentText(CAS cas) {
        cas.setSofaDataString(startDocument.text, "text/plain");
    }

    private void resizeArrayFS(int newCapacity, ArrayFS originalArray, CAS cas) {
        ArrayFS biggerArrayFS = cas.createArrayFS(newCapacity);
        biggerArrayFS.copyFromArray(originalArray.toArray(), 0, 0, originalArray.size());
        this.annotationFeatureStructures = biggerArrayFS;
        this.featureStructureArrayCapacity = annotationFeatureStructures.size();
    }
}
