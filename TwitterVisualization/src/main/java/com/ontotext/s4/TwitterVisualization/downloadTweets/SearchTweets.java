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

public class SearchTweets {
	Logger logger = Logger.getLogger(SearchTweets.class);

	public static final String DEFAULT_RAW_TWEETS_FOLDER = "data/rawTweets";

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	private String dataFolder;

	public SearchTweets(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		this(consumerKey, consumerSecret, accessToken, accessTokenSecret, null);
	}

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
	 * 
	 * @param queryString
	 *            Search query; For more info how to write it right look
	 *            https://dev.twitter.com/docs/using-search
	 * @throws TwitterException 
	 */
	public void search(String queryString) throws TwitterException {
		// configure twitter API keys
		Twitter twitter = ConfigurateTwitterAccount();

		// prepare query
		Query query = new Query(queryString);

		// look for both popular and recent tweets
		query.resultType(Query.MIXED);
		
		//restrict result to English language
		query.lang("en");

		// result of query
		QueryResult result;
		
		do {
			logger.debug(query);
			
			// execute query
			result = twitter.search(query);

			// make list of all tweets we can get
			List<Status> tweets = result.getTweets();

			// save them one by one
			for (Status tweet : tweets) {
				saveTweetIntoFile(tweet);
			}

		}

		// if query contains link to next page just continue
		while ((query = result.nextQuery()) != null);

	}

	/**
	 * Save this Tweet into file. Name of the file will be id of Tweet.
	 * 
	 * @param tweet
	 *            Tweet we want to save.
	 */
	private void saveTweetIntoFile(Status tweet) {
		/*
		 * check if data folder exist. if not create it.
		 */
		File files = new File(dataFolder);
		if (!files.exists()) {
			files.mkdirs();
		}

		/*
		 * create file with tweet id for name.
		 */
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
			 *  append raw Json from tweet and then add new line into file
			 */
			writer.append(TwitterObjectFactory.getRawJSON(tweet) + "\n");
		} catch (IOException e) {
			logger.debug(e);
		}

		/*
		 *  close file
		 */
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				logger.debug("Something went wrong when closing tweet file.", e);
			}
		}
	}

}
