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
package com.ontotext.s4.TwitterVisualization.viz;

import java.util.List;

public class TermsAggregator {
	
	/**
	 * This method will list all term from the tweets.
	 * @param tweets List of tweets.
	 * @return String with one term per line.
	 */
	public static String process(List<Tweet> tweets) {
		StringBuffer result = new StringBuffer();
		for(Tweet tw : tweets) {
			for(String loc : tw.locs) {
				result.append(loc).append('\n');
			}
			for(String loc : tw.orgs) {
				result.append(loc).append('\n');
			}
			for(String loc : tw.people) {
				result.append(loc).append('\n');
			}
			for(String htag : tw.htags) {
				result.append(htag).append('\n');
			}
		}
		return result.toString();
	}

}
