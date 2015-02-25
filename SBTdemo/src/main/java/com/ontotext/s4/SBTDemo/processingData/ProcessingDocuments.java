/** Self-Service Semantic Suite (S4)
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.ontotext.s4.SBTDemo.processingData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.catalog.ServicesCatalog;
import com.ontotext.s4.service.ResponseFormat;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;


/**
 * Read tweets from the 'rawTweetsFolder'.Send them for processing to S4.
 * Save them into the 'processedTweetsFolder'(one file per tweet).
 */
public class ProcessingDocuments {
	Logger logger = Logger.getLogger(ProcessingDocuments.class);

	private String rawDataFolder;
	private String annotatedDataFolder;
	private String apiKey;
	private String apiPass;

	/**
	 * 
	 * @param apiKey
	 *            API key for S4
	 * @param apiPass
	 *            API pass for S4
	 * @param rawTweetsFolder
	 *            path to the raw tweets folder
	 * @param processedTweetsFolder
	 *            path to the processed tweets folder
	 */
	public ProcessingDocuments(String apiKey, String apiPass,
			String rawTweetsFolder, String processedTweetsFolder) {
		this.apiKey = apiKey;
		this.apiPass = apiPass;
		this.rawDataFolder = rawTweetsFolder;
		this.annotatedDataFolder = processedTweetsFolder;
	}

	/**
	 * Process all documents into the raw tweets folder. Save processed tweets
	 * into the processed tweets Folder.
	 */
	public void ProcessData() {

		// get all documents in a folder
		File f = new File(rawDataFolder);
		File[] documents = f.listFiles();

		//
		if (documents == null) {
			logger.info("There are no files to process");
			return;
		}

		// the output
		File results = new File(annotatedDataFolder);
		if (!results.exists()) {
			results.mkdirs();
		}

		Writer w = null;
		for (File document : documents) {
			try {

				if (document.isDirectory() || !document.canRead()) {
					continue;
				}

				logger.info("Just send " + document.getName()
						+ " for processing.");
				// annotate each file
				String result = ProcessThisTweet(new String(
						Files.readAllBytes(document.toPath())));

				logger.info("Received " + document.getName());
				w = new OutputStreamWriter(new FileOutputStream(
						annotatedDataFolder + "/" + document.getName()));
				w.append(result);

			} catch (FileNotFoundException e) {
				logger.debug(e);
			} catch (IOException e) {
				logger.debug(e);
			} finally {
				try {
					w.close();
				} catch (IOException e) {
					logger.debug(e);
				} catch (Exception e2) {
					logger.debug(e2);
				}
			}
		}
	}

	/**
	 * Process one tweet.
	 * 
	 * @param tweet
	 *            Tweet we want to process.
	 * @return Processed tweet
	 */
	private String ProcessThisTweet(String tweet) {

		ServiceDescriptor sbtItem = ServicesCatalog.getItem("SBT");
		S4ServiceClient client = new S4ServiceClient(sbtItem, apiKey, apiPass);

		String documentText = tweet;
		SupportedMimeType documentMimeType = SupportedMimeType.PLAINTEXT;
		ResponseFormat serializationFormat = ResponseFormat.JSON;
		InputStream result = client.annotateDocumentAsStream(documentText,
				documentMimeType, serializationFormat);
		StringWriter w = new StringWriter();
		try {
			IOUtils.copy(result, w,Charset.forName("UTF-8"));
		} catch (IOException e) {
			logger.debug(e);
		}
		return w.toString();
	}
}
