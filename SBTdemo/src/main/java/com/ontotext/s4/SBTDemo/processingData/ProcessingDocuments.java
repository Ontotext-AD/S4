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
