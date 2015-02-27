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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ontotext.s4.service.ResponseFormat;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.SupportedMimeType;

public class ThreadClient implements Runnable {
	private static final Logger logger = Logger.getLogger(ThreadClient.class);

	private volatile boolean isRunning = true;
	private Thread t;
	private LinkedBlockingQueue<File> requestsQueue;
	private S4ServiceClient client;
	private String mimeType;
	private String responseFormat;
	private String inputFolder;
	private String outputFolder;

	public ThreadClient(LinkedBlockingQueue<File> requestsQueue, String apiKey,
			String apiSecret, String serviceName, String mimeType,
			String responseFormat, String inputFolder, String outputFolder) {
		super();
		this.requestsQueue = requestsQueue;
		this.mimeType = mimeType;
		this.responseFormat = responseFormat;
		this.inputFolder = inputFolder;
		this.outputFolder = outputFolder;
		initService(apiKey, apiSecret, mimeType, serviceName);
	}

	private void initService(String apiKey, String apiSecret, String mimeType,
			String serviceName) {
		if (serviceName != null) {
			try {
				client = new S4ServiceClient(new URL(serviceName), apiKey,
						apiSecret);
			} catch (MalformedURLException e) {
				logger.error(e);
			}
		} else {
			try {
				client = new S4ServiceClient(new URL(serviceName), apiKey,
						apiSecret);
			} catch (MalformedURLException e) {
				logger.error(e);
			}
		}

	}

	public void run() {
		while (isRunning) {
			File currentFile = null;

			try {

				// read currentFile

				currentFile = requestsQueue.poll();

				if (currentFile == null && ProcessingDocuments.readHasFinished) {
					logger.info(t.getName()
							+ " can't get any more text for processing");
					kill();
				} else {
					String textToProcess = null;
					try {
						textToProcess = FileUtils.readFileToString(currentFile,
								"UTF-8");
					} catch (IOException e) {
						logger.info("There was problem reading this "
								+ currentFile.getAbsolutePath() + " document.");
						logger.error(e);
					}

					// log input file
					// logger.debug(textToProcess);
					logger.debug(t.getName() + " send "
							+ currentFile.getAbsolutePath());

					if (textToProcess.trim().length() != 0) {

						InputStream annotatedDocument = client
								.annotateDocumentAsStream(textToProcess,
										SupportedMimeType.valueOf(mimeType),
										ResponseFormat.valueOf(responseFormat));

						

						// log result
						try {
							String result = convertSreamToString(annotatedDocument);
							saveFile(result, changeDataFolder(currentFile));

							// log result
							// logger.debug(result);
						} catch (IOException e) {
							logger.info("There was problem saving this "
									+ currentFile.getAbsolutePath()
									+ " document.");
							logger.error(e);
						}

					}
				}
			} catch (Exception e) {

				logger.error(e);

			}
		}
	}

	private File changeDataFolder(File currentFile) {
		String path = currentFile.getAbsolutePath();
		
		path = path.replace(new File(inputFolder).getAbsolutePath(), new File(outputFolder).getAbsolutePath());
		
		File outputFile = new File(path);
		
		outputFile.getParentFile().mkdirs();
		return outputFile;
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void kill() {
		isRunning = false;
	}

	public boolean isAlive() {
		return t.isAlive();
	}

	public void saveFile(InputStream text, File file) throws IOException {
		saveFile(convertSreamToString(text), file);
	}

	public void saveFile(String text, File file) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.append(text);
		fileWriter.close();

	}

	public String convertSreamToString(InputStream inputStream)
			throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, "UTF-8");
		String theString = writer.toString();
		return theString;
	}

}
