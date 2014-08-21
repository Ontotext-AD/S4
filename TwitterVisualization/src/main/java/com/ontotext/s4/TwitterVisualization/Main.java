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
 
package com.ontotext.s4.TwitterVisualization;

import org.apache.log4j.Logger;

import com.ontotext.s4.TwitterVisualization.downloadTweets.SearchTweets;
import com.ontotext.s4.TwitterVisualization.processingTweets.ProcessingTweets;
import com.ontotext.s4.TwitterVisualization.viz.TwitterReader;

import twitter4j.TwitterException;

/**
 * This class is the main entry point to the demo application. It performs data collection, 
 * annotation and vizualization
 * Make sure 'app.properties' file contains the proper credentials prior to running the application.
 */
public class Main {
	private static final String DEFAULT_PROPERTIES_FILE = "app.properties";

	public static Logger logger = Logger.getLogger(Main.class);

	/**
	 * Main method
	 * @param args the only argument necessary is a properties file. 
	 * If omitted the deault will be 'app.properties'.
	 */
	public static void main(String[] args) {
		/*
		 * searching and reading the app properties file
		 */
		PropertiesService appProperties;
		try {
			appProperties = new PropertiesService(args[0]);
		} catch (Exception e) {
			logger.info(args[0] + " is not valid properties file.");
			appProperties = new PropertiesService(DEFAULT_PROPERTIES_FILE);
		}

		/*
		 * performs search for tweets on topic (twitterQuery property);
		 */
		SearchTweets searchTweets = new SearchTweets(
				appProperties.getProperty("twitter.consumerKey"),
				appProperties.getProperty("twitter.consumerSecret"),
				appProperties.getProperty("twitter.accessToken"),
				appProperties.getProperty("twitter.accessTokenSecret"),
				appProperties.getProperty("rawTweetsFolder"));

		try {
			searchTweets.search(appProperties.getProperty("twitterQuery"));
		} catch (TwitterException e) {
			logger.debug(
					"Something went wrong when we try to get tweets, but lets try to process what we already have into raw tweets folder.",
					e);
		}

		/*
		 * processing the tweets using the S4 text analyzis services
		 */
		ProcessingTweets processingTweets = new ProcessingTweets(
				appProperties.getProperty("s4.apiKey"),
				appProperties.getProperty("s4.apiPass"),
				appProperties.getProperty("rawTweetsFolder"),
				appProperties.getProperty("proccessedTweetsFolder"));

		processingTweets.ProcessTweets();

		/*
		 * aggregating the results and preparing the inputs for visualization;
		 */
		TwitterReader twitterReader = new TwitterReader(appProperties
				.getProperty("timelineTerms").split(","),
				appProperties.getProperty("proccessedTweetsFolder"),
				appProperties.getProperty("visualisationFolder"));
		try {
			twitterReader.startVisualisation();
		} catch (Exception e) {
			logger.debug(e);
		}

	}

}
