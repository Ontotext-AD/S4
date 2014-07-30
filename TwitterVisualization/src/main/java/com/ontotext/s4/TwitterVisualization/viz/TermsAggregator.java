package com.ontotext.s4.TwitterVisualization.viz;

import java.util.List;

public class TermsAggregator {
	

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
