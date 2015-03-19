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

package com.ontotext.s4.api.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.ontotext.s4.api.annotator.uimafit.S4DocumentUimaFitAnnotator;
import com.ontotext.s4.api.example.components.FileSystemCollectionReader;
import com.ontotext.s4.api.example.components.XmiWriterCasConsumer;
import com.ontotext.s4.api.restclient.S4Endpoints;
import com.ontotext.s4.api.util.ComponentConfigurationParameters;
import com.ontotext.s4.api.util.TypeSystemUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * An example pipeline representing a typical processing scenario of having the following components:
 * <ul>
 *     <li>{@code FileSystemCollectionReader}</li>
 *     <li>{@code S4DocumentUimaFitAnnotator}</li>
 *     <li>{@code XmiWriterCasConsumer}</li>
 * </ul>
 *
 * NOTE: This pipeline can be launched through the console using the {@code s4pipeline} console tool found at src/main/scripts.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-19
 */
public class S4UimaPipeline {

    private static final Logger LOG = LoggerFactory.getLogger(S4UimaPipeline.class);

    private static class CommandLineParams {

        @Parameter(names = {"-t", "--service-type"}, required = true, description = "Type of S4 services: NEWS, TWITIE, SBT")
        private S4Endpoints s4Endpoint;

        @Parameter(names = {"-k", "--api-key"}, required = true,
                description = "API key for for your user account, obtainable from the web interface")
        private String apiKey;

        @Parameter(names = {"-s", "--secret"}, required = true,
                description = "API password for for your user account, obtainable from the web interface")
        private String apiPassword;

        @Parameter(names = {"-i", "--input-dir"}, required = true,
                description = "File path with the raw text documents to be processed")
        private String inputDir;

        @Parameter(names = {"-o", "--output-dir"}, required = true,
                description = "The directory where the XMI files produced will be written")
        private String outputDir;

        @Parameter(names = {"--descriptor-dir"}, required = false,
                description = "If provided, a directory where descriptors will be written")
        private String descriptorDir = "desc";

        @Parameter(names = {"--generate-typesystem"}, required = false,
                description = "Dynamically generate type system as xml descriptors and java classes for the types.\n" +
                        "\tWARNING: Use only if you think the current type system is not up \tcd to date with the S4 services.")
        private String generateTypesystem = "false";

        @Parameter(names = {"-h", "--help"}, help = true, description = "Display this help text")
        private boolean help;
    }

    private static final String USAGE_DESC =
            "Instantiate a basic pipeline using uimaFIT which calls S4 text analytics services\n" +
            "and translates their output to native UIMA  datastructures. Then it serializes the UIMA components\n" +
            "and the document annotations as XMI files in the requested directory. If --descriptor-dir \n" +
            "is set, the descriptors for the collection reader, analysis engines \n" +
            "and type systems will be written to that directory.\n";


    public static void main(String[] args) throws Exception {
        CommandLineParams params = new CommandLineParams();
        JCommander commander = new JCommander(params, args);
        commander.setProgramName(S4UimaPipeline.class.getName());
        if (params.help) {
            System.out.println(USAGE_DESC);
            commander.usage();
            return;
        }

        runPipeline(params.s4Endpoint,
                params.apiKey,
                params.apiPassword,
                params.inputDir,
                params.outputDir,
                params.descriptorDir,
                params.generateTypesystem);
    }

    private static void runPipeline(S4Endpoints s4Endpoint, String apiKeyId, String apiPassword, String rawTextFilePath, String xmiDir, String descriptorDir, String generateTypeSystemFlag)
            throws Exception {
        ComponentConfigurationParameters readerParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(FileSystemCollectionReader.PARAM_SOURCE_TEXT_FILE_PATH, rawTextFilePath);

        ComponentConfigurationParameters annotatorParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_SERVICE_ENDPOINT, s4Endpoint.toString())
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_API_KEY_ID, apiKeyId)
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_API_PASSWORD, apiPassword)
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_GENERATE_TYPESYSTEM, generateTypeSystemFlag);

        ComponentConfigurationParameters xmiWriterParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(XmiWriterCasConsumer.PARAM_OUTPUT_DIR, xmiDir);

        CollectionReaderDescription readerDesc = CollectionReaderFactory
                .createReaderDescription(FileSystemCollectionReader.class, readerParameters.getParametersArray());

        AnalysisEngineDescription annotatorDesc = AnalysisEngineFactory
                .createEngineDescription(S4DocumentUimaFitAnnotator.class, annotatorParameters.getParametersArray());

        if (generateTypeSystemFlag.equals("true")) {
            final String serviceType = s4Endpoint.toString().substring(s4Endpoint.toString().lastIndexOf("/"));
            TypeSystemUtils.generateTypeSystemDynamically(
                    descriptorDir, readerDesc, annotatorDesc, serviceType,
                    S4DocumentUimaFitAnnotator.cachedTypeSystemDescriptions);
            return;
        }

        AnalysisEngineDescription casWriter = AnalysisEngineFactory.createEngineDescription(
                XmiWriterCasConsumer.class, xmiWriterParameters.getParametersArray());

        AggregateBuilder builder = new AggregateBuilder();
        builder.add(annotatorDesc);
        builder.add(casWriter);

        OutputStream annotatorOs = new FileOutputStream(
                new File(descriptorDir, S4DocumentUimaFitAnnotator.class.getSimpleName() + ".xml"));
        annotatorDesc.getAnalysisEngineMetaData().toXML(annotatorOs);

        SimplePipeline.runPipeline(readerDesc, builder.createAggregateDescription());
    }
}
