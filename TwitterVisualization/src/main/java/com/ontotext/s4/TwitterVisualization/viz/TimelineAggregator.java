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

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class TimelineAggregator {
	public static Logger logger = Logger.getLogger(TimelineAggregator.class);

	/**
	 * This method convert list of tweets to a JSON. This is the JOSN we will
	 * send to Google Visualization API in order to create a timeline
	 * visualization.
	 * 
	 * @param tweets
	 *            List of tweets we will visualize.
	 * @return JSON in an appropriate format for Google Visualization API.
	 */
	public static String process(List<Tweet> tweets, String[] entities) {

		HashMap<String, int[]> maps = new HashMap<String, int[]>();

		for (Tweet tw : tweets) {
			if (tw.date == null) {
				continue;
			}
			String data = tw.date;
			String key = data.substring(0, 16);
			int[] countSet = maps.get(key);
			if (countSet == null) {
				countSet = new int[entities.length];
				maps.put(key, countSet);
			}

			for (int i = 0; i < entities.length; i++) {

				for (String val : tw.htags) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}

				for (String val : tw.locs) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}

				for (String val : tw.orgs) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}

				for (String val : tw.people) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}
			}
		}
		return generateJsonData(maps, entities);
	}

	/**
	 * 
	 * @param maps
	 * @param entities
	 * @return
	 */
	private static String generateJsonData(HashMap<String, int[]> maps,
			String[] entities) {
		StringBuffer buff = new StringBuffer();
		buff.append("function initTable(data){").append(
				"\ndata.addColumn('date', 'Date');");
		for (String entity : entities) {
			buff.append("\ndata.addColumn('number', '").append(entity)
					.append("');").append("\ndata.addColumn('string', '")
					.append(entity).append("');")
					.append("\ndata.addColumn('string', '").append(entity)
					.append("');");
		}
		buff.append("\ndata.addRows([");

		for (String date : maps.keySet()) {
			int[] values = maps.get(date);
			if (isEmpty(values)) {
				continue;
			}
			buff.append("\n [new Date(").append(date.substring(0, 4))
					.append(", ").append(date.substring(5, 7)).append(", ")
					.append(date.substring(8, 10)).append(", ")
					.append(date.substring(11, 13)).append(", ")
					.append(date.substring(14, 16)).append(", 00")
					.append("), ");
			for (int count : values) {
				buff.append(count).append(", undefined, undefined, ");
			}
			buff.delete(buff.length() - 2, buff.length()).append("],");

		}
		buff.delete(buff.length() - 1, buff.length()).append("\n]);\n}\n");
		return buff.toString();
	}

	/**
	 * This method checks if an array contains only 0
	 * 
	 * @param arr
	 *            Array we want to check.
	 * @return True if the array contains only 0.
	 */
	private static boolean isEmpty(int[] arr) {
		for (int a : arr) {
			if (a != 0) {
				return false;
			}
		}
		return true;
	}
}
