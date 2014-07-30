package com.ontotext.s4.TwitterVisualization.viz;

import java.util.HashSet;
import java.util.Set;

public class Tweet {

	String text;
	String date;
	Set<String> locs = new HashSet<String>();
	Set<String> people = new HashSet<String>();
	Set<String> orgs = new HashSet<String>();
	Set<String> htags = new HashSet<String>();
	
	public Tweet(String text) {
		this.text = text;
	}

}
