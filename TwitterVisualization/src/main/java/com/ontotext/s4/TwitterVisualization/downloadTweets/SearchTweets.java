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
package com.ontotext.s4.TwitterVisualization.downloadTweets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class is responsible for collecting twitter date based on Twitter Search API
 * and storing the results in a certain local folder. The class expects proper twitter
 * credentials to be provided (API keys and access tokens)
 *
 */
public class SearchTweets {
	Logger logger = Logger.getLogger(SearchTweets.class);

	public static final String DEFAULT_RAW_TWEETS_FOLDER = "data/rawTweets";

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	private String dataFolder;

	/**
	 * Creates new Twitter client instance. It requires valid API key, API
	 * secret, access token and access token secret. Data folder will be set to:
	 * 'data/rawTweets' (default)
	 * 
	 * @param consumerKey
	 *            API key
	 * @param consumerSecret
	 *            API secret
	 * @param accessToken
	 *            accessToken
	 * @param accessTokenSecret
	 *            accessTokenSecret
	 */
	public SearchTweets(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		this(consumerKey, consumerSecret, accessToken, accessTokenSecret, null);
	}

	/**
	 * Creates new Twitter client instance. It requires valid API key, API
	 * secret, access token and access token secret.
	 * 
	 * @param consumerKey
	 *            API key
	 * @param consumerSecret
	 *            API secret
	 * @param accessToken
	 *            accessToken
	 * @param accessTokenSecret
	 *            accessTokenSecret
	 * @param dataFolder
	 *            Twitter data result local folder. If this is null the default folder is 'data/rawTweets'
	 */
	public SearchTweets(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret, String dataFolder) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
		if (dataFolder != null) {
			this.dataFolder = dataFolder;
		} else {
			this.dataFolder = DEFAULT_RAW_TWEETS_FOLDER;
		}
	}

	public String getDataFolder() {
		return dataFolder;
	}

	public void setDataFolder(String dataFolder) {
		this.dataFolder = dataFolder;
	}

	
	/**
	 * Creates and configures Twitter client instance with provided credentials.
	 * 
	 * @return Twitter instance
	 */
	private Twitter ConfigurateTwitterAccount() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}
	
	/**
	 * This method executes search query on Twitter. The results are saved into the preconfigured result folder.
	 * @param queryString
	 *            the search query string. For more information refer to https://dev.twitter.com/docs/using-search
	 * @throws TwitterException
	 *            any problem related to authentication, communication, search, etc.
	 */
	public void search(String queryString) throws TwitterException {
		// configures twitter API keys
		Twitter twitter = ConfigurateTwitterAccount();

		// preparint the query
		Query query = new Query(queryString);

		// search in both popular and recent tweets
		query.resultType(Query.MIXED);

		// restrict result to English language
		query.lang("en");

		// result of the query
		QueryResult result;

		do {
			logger.debug(query);

			// executing the query
			result = twitter.search(query);

			// collect all tweets available
			List<Status> tweets = result.getTweets();

			// save each tweet into a file
			for (Status tweet : tweets) {
				saveTweetIntoFile(tweet);
			}

		}
		// handle the paging of the results
		while ((query = result.nextQuery()) != null);

	}

	/**
	 * Saves a Tweet into file. The name of the file will be the id of the Tweet.
	 * 
	 * @param tweet
	 *            Tweet message to save.
	 */
	private void saveTweetIntoFile(Status tweet) {
		/*
		 * checks if data folder exist. If not creates it.
		 */
		File files = new File(dataFolder);
		if (!files.exists()) {
			files.mkdirs();
		}

		BufferedWriter writer = null;
		try {
			/*
			 * create file into data folder with id for name, json for extension
			 * and UTF-8 encoding
			 */
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(dataFolder + "/" + tweet.getId()
							+ ".json"), "UTF-8"));

			/*
			 * appends raw Json from tweet and then add new line into file
			 */
			writer.append(TwitterObjectFactory.getRawJSON(tweet) + "\n");
		} catch (IOException e) {
			logger.debug(e);
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				logger.debug("Something went wrong when closing tweet file.", e);
			}
		}
	}

}
