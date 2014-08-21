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

import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents one processed tweet by S4 services.
 * It contains the text, date and any entities recognized by the service.
 */
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
