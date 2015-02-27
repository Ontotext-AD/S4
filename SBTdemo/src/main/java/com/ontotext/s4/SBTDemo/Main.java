/*
 * Licensed to the Ontotext under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Ontotext licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ontotext.s4.SBTDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;
import org.openrdf.model.Model;
import org.openrdf.repository.RepositoryException;

import com.ontotext.s4.SBTDemo.graphDB.RepoManager;
import com.ontotext.s4.SBTDemo.parse.JsonToRDF;
import com.ontotext.s4.SBTDemo.processingData.ProcessingDocuments;
import com.ontotext.s4.SBTDemo.utils.PropertiesNames;

public class Main {

	static Logger logger = Logger.getLogger(Main.class);
	static Collection<File> listOfAllAnnotatedFiles;
	static Properties programProperties;

	public static final String DEAFULT_PROPERTIES_FILE = "app.properties";
	public static final String DEFAULT_LOG4J_FILE = "log4j.properties";

	public static void main(String[] args) {
		/*
		 * Read log4j properties file.
		 */
		org.apache.log4j.PropertyConfigurator
				.configure(args.length >= 1 ? args[1] : DEFAULT_LOG4J_FILE);

		/*
		 * Read properties file  List all annotated files. We should
		 * use absolute path to the files
		 */
		init(args);
		
		ProcessingDocuments processingDocuments = new ProcessingDocuments(
				programProperties.getProperty(PropertiesNames.S4_API_KEY),
				programProperties.getProperty(PropertiesNames.S4_API_PASS),
				programProperties.getProperty(PropertiesNames.RAW_FOLDER),
				programProperties.getProperty(PropertiesNames.ANNOTATED_FOLDER),
				programProperties.getProperty(PropertiesNames.SERVICE),
				programProperties.getProperty(PropertiesNames.MIME_TYPE),
				programProperties
						.getProperty(PropertiesNames.RESPONSE_FORMAT),
				Integer.parseInt(programProperties.getProperty(PropertiesNames.NUMBER_OF_THREADS)));

		processingDocuments.ProcessData();
		
		File directory = new File(
				programProperties.getProperty(PropertiesNames.ANNOTATED_FOLDER));
		listOfAllAnnotatedFiles = FileUtils.listFiles(directory, new RegexFileFilter(
				"^(.*?)"), DirectoryFileFilter.DIRECTORY);
		
		RepoManager repoManager = new RepoManager(
				programProperties.getProperty(PropertiesNames.REPOSITORY_URL));
		JsonToRDF jsonToRdfParser = new JsonToRDF(
				programProperties.getProperty(PropertiesNames.MIME_TYPE),programProperties.getProperty(PropertiesNames.RDFIZE_FOLDER));

		
		
		for (File file : listOfAllAnnotatedFiles) {
			String fileContent = null;
			try {
				fileContent = FileUtils.readFileToString(file, "UTF-8");
			} catch (IOException e) {
				logger.error(e);
			}
			
			Model graph=jsonToRdfParser.wirteDataToRDF(fileContent,
					file.getName(),programProperties.getProperty(PropertiesNames.RDFIZE_FOLDER));
			try {
				repoManager.sendDataTOGraphDB(graph);
			} catch (RepositoryException e) {
				logger.error(e);
			}
			
		}
		
		repoManager.close();
	}

	private static void init(String[] args) {
		logger.info("Initializing properties");
		programProperties = new Properties();
		InputStream is = null;
		if (args.length == 0) {
			try {
				is = new FileInputStream("app.properties");
			} catch (FileNotFoundException e) {
				logger.error(e);
			}
		} else {
			try {
				is = new FileInputStream(args[0]);
			} catch (FileNotFoundException e) {
				logger.error(e);
			}
		}
		try {
			programProperties.load(is);
		} catch (IOException e) {
			logger.error("Error loading app.properties. " + e);
		}
	}

}
