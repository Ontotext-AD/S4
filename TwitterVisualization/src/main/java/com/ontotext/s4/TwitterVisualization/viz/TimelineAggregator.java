package com.ontotext.s4.TwitterVisualization.viz;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class TimelineAggregator {
	public static Logger logger=Logger.getLogger(TimelineAggregator.class);

	public static String process(List<Tweet> tweets, String[] entities) {
		
		
		HashMap<String, int[]> maps = new HashMap<String, int[]>();
		
		
		for(Tweet tw : tweets) {
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
			
			for(int i = 0; i < entities.length; i++) {

				for(String val : tw.htags) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}
				
				for(String val : tw.locs) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}
				
				for(String val : tw.orgs) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}
				
				for(String val : tw.people) {
					if (val.equalsIgnoreCase(entities[i])) {
						countSet[i]++;
					}
				}
			}
		}
		return generateJsonData(maps, entities);
	}

	private static String generateJsonData(HashMap<String, int[]> maps, String[] entities) {
		StringBuffer buff = new StringBuffer();
		buff.append("function initTable(data){")
		    .append("\ndata.addColumn('date', 'Date');");
		for(String entity : entities) {
			buff.append("\ndata.addColumn('number', '").append(entity).append("');")
			    .append("\ndata.addColumn('string', '").append(entity).append("');")
			    .append("\ndata.addColumn('string', '").append(entity).append("');");
		}
		buff.append("\ndata.addRows([");
		
		for(String date : maps.keySet()) {
			int[] values = maps.get(date);
			if (isEmpty(values)) {
				continue;
			}
			buff.append("\n [new Date(").append(date.substring(0, 4)).append(", ").append(date.substring(5,7)).append(", ").append(date.substring(8,10))
			.append(", ").append(date.substring(11, 13)).append(", ").append(date.substring(14, 16)).append(", 00").append("), ");
			for(int count : values) {
				buff.append(count).append(", undefined, undefined, ");
			}
			buff.delete(buff.length() - 2, buff.length()).append("],");
	
			
		}
		buff.delete(buff.length() - 1, buff.length()).append("\n]);\n}\n");
		return buff.toString();
	}
	
	private static boolean isEmpty(int[] arr) {
		for(int a : arr) {
			if(a != 0) {
				return false;
			}
		}
		return true;
	}
}
