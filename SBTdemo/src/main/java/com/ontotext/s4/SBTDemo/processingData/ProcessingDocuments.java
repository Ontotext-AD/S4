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
package com.ontotext.s4.SBTDemo.processingData;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

public class ProcessingDocuments {
	Logger logger = Logger.getLogger(ProcessingDocuments.class);

	private String rawDataFolder;
	private String annotatedDataFolder;
	private String apiKey;
	private String apiPass;
	private String service;
	private String mimeType;
	private String responseFormat;
	private int numberOfThreads;
	protected static Boolean readHasFinished;
	private static LinkedBlockingQueue<File> requestsQueue;

	/**
	 * 
	 * @param apiKey
	 *            API key for S4
	 * @param apiPass
	 *            API pass for S4
	 * @param rawDataFolder
	 *            path to the raw data folder
	 * @param annotatedDataFolder
	 *            path to the processed data folder
	 */
	public ProcessingDocuments(String apiKey, String apiPass,
			String rawDataFolder, String annotatedDataFolder, String service,
			String mimeType, String responseFormat, int numberOfThreads) {
		this.apiKey = apiKey;
		this.apiPass = apiPass;
		this.rawDataFolder = rawDataFolder;
		this.annotatedDataFolder = annotatedDataFolder;
		this.service = service;
		this.mimeType = mimeType;
		this.responseFormat = responseFormat;
		this.numberOfThreads = numberOfThreads;
		requestsQueue = new LinkedBlockingQueue<File>();
	}

	/**
	 * Process all documents into the raw tweets folder. Save processed tweets
	 * into the processed tweets Folder.
	 */
	public void ProcessData() {

		// read files

		ThreadReadFiles readFiles = new ThreadReadFiles(requestsQueue,
				new File(rawDataFolder));
		readFiles.start();

		// start client threads
		ThreadClient[] threads = new ThreadClient[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new ThreadClient(requestsQueue, apiKey, apiPass,
					service, mimeType, responseFormat, rawDataFolder,
					annotatedDataFolder);
			threads[i].start();

		}
		while (true) {
			int i = 0;
			for (ThreadClient tClient : threads) {
				if (!tClient.isAlive()) {
					i++;
				}
			}
			if(i>=numberOfThreads){
				return;
			}
		}
	}

}
