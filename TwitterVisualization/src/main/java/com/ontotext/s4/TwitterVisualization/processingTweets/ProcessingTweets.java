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
package com.ontotext.s4.TwitterVisualization.processingTweets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ontotext.s4.item.AvailableItems;
import com.ontotext.s4.item.Item;
import com.ontotext.s4.online.OnlineApi;
import com.ontotext.s4.online.ResponseFormat;
import com.ontotext.s4.online.SupportedMimeType;

public class ProcessingTweets {
	Logger logger = Logger.getLogger(ProcessingTweets.class);
	private String rawTweetsFolder;
	private String processedTweetsFolder;
	private String apiKey;
	private String apiPass;

	public ProcessingTweets(String apiKey, String apiPass,
			String rawTweetsFolder, String processedTweetsFolder) {
		this.apiKey=apiKey;
		this.apiPass=apiPass;
		this.rawTweetsFolder = rawTweetsFolder;
		this.processedTweetsFolder = processedTweetsFolder;
	}

	public void ProcessTweetsNow() {

		// get all documents in a folder
		File f = new File(rawTweetsFolder);
		File[] documents = f.listFiles();
		
		//
		if (documents==null) {
			logger.info("There are no files to process");
			return;
		}

		// the output
		File results = new File(processedTweetsFolder);
		if (!results.exists()) {
			results.mkdirs();
		}

		Writer w = null;
		for (File document : documents) {
			try {

				if (document.isDirectory() || !document.canRead()) {
					continue;
				}
				
				logger.info("Just send "+document.getName()+" for processing.");
				// annotate each file
				String result = ProcessThisTweet(new String(
						Files.readAllBytes(document.toPath())));
				
				logger.info("Received "+document.getName());
				w = new OutputStreamWriter(new FileOutputStream(
						processedTweetsFolder + "/" + document.getName()));
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

	private String ProcessThisTweet(String tweet) {

		Item twitieItem = AvailableItems.getItem("TwitIE");
		OnlineApi api = new OnlineApi(twitieItem, apiKey,apiPass);

		String documentText = tweet;
		SupportedMimeType documentMimeType = SupportedMimeType.TWITTER_JSON;
		ResponseFormat serializationFormat = ResponseFormat.JSON;
		InputStream result = api.annotateDocumentAsStream(documentText,
				documentMimeType, serializationFormat);
		StringWriter w = new StringWriter();
		try {
			IOUtils.copy(result, w);
		} catch (IOException e) {
			logger.debug(e);
		}
		return w.toString();
	}
}
