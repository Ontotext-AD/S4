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

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

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
	
	public void sendDataToGraphDB(File rdfFile)throws RepositoryException {
		RepositoryConnection con = httpRepository.getConnection();
		con.begin();
		try {
			con.add(rdfFile,"http://example.org/",RDFFormat.NTRIPLES);
		} catch (RDFParseException | IOException e) {
			logger.error(e);
		}
		con.commit();
	}

	public void close() {
		try {
			httpRepository.shutDown();
		} catch (RepositoryException e) {
			logger.error(e);
		}
	}

}
