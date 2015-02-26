/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ontotext.s4.SBTDemo.graphDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openrdf.model.Literal;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;

import com.ontotext.s4.SBTDemo.parse.JsonToRDF;

public class RepoManager {

	Logger logger = Logger.getLogger(RepoManager.class);

	HTTPRepository httpRepository;

	/*
	 * TODO init-repo url, user managment
	 */
	public RepoManager(String repoURL) {
		this(repoURL, null, null);
	}

	public RepoManager(String repoURL, String username, String password) {
		init(repoURL, username, password);
	}

	private void init(String repoURL, String username, String password) {
		httpRepository = new HTTPRepository(repoURL);
		if (username != null && password != null) {
			httpRepository.setUsernameAndPassword(username, password);
		}
		try {
			httpRepository.initialize();
		} catch (RepositoryException e) {
			logger.error(e);
		}

	}

	/*
	 * TODO sendData params- graph(model) Read graph statements and send them to
	 * the GraphDB DONE
	 */

	public void sendDataTOGraphDB(Model graph) throws RepositoryException {
		RepositoryConnection con = httpRepository.getConnection();
		con.begin();
		for (Statement st : graph) {
			con.add(st);
		}
		con.commit();
	}

	public void sendDataToGraphDB(File rdfFile) throws RepositoryException,
			IOException {
		ValueFactory factory = ValueFactoryImpl.getInstance();
		RepositoryConnection con = httpRepository.getConnection();

		// read line by line

		BufferedReader br = new BufferedReader(new FileReader(rdfFile));
		String line;
		int i=0;
		while ((line = br.readLine()) != null) {
			String subjectString = line.substring(0, line.indexOf(" "));
			line = line.substring(line.indexOf(" "), line.length()).trim();
			subjectString=trimString(subjectString);
			
			String predicateString = line.substring(0, line.indexOf(" "));
			line = line.substring(line.indexOf(" "), line.length()).trim();
			predicateString=trimString(predicateString);
			
			String objectString = line.substring(0, line.lastIndexOf("."));
			objectString=trimString(objectString);
			
			// process the line.

			 URI subject = factory.createURI(subjectString);
			 URI predicate = factory.createURI(predicateString);
			 Value object = objectString.startsWith(JsonToRDF.URI_START_WITH) ? factory
			 .createURI(objectString) : factory.createLiteral(objectString);

			 con.add(subject, predicate, object);
			 i++;
			 

			 if(i>=1000000){
				 con.commit();
				 i=0;
			 }

		}
		br.close();
	}

	public void close() {
		try {
			httpRepository.shutDown();
		} catch (RepositoryException e) {
			logger.error(e);
		}
	}

	public String trimString(String toTrim) {
		if (toTrim.contains("<")) {
			toTrim = toTrim.substring(toTrim.indexOf('<')+ 1,
					toTrim.indexOf(">"));
		}
		else
		{
			toTrim = toTrim.substring(toTrim.indexOf("\"") + 1,
					toTrim.lastIndexOf("\""));
		}
		return toTrim;
	}

}
