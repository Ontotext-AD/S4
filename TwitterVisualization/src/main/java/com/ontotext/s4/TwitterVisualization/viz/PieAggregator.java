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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieAggregator {

	static class Helper {
		String token;
		int count;
	}

	/**
	 * This method convert list of tweets to a JSON. This is the JOSN we will
	 * send to Google Visualization API in order to create a pie chart
	 * visualization.
	 * 
	 * @param tweets
	 *            List of tweets we will visualize.
	 * @return JSON in an appropriate format for Google Visualization API.
	 */
	public static String process(List<Tweet> tweets) {
		Map<String, Helper> buffer = new HashMap<String, PieAggregator.Helper>();
		for (Tweet tw : tweets) {
			for (String loc : tw.locs) {
				Helper h = buffer.get(loc.toLowerCase());
				if (h == null) {
					h = new Helper();
					h.token = loc;
					buffer.put(loc.toLowerCase(), h);
				}
				h.count++;
			}
			for (String loc : tw.orgs) {
				Helper h = buffer.get(loc.toLowerCase());
				if (h == null) {
					h = new Helper();
					h.token = loc;
					buffer.put(loc.toLowerCase(), h);
				}
				h.count++;
			}
			for (String loc : tw.people) {
				Helper h = buffer.get(loc.toLowerCase());
				if (h == null) {
					h = new Helper();
					h.token = loc;
					buffer.put(loc.toLowerCase(), h);
				}
				h.count++;
			}
			for (String htag : tw.htags) {
				Helper h = buffer.get(htag.toLowerCase());
				if (h == null) {
					h = new Helper();
					h.token = htag;
					buffer.put(htag.toLowerCase(), h);
				}
				h.count++;
			}
		}
		Helper[] recs = buffer.values().toArray(new Helper[buffer.size()]);
		Arrays.sort(recs, new Comparator<Helper>() {
			public int compare(Helper o1, Helper o2) {
				return o2.count - o1.count;
			}
		});
		StringBuffer result = new StringBuffer(
				"var thedata = [\n['Entity', 'Popularity'],");
		int limit = 10;
		for (Helper h : recs) {
			if (limit-- <= 0) {
				break;
			}
			result.append("['").append(h.token.replace("'", "\\\'"))
					.append("', ").append(h.count).append("],\n");
		}
		String jsData = result.toString();
		if (jsData.endsWith(",\n")) {
			jsData = jsData.substring(0, jsData.length() - 2) + '\n';
		}
		return jsData + "];";
	}

}
