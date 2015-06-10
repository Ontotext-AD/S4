/*
 * Copyright (c) 2015
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ontotext.s4.gdbaas.createRepo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.openrdf.rio.RDFFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontotext.s4.gdbaas.createRepo.model.CreateRepositoryRequest;

/**
 * Manage GDBaaS
 * 
 * @author YavorPetkov
 *
 */
public class GdbaasClient {

	public static final String SPARQL_ACCEPT_HEADER = "application/sparql-results+json";
	public static final String SPARQL_CONTENT_TYPE = "application/x-www-form-urlencoded";
	public static final String APPLICATION_JSON_HEADER = "application/json";

	private CloseableHttpClient httpClient;
	private HttpClientContext ctx;
	private String dbaasName;
	private String userId;
	private String keyId;
	private String password;

	public GdbaasClient(String userId, String dbaasName, String keyId,
			String password) {
		this.setDbaasName(dbaasName);
		this.setUserId(userId);
		this.keyId = keyId;
		this.password = password;
		httpClient = HttpClients.createDefault();
		setupContext();
	}

	public String getDbaasName() {
		return dbaasName;
	}

	public void setDbaasName(String dbaasName) {
		this.dbaasName = dbaasName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private URI generateRepositoryEndpointUrl() throws URISyntaxException {

		return new URI("https://rdf.s4.ontotext.com/" + userId + "/"
				+ dbaasName + "/repositories/");
	}

	/**
	 * Sets up a HTTPContext for authentication
	 */
	private void setupContext() {
		ctx = HttpClientContext.create();
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
				keyId, password);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, creds);
		ctx.setCredentialsProvider(credsProvider);
	}

	/**
	 * Sends a test request to the service
	 * 
	 * @return true if the service is available
	 */
	public boolean testEndpoint() {
		HttpGet get = new HttpGet("");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get, ctx);
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			if (statusCode != 200) {
				System.out.println("Error communicating with endpoint.");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				return false;
			} else {
				System.out.println("Endpoint returned status SUCCESS.");
				System.out.println(response.toString());
				System.out.println("Response body: ");
				System.out.println(getContent(response));
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		return false;
	}

	/**
	 * Serialize a CreateRepository
	 * 
	 * @return
	 * @throws JsonProcessingException
	 * @throws URISyntaxException
	 */
	public String createRepository(CreateRepositoryRequest repositoryInfo)
			throws JsonProcessingException, URISyntaxException {
		ObjectMapper mapper = new ObjectMapper();
		String message = mapper.writeValueAsString(repositoryInfo);
		HttpEntityEnclosingRequestBase put = new HttpPut(
				generateRepositoryEndpointUrl());
		return processRequest(message, APPLICATION_JSON_HEADER,
				APPLICATION_JSON_HEADER, put, APPLICATION_JSON_HEADER,
				APPLICATION_JSON_HEADER);
	}

	/**
	 * 
	 * @param repositoryId
	 * @return
	 * @throws URISyntaxException
	 */
	public String deleteRepository(String repositoryId)
			throws URISyntaxException {
		HttpRequestBase put = new HttpDelete(generateRepositoryEndpointUrl()
				+ repositoryId);
		return processRequest("", APPLICATION_JSON_HEADER,
				APPLICATION_JSON_HEADER, put, APPLICATION_JSON_HEADER,
				APPLICATION_JSON_HEADER);
	}

	/**
	 * 
	 * @param repositoryId
	 * @return
	 * @throws URISyntaxException
	 */
	public String checkRepository(String repositoryId)
			throws URISyntaxException {
		String message = "query=select%20*%20%7B%3Fs%20%3Fp%20%3Fo%7D%20limit%201";
		HttpEntityEnclosingRequestBase put = new HttpPost(
				generateRepositoryEndpointUrl() + repositoryId);
		return processRequest(message, APPLICATION_JSON_HEADER,
				APPLICATION_JSON_HEADER, put, SPARQL_CONTENT_TYPE,
				SPARQL_ACCEPT_HEADER);

	}

	public void uploadFile(String repositoryId, String baseURI,
			String pathToTheFile) throws Exception {
		RemoteRepositoryManager manager = RemoteRepositoryManager.getInstance(
				"https://rdf.s4.ontotext.com/" + userId + "/" + dbaasName,
				keyId, password);

		// Get the repository to use
		Repository repository = manager.getRepository(repositoryId);

		// Open a connection to this repository
		RepositoryConnection repositoryConnection = repository.getConnection();

		File fileToUpload = new File(pathToTheFile);
		try {
			repositoryConnection.add(fileToUpload, baseURI, RDFFormat.RDFXML);
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally {
			repositoryConnection.close();
		}
	}

	/**
	 * Serialize a ProcessingRequest and send it to Self Service Semantic Suite
	 * Online Processing Service
	 * 
	 * @param pr
	 *            the processing request to send
	 * @param acceptType
	 *            the type of output we want to produce
	 * @throws URISyntaxException
	 */
	private String processRequest(String message, String acceptType,
			String contentType, HttpRequestBase request,
			String contentTypeHeader, String acceptTypeHeader)
			throws URISyntaxException {

		request.setHeader("Accept", acceptTypeHeader);
		request.setHeader("Content-Type", contentTypeHeader);
		request.setHeader("Accept-Encoding", "gzip");

		if (request instanceof HttpEntityEnclosingRequestBase) {
			System.out.println("POST body is:");
			System.out.println(message);
			((HttpEntityEnclosingRequestBase) request)
					.setEntity(new StringEntity(message, Charset
							.forName("UTF-8")));
		}

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(request, ctx);
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			switch (statusCode) {
			case 200: {
				// Request was processed successfully
				System.out.println("SUCCESS");
				System.out.println(response.toString());
				return getContent(response);
			}
			case 204: {
				// Request was processed successfully
				System.out.println("SUCCESS");
				return response.toString();
			}
			case 400: {
				// Bad request, there is some problem with user input
				System.out.println("Bad request");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 403: {
				// Problem with user authentication
				System.out.println("Error during authentication");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 404: {
				// Not found
				System.out.println("Not found, check endpoint URL");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 406: {
				// Not Accepted
				System.out
						.println("The request was not accepted. Check Accept header");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 408: {
				// Processing this request took too long
				System.out.println("Could not process document in time");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 415: {
				// Unsupported media type
				System.out.println("Invalid value in Content-Type header");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 500: {
				// Internal server error
				System.out.println("Error during processing");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			default: {
				System.out.println("Could not process request");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * Helper method which collects the response's body as a string
	 * 
	 * @param response
	 *            the HttpResponse whose content we want to collect
	 * @return the String value of the response body
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private static String getContent(HttpResponse response)
			throws IllegalStateException, IOException {
		InputStream content = response.getEntity().getContent();
		StringWriter sw = new StringWriter();
		IOUtils.copy(content, sw, "UTF-8");
		return sw.toString();
	}
}