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
package com.ontotext.s4.TwitterVisualization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Properties wrapper class, initializing its data from a file path provided
 */
public class PropertiesService {

	private static Logger log = Logger.getLogger(PropertiesService.class);
	private Properties p;

	/**
	 * This method loads the properties file
	 * @param propertiesFile Path to the properties file to use.
	 */
	public PropertiesService(String propertiesFile) {
		log.info("Initializing Properties Service with file " + propertiesFile);
		p = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
		try {
			p.load(is);
		} catch (IOException e) {
			log.error("Could not load 'app.properties' which contains configuration. " +
					"Initialization failed. Details follow.", e);
			return;
		}
	}

	/**
	 * This method returns the value of a property.
	 * @param key property name. 
	 * @return value Value of the property.
	 */
	public String getProperty(String key) {
		return p.getProperty(key);	
	}
}
