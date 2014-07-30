package com.ontotext.s4.TwitterVisualization.viz;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAggregator {
	
	static class Helper {
		String token;
		int count;
	}

	public static String process(List<Tweet> tweets) {
		Map<String, Helper> buffer = new HashMap<String, MapAggregator.Helper>();
		for(Tweet tw : tweets) {
			for(String loc : tw.locs) {
				Helper h = buffer.get(loc);
				if (h == null) {
					h = new Helper();
					h.token = loc;
					buffer.put(loc, h);
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
		StringBuffer result = new StringBuffer("var thedata = [\n['Country', 'Popularity'],");
		for(Helper h : recs) {
			result.append("['").append(h.token.replace("'", "\\\'")).append("', ").append(h.count).append("],\n");
		}
		String jsData = result.toString();
		if (jsData.endsWith(",\n")) {
			jsData = jsData.substring(0,  jsData.length() - 2) + '\n';
		}
		return jsData + "];";
	}

}
