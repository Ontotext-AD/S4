/*
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package service;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Executes S4 REST API requests.
 * @author YavorPetkov
 *
 */
public class S4ServiceClient {

	public static final String SPARQL_ACCEPT="application/sparql-results+json";
	public static final String SPARQL_CONTENT_TYPE="application/x-www-form-urlencoded";
	public static final String APPLICATION_JSON = "application/json";
	
	private CloseableHttpClient httpClient;
	private HttpClientContext ctx;
	private String endpointUrl;
	private String keyId;
	private String password;
	
	public S4ServiceClient(String endpointURL, String keyId, String password){
		this.setEndpointUrl(endpointURL);
		this.keyId=keyId;
		this.password=password;
		httpClient = HttpClients.createDefault();
		setupContext();
	}
	
	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}
	
	/**
	 * Sets up a HTTPContext for authentication
	 */
	private void setupContext() {
		ctx = HttpClientContext.create();
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(keyId, password);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, creds);
		ctx.setCredentialsProvider(credsProvider);
	}
	
	/**
	 * Serialize a ProcessingRequest and send it to Self Service Semantic Suite Online Processing Service
	 * @param acceptType the type of output we want to produce
	 */
	public String processRequest(String message, String acceptType, String contentType) {
		HttpPost post = new HttpPost(getEndpointUrl());
		post.setHeader("Content-Type", contentType);
		post.setHeader("Accept", acceptType);
		post.setHeader("Accept-Encoding", "gzip");			
		
		
		System.out.println("POST body is:");
		System.out.println(message);
		
		post.setEntity(new StringEntity(message, Charset.forName("UTF-8")));

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post, ctx);
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			switch(statusCode) {
			case 200 : {
				//Request was processed successfully
				System.out.println("SUCCESS");
				System.out.println(response.toString());
				return getContent(response);
			}
			case 400 : {
				//Bad request, there is some problem with user input
				System.out.println("Bad request");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 403 : {
				//Problem with user authentication
				System.out.println("Error during authentication");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 404 : {
				//Not found
				System.out.println("Not found, check endpoint URL");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 406 : {
				//Not Accepted
				System.out.println("The request was not accepted. Check Accept header");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 408 : {
				//Processing this request took too long
				System.out.println("Could not process document in time");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 415 : {
				//Unsupported media type
				System.out.println("Invalid value in Content-Type header");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}
			case 500 : {
				//Internal server error
				System.out.println("Error during processing");
				System.out.println(response.toString());
				System.out.println(getContent(response));
				break;
			}			
			default : {
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
			} catch (IOException e) {}
		}
		return null;
	}
	
	/**
	 * Helper method which collects the response's body as a string
	 * @param response the HttpResponse whose content we want to collect
	 * @return the String value of the response body
	 * @throws IllegalStateException 
	 * @throws IOException
	 */
	private static String getContent(HttpResponse response) throws IllegalStateException, IOException{
		InputStream content = response.getEntity().getContent();
		StringWriter sw = new StringWriter();
		IOUtils.copy(content, sw, "UTF-8");
		return sw.toString();
	}

	
}
