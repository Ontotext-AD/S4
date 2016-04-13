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

package com.ontotext.s4.api.util;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.tools.jcasgen.Jg;
import org.apache.uima.util.CasCreationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

/**
 * A utilities class giving functionality for generating and regenerating UIMA type system components as java classes and xml
 * descriptors.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-03-18
 */
public class TypeSystemUtils {

    private static final Logger LOG = LoggerFactory.getLogger(TypeSystemUtils.class);

    private static final String SOURCES_DIR = "src/main/java";

    public static final String UIMA_ANNOTATION_TYPES_PACKAGE = "com.ontotext.s4.api.types.";

    /**
     * Generate type system description file.
     *
     * @param descriptorDir output directory of the file
     * @param analysisEngineDesc analysis engine descritor object used for serializing its in-memory type system to xml
     * @param serviceType S4 service type: sbt, news, twitie
     * @return path to type system descriptor file
     */
    public static String generateTypeSystemFile(String descriptorDir, AnalysisEngineDescription analysisEngineDesc, String serviceType) {
        String typeSystemFilePath = descriptorDir + File.separator + serviceType + "_typesystem.xml";
        OutputStream typeSystemOs = null;
        try {
            typeSystemOs = new FileOutputStream(
                    new File(typeSystemFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LOG.info("Creating typesystem file at -> " + typeSystemFilePath);
        try {
            analysisEngineDesc.getAnalysisEngineMetaData().getTypeSystem().toXML(typeSystemOs);
        } catch (SAXException | IOException e) {
            LOG.error("Cannot serialize type system to XML", e);
        }
        LOG.info("Typesystem file is created!");

        return typeSystemFilePath;
    }

    /**
     * Generate UIMA type system classes using jcasgen tool.
     *
     * @param typeSystemFilePath path to type system xml descriptor
     * @param outputDir output directory for the java classes
     */
    public static void generateJCasGenClasses(String typeSystemFilePath, String... outputDir) {
        String srcDir = Preconditions.isNullOrEmpty(outputDir[0]) ? outputDir[0] : SOURCES_DIR;
        Jg jCasGen = new Jg();
        LOG.info(">>>>>>>>> Generating java type classes corresponding to typesystem from <{}>...", typeSystemFilePath);
        jCasGen.main1(new String[]{"-jcasgeninput", typeSystemFilePath, "-jcasgenoutput", srcDir});
        LOG.info("Type system classes are generated!!!");
    }

    /**
     * Read csv file.
     *
     * @param input file content
     * @param fieldSeparator separator of the csv fields
     * @param fieldEnclosedBy enclosing field character
     * @return a list of csv rows
     * @throws IOException
     */
    public static List<String[]> readCsv(String input, char fieldSeparator, char fieldEnclosedBy) throws IOException {
        if (!Preconditions.isNullOrEmpty(input)) {
            List<String[]> csvRow;
            try (CSVReader reader = new CSVReader(new StringReader(input), fieldSeparator, fieldEnclosedBy)) {
                csvRow = reader.readAll();
            }
            return csvRow;
        }

        return null;
    }

    /**
     * Create a {@code TypeSystemDescription} object used for setting a type system to an {@code AnalysisEngineDescription}
     * @param annotationTypes list of table rows containing the annotation name and annotation features
     * @param serviceType S4 service type: sbt, news, twitie
     * @return {@code TypeSystemDescription} object used for setting a type system to an {@code AnalysisEngineDescription}
     */
    public static TypeSystemDescription createTypeSystemDescription(List<String[]> annotationTypes, String serviceType) {
        TypeSystemDescription typeSystemDescription;
        try {
            TypeSystemDescriptionFactory.forceTypeDescriptorsScan();
            typeSystemDescription = TypeSystemDescriptionFactory.createTypeSystemDescription();
        } catch (ResourceInitializationException e) {
            e.printStackTrace();
            return null;
        }
        for (String[] typeData : annotationTypes) {
            String typeName = typeData[0];
            typeName = UIMA_ANNOTATION_TYPES_PACKAGE + serviceType + "." + removeDashes(typeName);
            TypeDescription typeDescription = typeSystemDescription.addType(typeName,
                    "Automatically generated type for " + typeName, "uima.tcas.Annotation");
            LOG.info(">>>>>>>> Inserted new type -> " + typeName);
            for (int i = 1; i < typeData.length ; i++) {
                String feature = typeData[i];
                if (!Preconditions.isNullOrEmpty(feature)) {
                    feature = patchMatchingFeatureNamesWithUimaReservedKeywords(feature);
                    typeDescription.addFeature(feature, String.format("Feature <%s> for type <%s>", feature, typeName), "uima.cas.String");
                    LOG.info("\t\tInserted feature <{}> for type -> {}", feature, typeName);
                }
            }
        }

        return typeSystemDescription;
    }

    /**
     * Generate type system dynamically based on raw documents that you feed it.
     *
     * @param descriptorDir type system descriptor xml file output directory
     * @param readerDesc {@code CollectionReaderDescription} object
     * @param annotatorDesc {@code AnalysisEngineDescription} object
     * @param serviceType S4 service type: sbt, news, twitie
     * @param cachedTypeSystemDescriptions list of aggregated {@code TypeSystemDescription}s which are later merged into
     *                                     one in order to get one {@code TypeSystemDescription} with aggregated types
     * @throws UIMAException
     * @throws IOException
     * @throws SAXException
     */
    public static void generateTypeSystemDynamically(String descriptorDir, CollectionReaderDescription readerDesc,
                                                     AnalysisEngineDescription annotatorDesc, String serviceType, List<TypeSystemDescription> cachedTypeSystemDescriptions)
            throws UIMAException, IOException, SAXException {
        CollectionReader reader = CollectionReaderFactory.createReader(readerDesc);
        AnalysisEngine annotator = AnalysisEngineFactory.createEngine(annotatorDesc);

        SimplePipeline.runPipeline(reader, annotator);

        final TypeSystemDescription fullTypeSystemDescription = CasCreationUtils
                .mergeTypeSystems(cachedTypeSystemDescriptions);
        annotatorDesc.getAnalysisEngineMetaData().setTypeSystem(fullTypeSystemDescription);

        String typeSystemFilePath = generateTypeSystemFile(descriptorDir, annotatorDesc, serviceType);

        generateJCasGenClasses(typeSystemFilePath);
    }

    /**
     * A method fixing feature names matching UIMA reserved keywords like "class" or "type".
     *
     * @param feature the feature name to be patched
     * @return the new pathched feature name
     */
    public static String patchMatchingFeatureNamesWithUimaReservedKeywords(String feature) {
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
    public static String removeDashes(String typeName) {
        if (typeName.contains("-")) {
            typeName = typeName.replaceAll("-", "_");
        }
        return typeName;
    }
}
