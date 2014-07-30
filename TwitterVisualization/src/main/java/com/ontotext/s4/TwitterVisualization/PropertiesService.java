package com.ontotext.s4.TwitterVisualization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesService {

	private static Logger log = Logger.getLogger(PropertiesService.class);
	private Properties p;

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

	public String getProperty(String key) {
		return p.getProperty(key);	
	}
}
