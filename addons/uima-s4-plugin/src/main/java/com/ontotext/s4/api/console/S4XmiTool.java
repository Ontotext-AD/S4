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

package com.ontotext.s4.api.console;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.ontotext.s4.api.components.ComponentConfigurationParameters;
import com.ontotext.s4.api.components.casconsumer.XmiWriterCasConsumer;
import com.ontotext.s4.api.components.collectionreader.S4DocumentCollectionReader;
import com.ontotext.s4.api.restclient.S4Endpoints;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.xml.sax.SAXException;

import java.io.*;

public class S4XmiTool {

    protected static class CommandLineParams {

		@Parameter(names = {"-t", "--service-type"}, required = true, description = "Type of S4 services: NEWS, TWITIE, SBT")
		private S4Endpoints s4Endpoint;

		@Parameter(names = {"-k", "--api-key"}, required = true,
				description = "API key for for your user account, obtainable from the web interface")
		private String apiKey;

		@Parameter(names = {"-s", "--secret"}, required = true,
				description = "API password for for your user account, obtainable from the web interface")
		private String apiPassword;

		@Parameter(names = {"-d", "--source-documents-dir"}, required = true,
				description = "File path with the raw text documents to be processed")
		private String rawTextFilePath;

		@Parameter(names = { "--descriptor-dir"}, required = false,
				description = "If provided, a directory where descriptors will be written")
		private String descriptorDir = null;

		@Parameter(names = { "-o", "--xmi-output-dir"}, required = true,
				description = "The directory where the XMI files produced will be written")
		private String xmiDir;

        @Parameter(names = { "-h", "--help", "" }, help = true, description = "Display this help text")
        private boolean help;
    }

	private static final String USAGE_DESC = String.format(
            "Instantiate a basic pipeline using uimaFIT which calls S4 text analytics services\n" +
            "and translates their output to native UIMA  datastructures.Then it serializes the UIMA components\n" +
            "and the document annotations as XMI files in the requested directory. If --descriptor-dir \n" +
			"is set, the descriptors for the collection reader, analysis engine \n" +
			"and type system will be written to that directory.");


	public static void main(String[] args) throws IOException, UIMAException, SAXException {

		CommandLineParams params = new CommandLineParams();
		JCommander commander = new JCommander(params, args);
		commander.setProgramName(S4XmiTool.class.getName());
		if (params.help) {
			System.err.println(USAGE_DESC);
			commander.usage();
			return;
		}
		runPipeline(params.s4Endpoint, params.apiKey, params.apiPassword, params.rawTextFilePath, params.descriptorDir, params.xmiDir);
	}

	private static void runPipeline(S4Endpoints s4Endpoint, String apiKeyId, String apiPassword, String rawTextFilePath, String descriptorDir, String xmiDir)
            throws UIMAException, IOException, SAXException {
        ComponentConfigurationParameters readerParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(S4DocumentCollectionReader.PARAM_S4_SERVICE_ENDPOINT, s4Endpoint.toString())
                .withConfigParameter(S4DocumentCollectionReader.PARAM_S4_API_KEY_ID, apiKeyId)
                .withConfigParameter(S4DocumentCollectionReader.PARAM_S4_API_PASSWORD, apiPassword)
                .withConfigParameter(S4DocumentCollectionReader.PARAM_SOURCE_TEXT_FILE_PATH, rawTextFilePath);

        CollectionReaderDescription reader = new S4DocumentCollectionReader()
                .createDescription(readerParameters.getParametersArray());

		AnalysisEngineDescription casWriter = AnalysisEngineFactory.createEngineDescription(
				XmiWriterCasConsumer.class, XmiWriterCasConsumer.PARAM_OUTPUT_DIR, xmiDir);
		casWriter.getAnalysisEngineMetaData().setTypeSystem(reader.getCollectionReaderMetaData().getTypeSystem());
		if (descriptorDir != null) {
			OutputStream readerOS = new BufferedOutputStream(new FileOutputStream(
					new File(descriptorDir, "S4DocumentCollectionReader.xml")));
			reader.toXML(readerOS);
			OutputStream tsOS = new BufferedOutputStream(new FileOutputStream(
					new File(descriptorDir, "typesystem-full.xml")));
			reader.getCollectionReaderMetaData().getTypeSystem().toXML(tsOS);
			OutputStream cwOS = new BufferedOutputStream(new FileOutputStream(
					new File(descriptorDir, "S4DocumentsToXmiAE.xml")));
			casWriter.toXML(cwOS);
		}
		SimplePipeline.runPipeline(reader, casWriter);
	}
}
