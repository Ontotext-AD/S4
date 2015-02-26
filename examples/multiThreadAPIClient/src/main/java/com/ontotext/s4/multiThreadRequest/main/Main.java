/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
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
package com.ontotext.s4.multiThreadRequest.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.ontotext.s4.multiThreadRequest.thread.ThreadClient;
import com.ontotext.s4.multiThreadRequest.thread.ThreadReadFiles;
import com.ontotext.s4.multiThreadRequest.utils.PropertiesNames;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class);

	public static final int APP_PRPERTIES_ARGS=0;
	
	public static volatile Boolean readHasFinished;
	
	private static LinkedBlockingQueue<File> requestsQueue;

	private static Properties programProperties;

	private static int numberOfThreads = 1;

	public static void main(String[] args) {

		org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
		// read properties
		init(args);

		// read files
		ThreadReadFiles readFiles = new ThreadReadFiles(requestsQueue,
				new File(
						programProperties
								.getProperty(PropertiesNames.INPUT_FOLDER)));
		readFiles.start();

		// start client threads
		ThreadClient[] threads = new ThreadClient[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new ThreadClient(
					requestsQueue,
					programProperties.getProperty(PropertiesNames.API_KEY),
					programProperties.getProperty(PropertiesNames.API_SECRET),
					programProperties.getProperty(PropertiesNames.SERVICE),
					programProperties.getProperty(PropertiesNames.MIME_TYPE),
					programProperties
							.getProperty(PropertiesNames.RESPONSE_FORMAT),
					programProperties.getProperty(PropertiesNames.INPUT_FOLDER),
					programProperties
							.getProperty(PropertiesNames.OUTPUT_FOLDER));
			threads[i].start();
		}

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

		requestsQueue = new LinkedBlockingQueue<File>();
		readHasFinished = false;
		numberOfThreads = Integer.parseInt(programProperties
				.getProperty(PropertiesNames.NUMBER_OF_CLIENTS));
	}

}
