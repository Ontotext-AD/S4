package com.ontotext.s4.TwitterVisualization;

import org.apache.log4j.Logger;

import com.ontotext.s4.TwitterVisualization.downloadTweets.SearchTweets;
import com.ontotext.s4.TwitterVisualization.processingTweets.ProcessingTweets;
import com.ontotext.s4.TwitterVisualization.viz.TwitterReader;

import twitter4j.TwitterException;

public class Main {
	private static final String DEFAULT_PROPERTIES_FILE = "app.properties";

	public static Logger logger = Logger.getLogger(Main.class);

	/*
	 * we need Twitter api key and secret ...; we need Search words; we need S4
	 * api key and secret ; we need folders to save raw tweets, process tweets,
	 * and visualisation;
	 */
	public static void main(String[] args) {
		/*
		 * prepare app propertie file
		 */
		PropertiesService appProperties;
		try {
			appProperties = new PropertiesService(args[0]);
		} catch (Exception e) {
			logger.info(args[0] + " is not valid properties file.");
			appProperties = new PropertiesService(DEFAULT_PROPERTIES_FILE);
		}

		/*
		 * search for tweets on topic(twitterQuery);
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
		 * process tweets;
		 */
		ProcessingTweets processingTweets = new ProcessingTweets(
				appProperties.getProperty("s4.apiKey"),
				appProperties.getProperty("s4.apiPass"),
				appProperties.getProperty("rawTweetsFolder"),
				appProperties.getProperty("proccessedTweetsFolder"));
		
		processingTweets.ProcessTweetsNow();

		/*
		 * prepare visualisation;
		 */
		TwitterReader twitterReader=new TwitterReader(
				appProperties.getProperty("timelineTerms").split(","),
				appProperties.getProperty("proccessedTweetsFolder"),
				appProperties.getProperty("visualisationFolder")
				);
		try {
			twitterReader.startVisualisation();
		} catch (Exception e) {
			logger.debug(e);
		}

	}

}
