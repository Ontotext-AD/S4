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
package com.ontotext.s4.SBTDemo.parse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.Rio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonToRDF {
	Logger logger = Logger.getLogger(JsonToRDF.class);

	public static final String RDF_TYPE_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	public static final String CITATION_URI = "http://linkedlifedata.com/resource/pubmed/Citation";
	public static final String SBT_DEMO_URI = "http://s4.ontotext.com/demos/SBT/";
	public static final String URI_START_WITH = "http";
	public static final String MENTIONS_URI = "http://linkedlifedata.com/resource/lifeskim/mentions";
	public static final String LABEL_URI = "http://www.w3.org/2000/01/rdf-schema#label";
	public static final String ABSTRACT_TEXT_URI = "http://linkedlifedata.com/resource/pubmed/abstractText";
	public static final int SUBJECT = 0;
	public static final int PREDICATE = 1;
	public static final int OBJECT = 2;

	public static final String entity = "entities";
	public static final String textJson = "text";
	public static final String inst = "inst";
	public static final String classProperty = "class";
	public static final String stringProperty = "string";
	public static final String type = "type";

	public static boolean writeToFile;

	RDFFormat format;
	FileOutputStream out;

	public JsonToRDF(String mimeType, String rdfDataFolder) {
		if (mimeType != null) {
			format = RDFFormat.NTRIPLES;
		} else {
			format = RDFFormat.forMIMEType(mimeType);
		}

		try {
			out = new FileOutputStream(rdfDataFolder + '/' + "rdfFile.nt");
		} catch (FileNotFoundException e) {
			logger.error(e);
		}
	}

	public Model wirteDataToRDF(String fileContent, String fileName,
			String rdfDataFolder) {
		if (fileContent == "") {
			return new LinkedHashModel();
		}
		String fileSubject = SBT_DEMO_URI + fileName;

		Model graph = new LinkedHashModel();
		try {
			List<String[]> jsonObject = parse(fileContent, fileSubject);
			ValueFactory factory = ValueFactoryImpl.getInstance();
			for (String[] arr : jsonObject) {
				try {
					URI subjectURI = factory.createURI(arr[SUBJECT]);
					URI predicateURI = factory.createURI(arr[PREDICATE]);
					Value objectURI = arr[OBJECT].startsWith(URI_START_WITH) ? factory
							.createURI(arr[OBJECT]) : factory
							.createLiteral(arr[OBJECT]);
					Statement nameStatement = factory.createStatement(
							subjectURI, predicateURI, objectURI);
					graph.add(nameStatement);
				} catch (NullPointerException e) {
					logger.error(e);
				}

			}

			graph.add(factory.createURI(fileSubject),
					factory.createURI(RDF_TYPE_URI),
					factory.createURI(CITATION_URI));
			
			writeIntoFile(graph,fileName,rdfDataFolder);
		} catch (Exception e) {
			logger.error(e);
		}
		appendToFile(graph);
		return graph;
	}

	/**
	 * This method should create
	 * 
	 * @param jsonLine
	 * @param fileSubject
	 * @return
	 */
	public List<String[]> parse(String jsonLine, String fileSubject)
			throws Exception {
		List<String[]> hashList = new LinkedList<String[]>();

		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		String text = null;
		if (jobject.has(textJson)) {
			text = jobject.getAsJsonPrimitive(textJson).getAsString();
		}
		if (jobject.has(entity)) {

			JsonObject entities = jobject.getAsJsonObject(entity);
			for (Iterator<Entry<String, JsonElement>> iterator = entities
					.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, JsonElement> element = iterator.next();
				JsonArray jsonArrayOfEntities = element.getValue()
						.getAsJsonArray();
				for (JsonElement jsonElement : jsonArrayOfEntities) {
					JsonObject annotation = jsonElement.getAsJsonObject();
					try {
						String[] fileMentionsArray = new String[] {
								fileSubject,
								MENTIONS_URI,
								annotation.getAsJsonPrimitive(inst)
										.getAsString() };
						String[] instanceTypeArray = new String[] {
								annotation.getAsJsonPrimitive(inst)
										.getAsString(),
								RDF_TYPE_URI,
								annotation.getAsJsonPrimitive(classProperty)
										.getAsString() };
						String[] instanceLabelArray = new String[] {
								annotation.getAsJsonPrimitive(inst)
										.getAsString(),
								LABEL_URI,
								annotation.getAsJsonPrimitive(stringProperty)
										.getAsString() };
						String[] classLableArray = new String[] {
								annotation.getAsJsonPrimitive(classProperty)
										.getAsString(),
								LABEL_URI,
								annotation.getAsJsonPrimitive(type)
										.getAsString().replaceAll("_", " ") };
						String[] stringAbstractText = new String[] {
								fileSubject, ABSTRACT_TEXT_URI, text };

						hashList.add(fileMentionsArray);
						hashList.add(instanceTypeArray);
						hashList.add(classLableArray);
						hashList.add(instanceLabelArray);
						hashList.add(stringAbstractText);

					} catch (Exception e) {
						logger.error(e);
					}

				}

			}

		}
		return hashList;
	}

	private void writeIntoFile(Model model, String fileName,
			String rdfDataFolder) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(rdfDataFolder + '/' + fileName);
		} catch (FileNotFoundException e) {
			logger.error(e);
		}

		try {
			Rio.write(model, out, format);
		} catch (RDFHandlerException e) {
			logger.error(e);
		}

	}

	private void appendToFile(Model model) {

		try {
			Rio.write(model, out, format);
		} catch (RDFHandlerException e) {
			logger.error(e);
		}

	}
}
