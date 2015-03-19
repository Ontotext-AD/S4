/*
 * Copyright (c) 2014
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ontotext.s4.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontotext.s4.classifier.ClassifierDataModel;
import com.ontotext.s4.service.ResponseFormat;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;

import gate.Document;
import gate.Factory;
import gate.ProcessingResource;
import gate.Resource;
import gate.corpora.DocumentContentImpl;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;

/**
 * Main entry point for the S4 GATE PR plug-in.
 * 
 * @author YavorPetkov
 */
public class S4Plugin extends AbstractLanguageAnalyser implements
		ProcessingResource, Serializable {
	private static final long serialVersionUID = -1754780486191588663L;
	/**
	 * Only for debugging.
	 */
	private Boolean debug = false;

	/**
	 * This are the names of an api key and an api pass into the config file.
	 */
	private static final String apiKeyString = "apiKey";
	private static final String apiPassString = "apiPass";

	/**
	 * The API Client
	 */
	private S4ServiceClient s4ServiceClient;

	/**
	 * The URL of the S4 REST service
	 */
	private S4Endpoints url;

	/**
	 * The URL of the REST Service. Use this only if we change S4 rest API
	 * endpoints.
	 */
	private String customUrl;

	// config file
	/**
	 * Configuration file with apiKey and apiPass
	 */
	private URL configFileURL;

	/**
	 * apiKey and apiPass
	 */
	private Properties authenticationProperties;

	/**
	 * Initialize S4 Gate PR
	 */
	public Resource init() throws ResourceInstantiationException {

		// debug
		if (getDebug() == true) {
			System.out.println(getConfigFileURL().getFile());
		}

		// check configuration file
		try {
			authenticationProperties = new Properties();
			authenticationProperties.load(new FileInputStream(
					getConfigFileURL().getFile()));
		} catch (Exception e) {
			throw new ResourceInstantiationException(
					"Configuration file not found (default \"S4.config\")", e);
		}
		return this;
	}

	/**
	 * The execute method
	 */
	public void execute() throws ExecutionException {

		// get document
		String text = ((DocumentContentImpl) getDocument().getContent())
				.toString();

		if (text.trim().isEmpty()) {
			return;
		}

		// check if Service Client is loaded
		if (s4ServiceClient == null) {
			try {
				s4ServiceClient = new S4ServiceClient(getServiceURL(),
						authenticationProperties.getProperty(apiKeyString),
						authenticationProperties.getProperty(apiPassString));
			} catch (MalformedURLException e) {
				throw new ExecutionException(e);
			}
		}

		InputStream responseStream;

		// debug
		if (getDebug() == true) {
			System.out.println("Send " + getDocument().getName()
					+ " for processing.");
		}

		// send the document for processing
		responseStream = s4ServiceClient.annotateDocumentAsStream(text,
				SupportedMimeType.PLAINTEXT, ResponseFormat.GATE_XML);

		String response = null;
		try {
			response = copyToString(responseStream);
		} catch (IOException e) {
			throw new ExecutionException(e);
		}

		try {
			parseJSON(response, document);
			return;
		} catch (Exception e) {
			if(getDebug()==true){
				System.out.println(e);
			}
		}
		// debug
		if (getDebug() == true) {
			System.out.println("Response " + response);
			System.out
					.println("Copy annotations to " + getDocument().getName());
		}

		// copy annotations
		try {
			copyAnnotations(response, document);
		} catch (ResourceInstantiationException e) {
			throw new ExecutionException(e);
		}
	}

	private void parseJSON(String response, Document document) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ClassifierDataModel classifer = mapper.readValue(response.getBytes(),
				new TypeReference<ClassifierDataModel>() {
				});
		document.getFeatures().put("CATEGORY", classifer.getCategory());
		document.getFeatures().put("allCategoryScores", classifer.getScoreAsMap());
		
		if (debug == true) {
			System.out.println(classifer.getAllScores());
		}
	}

	/**
	 * Reinitialize the Processing Resource
	 */
	@Override
	public void reInit() throws ResourceInstantiationException {
		s4ServiceClient = null;
		init();
	}

	/**
	 * 
	 * @param response
	 *            - gate/xml from where we want to copy annotations
	 * @param document
	 *            - gate document where we want to add annotations
	 * @throws ResourceInstantiationException
	 *             - if we can't create tempDoc
	 */
	private void copyAnnotations(String response, Document document)
			throws ResourceInstantiationException {

		// create temporary doc
		Document tempDoc = (Document) Factory.newDocument(response);

		// get all annotations sets names
		Set<String> annotationSetNames = tempDoc.getAnnotationSetNames();

		// copy 'default' annotations
		document.getAnnotations().addAll(tempDoc.getAnnotations());

		document.getFeatures().putAll(tempDoc.getFeatures());

		for (String annotationSetName : annotationSetNames) {
			// check Annotations set already exists
			if (document.getNamedAnnotationSets()
					.containsKey(annotationSetName)) {
				// this means that it exists so we just copy new annotations
				// into old set
				document.getAnnotations(annotationSetName).addAll(
						tempDoc.getAnnotations(annotationSetName));
			} else {
				// add new annotations set name and new annotations set
				document.getNamedAnnotationSets().put(annotationSetName,
						tempDoc.getAnnotations(annotationSetName));
			}
		}

		// remove temporary document
		Factory.deleteResource(tempDoc);

	}

	/**
	 * Choose what source to use for the rest service URL.
	 * 
	 * @return String with the S4 rest service URL.
	 * @throws MalformedURLException
	 */
	private URL getServiceURL() throws MalformedURLException {
		return getCustomUrl() != null ? new URL(getCustomUrl()) : new URL(
				getUrl().toString());
	}

	/**
	 * Convert InputStream into string
	 * 
	 * @param is
	 *            input stream with text data
	 * @return String that contains text data from InputStream
	 * @throws IOException
	 *             can't copy text data
	 */
	private String copyToString(InputStream is) throws IOException {
		StringWriter writer = new StringWriter();

		IOUtils.copy(is, writer, "UTF-8");

		return writer.toString();

	}

	public S4Endpoints getUrl() {
		return url;
	}

	public void setUrl(S4Endpoints s4url) {
		url = s4url;
	}

	public URL getConfigFileURL() {
		return configFileURL;
	}

	public void setConfigFileURL(URL configFileURL) {
		this.configFileURL = configFileURL;
	}

	public String getCustomUrl() {
		return customUrl;
	}

	public void setCustomUrl(String customUrl) {
		this.customUrl = customUrl;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}
}
