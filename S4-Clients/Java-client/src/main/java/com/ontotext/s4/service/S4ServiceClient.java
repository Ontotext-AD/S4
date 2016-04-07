/*
 * S4 Java client library
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
package com.ontotext.s4.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.catalog.ServicesCatalog;
import com.ontotext.s4.client.HttpClient;
import com.ontotext.s4.client.HttpClientException;
import com.ontotext.s4.common.Parameters;

/**
 * Main entry point for the S4 text analytics APIs to send individual documents for annotation
 * by a particular pipeline and receive the results immediately.
 */
public class S4ServiceClient {

	private HttpClient client;

	/**
	 * Constructs a <code>S4ServiceClient</code> for accessing a specific processing
	 * pipeline on the s4.ontotext.com platform using the given credentials.
	 * 
	 * @param item the {@link ServiceDescriptor} which represents the processing pipeline which will be used
	 * @param apiKeyId API key ID for authentication
	 * @param apiPassword corresponding password
	 */
	public S4ServiceClient(ServiceDescriptor item, String apiKeyId, String apiPassword) {
		URL endpoint;
		try {
			endpoint = new URL(item.getServiceUrl());
		} catch(MalformedURLException murle) {
			throw new IllegalArgumentException("Invalid ServiceDescriptor specified. No API endpoint found.", murle);
		}
		this.client = new HttpClient(endpoint, apiKeyId, apiPassword);
	}

	/**
	 * Constructs a <code>S4ServiceClient</code> for accessing a specific processing
	 * pipeline on the s4.ontotext.com platform using the given credentials.
	 * 
	 * @param endpoint the URL of the pipeline which will be used for processing
	 * @param apiKeyId apiKeyId API key ID for authentication
	 * @param apiPassword corresponding password
	 */
	public S4ServiceClient(URL endpoint, String apiKeyId, String apiPassword) {
		this.client = new HttpClient(endpoint, apiKeyId, apiPassword);
	}

	/**
	 * Annotates a single document with the specified MIME type. Returns an object which allows
     * for convenient access to the annotations in the annotated document.
	 * 
	 * @param documentText the document content to annotate
	 * @param documentMimeType the MIME type of the document which will be annotated
	 * @return an {@link AnnotatedDocument} containing the original content as well as the annotations produced
	 * @throws S4ServiceClientException
	 */
	public AnnotatedDocument annotateDocument(String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

		ServiceRequest rq =	new ServiceRequest(documentText, documentMimeType);
		return processRequest(rq, true);
	}

	/**
     * Classifies a single document with the specified MIME type. Returns an object which allows
     * for convenient access to the classification information of the document.
     *
     * @param documentText the document content to classify
     * @param documentMimeType the MIME type of the document which will be classified
	 * @return an {@link ClassifiedDocument} containing the original content as well as the classifications produced
	 * @throws S4ServiceClientException
     */
    public ClassifiedDocument classifyDocument(String documentText, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
        return classifyRequest(rq, false);
    }

	/**
	 * Annotates the contents of a single file with the specified MIME type. Returns an object which allows
     * for convenient access to the annotations in the annotated document.
	 * 
	 * @param documentContent the file whose contents will be annotated
	 * @param documentEncoding the encoding of the document file
	 * @param documentMimeType the MIME type of the document to annotated content as well as the annotations produced
	 * @throws IOException
	 * @throws S4ServiceClientException
	 */
	public AnnotatedDocument annotateFileContents(File documentContent, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException, S4ServiceClientException {

		Path documentPath = documentContent.toPath();
		if(!Files.isReadable(documentPath)) {
			throw new IOException("File " + documentPath.toString()
					+ " is not readable.");
		}
		ByteBuffer buff;
		buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
		String content = documentEncoding.decode(buff).toString();

		return annotateDocument(content, documentMimeType);
	}

    /**
     * Classifies the contents of a single file with the specified MIME type. Returns an object which allows
     * for convenient access to the classification information for the document.
     *
     * @param documentContent the file whose contents will be classified
     * @param documentEncoding the encoding of the document file
     * @param documentMimeType the MIME type of the document to classified content as well as the classifications produced
     * @throws IOException
     * @throws S4ServiceClientException
     */
    public ClassifiedDocument classifyFileContents(File documentContent, Charset documentEncoding, SupportedMimeType documentMimeType)
            throws IOException, S4ServiceClientException {

        Path documentPath = documentContent.toPath();
        if(!Files.isReadable(documentPath)) {
            throw new IOException("File " + documentPath.toString()	+ " is not readable.");
        }
        ByteBuffer buff;
        buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
        String content = documentEncoding.decode(buff).toString();

        return classifyDocument(content, documentMimeType);
    }

	/**
	 * Annotates a single document publicly available under a given URL. Returns an object which allows
     * for convenient access to the annotations in the annotated document
	 * 
	 * @param documentUrl the publicly accessible URL from where the document will be downloaded
	 * @param documentMimeType the MIME type of the document which will be annotated
	 * @return an {@link AnnotatedDocument} which allows for convenient programmatic access to the annotated document
	 * @throws S4ServiceClientException
	 */
	public AnnotatedDocument annotateDocumentFromUrl(URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
		return processRequest(rq, true);
	}

    /**
     * Classifies a single document publicly available under a given URL. Returns an object which allows
     * for convenient access to the classifications in the classified document
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be classified
     * @return an {@link ClassifiedDocument} which allows for convenient programmatic access to the classified document
     * @throws S4ServiceClientException
     */
    public ClassifiedDocument classifyDocumentFromUrl(URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
        return classifyRequest(rq, true);
    }

	/**
	 * Annotates a single document and returns an {@link InputStream} from
	 * which the contents of the serialized annotated document can be read
	 * 
	 * @param documentText the contents of the document which will be annotated
	 * @param documentMimeType the MIME type of the file which will be annotated
	 * @param serializationFormat the format which will be used for serialization of the annotated document
	 * @return an {@link InputStream} from which the serialization of the annotated document can be read
	 * @throws S4ServiceClientException
	 */
	public InputStream annotateDocumentAsStream(String documentText, SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
            throws S4ServiceClientException {

		ServiceRequest rq =
				new ServiceRequest(documentText, documentMimeType);
		try {
			return client.requestForStream("", "POST", rq, "Accept", serializationFormat.acceptHeader);
		} catch(HttpClientException e) {
			JsonNode response = e.getResponse();
			if(response == null) {
				throw new S4ServiceClientException(e.getMessage(), e);
			}
			JsonNode msg = response.get("message");
			throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
		}
	}

	/**
     * Classifies a single document and returns an {@link InputStream} from
     * which the contents of the serialized annotated document can be read
     *
     * @param documentText the contents of the document which will be classified
     * @param documentMimeType the MIME type of the file which will be classified
	 * @return an {@link InputStream} from which the serialization of the classified document can be read
	 * @throws S4ServiceClientException
     */
	public InputStream classifyDocumentAsStream(
			String documentText, SupportedMimeType documentMimeType)
			throws S4ServiceClientException {

		ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
		try {
			return client.requestForStream("", "POST", rq, "Accept", ResponseFormat.JSON.acceptHeader);
		} catch(HttpClientException e) {
			JsonNode response = e.getResponse();
			if(response == null) {
				throw new S4ServiceClientException(e.getMessage(), e);
			}
			JsonNode msg = response.get("message");
			throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
		}
	}

	/**
	 * Annotates the contents of a single file returning an
	 * {@link InputStream} from which the annotated content can be read
	 * 
	 * @param documentContent the file which will be annotated
	 * @param documentEncoding the encoding of the file which will be annotated
	 * @param documentMimeType the MIME type of the file which will be annotated
	 * @param serializationFormat the serialization format used for the annotated content
     *
	 * @throws IOException if there are problems reading the contents of the file
	 * @throws S4ServiceClientException
	 */
	public InputStream annotateFileContentsAsStream(
            File documentContent, Charset documentEncoding, SupportedMimeType documentMimeType,
			ResponseFormat serializationFormat) throws IOException,
			S4ServiceClientException {

		Path documentPath = documentContent.toPath();
		if(!Files.isReadable(documentPath)) {
			throw new IOException("File " + documentPath.toString()
					+ " is not readable.");
		}
		ByteBuffer buff;
		buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
		String content = documentEncoding.decode(buff).toString();

		return annotateDocumentAsStream(content, documentMimeType, serializationFormat);
	}

	/**
     * Classifies the contents of a single file returning an
     * {@link InputStream} from which the classification information can be read
     *
     * @param documentContent the file which will be classified
     * @param documentEncoding the encoding of the file which will be classified
     * @param documentMimeType the MIME type of the file which will be classified
     * 
     * @return Service response raw content
     *
     * @throws IOException if there are problems reading the contents of the file
     * @throws S4ServiceClientException
     */
	public InputStream classifyFileContentsAsStream(
			File documentContent, Charset documentEncoding,	SupportedMimeType documentMimeType)
					throws IOException,	S4ServiceClientException {

		Path documentPath = documentContent.toPath();
		if(!Files.isReadable(documentPath)) {
			throw new IOException("File " + documentPath.toString()
					+ " is not readable.");
		}
		ByteBuffer buff;
		buff = ByteBuffer.wrap(Files.readAllBytes(documentPath));
		String content = documentEncoding.decode(buff).toString();
		return classifyDocumentAsStream(content, documentMimeType);
	}

	/**
	 * Annotates a single document publicly available under a given URL.
	 * Returns the annotated document serialized into the specified format
	 * 
	 * @param documentUrl the publicly accessible URL from where the document will be downloaded
	 * @param documentMimeType the MIME type of the document which will be annotated
	 * @param serializationFormat the serialization format of the output
	 * @return an {@link InputStream} from where the serialized output can be read
	 * @throws S4ServiceClientException
	 */
	public InputStream annotateDocumentFromUrlAsStream(URL documentUrl,
			SupportedMimeType documentMimeType, ResponseFormat serializationFormat)
					throws S4ServiceClientException {

		ServiceRequest rq =
				new ServiceRequest(documentUrl, documentMimeType);
		try {
			return client.requestForStream("", "POST", rq, "Accept",
					serializationFormat.acceptHeader);
		} catch(HttpClientException e) {
			JsonNode response = e.getResponse();
			if(response == null) {
				throw new S4ServiceClientException(e.getMessage(), e);
			}
			JsonNode msg = response.get("message");
			throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
		}
	}

    /**
     * Classifies a single document publicly available under a given URL.
     * Returns the classified document serialized into the specified format
     *
     * @param documentUrl the publicly accessible URL from where the document will be downloaded
     * @param documentMimeType the MIME type of the document which will be classified
     * @return an {@link InputStream} from where the serialized output can be read
     * @throws S4ServiceClientException
     */
    public InputStream classifyDocumentFromUrlAsStream(
            URL documentUrl, SupportedMimeType documentMimeType)
            throws S4ServiceClientException {

        ServiceRequest rq = new ServiceRequest(documentUrl, documentMimeType);
        try {
            return client.requestForStream("", "POST", rq, "Accept",
            		ResponseFormat.JSON.acceptHeader);
        } catch(HttpClientException e) {
            JsonNode response = e.getResponse();
            if(response == null) {
                throw new S4ServiceClientException(e.getMessage(), e);
            }
            JsonNode msg = response.get("message");
            throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
        }
    }

	/**
	 * This low level method allows the user to explicitly specify all the parameters sent to the service.
     * This is done by constructing the appropriate ServiceRequest object.
     * Returns the contents of the annotated document
	 * 
	 * @param rq the request which will be sent
	 * @param serializationFormat the format in which to output the annotated document
	 * @param requestCompression whether to allow GZIP compression for large documents
	 * @return an{@link InputStream} for the serialization of the annotated document in the specified format
	 * @throws S4ServiceClientException
	 */
	public InputStream processRequestForStream(
            ServiceRequest rq, ResponseFormat serializationFormat, boolean requestCompression)
            throws S4ServiceClientException {

		try {
			if(requestCompression) {
				return client.requestForStream("", "POST", rq, "Accept",
						serializationFormat.acceptHeader, "Accept-Encoding", "gzip");
			} else {
				return client.requestForStream("", "POST", rq, "Accept",
						serializationFormat.acceptHeader);
			}
		} catch(HttpClientException e) {
			JsonNode response = e.getResponse();
			if(response == null) {
				throw new S4ServiceClientException(e.getMessage(), e);
			}
			JsonNode msg = response.get("message");
			throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
		}
	}

	/**
	 * This low level method allows the user to specify every parameter explicitly by setting the properties
     * of the OnlineService request object. Returns an object which wraps the annotated document.
	 * 
	 * @param rq the request which will be sent to the service
	 * @param requestCompression whether to allow GZIP compression for large documents
	 * @return an {@link AnnotatedDocument} containing the original content as well as the annotations produced
	 * @throws S4ServiceClientException
	 */
	public AnnotatedDocument processRequest(ServiceRequest rq, boolean requestCompression)
            throws S4ServiceClientException {
		try {

			if(requestCompression) {
				return client.request("", "POST", new TypeReference<AnnotatedDocument>() {}, rq,
                        "Accept", ResponseFormat.GATE_JSON.acceptHeader, "Accept-Encoding", "gzip");
			} else {
				return client.request("", "POST", new TypeReference<AnnotatedDocument>() {}, rq,
                        "Accept", ResponseFormat.GATE_JSON.acceptHeader);
			}
		} catch(HttpClientException e) {
			JsonNode response = e.getResponse();
			if(response == null) {
				throw new S4ServiceClientException(e.getMessage(), e);
			}
			JsonNode msg = response.get("message");
			throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(),
					e);
		}
	}

    /**
     * This low level method allows the user to specify every parameter explicitly by setting the properties
     * of the OnlineService request object. Returns an object which wraps the classified document.
     *
     * @param rq the request which will be sent
     * @param requestCompression whether to allow GZIP compression for large documents
     * @return a {@link ClassifiedDocument} containing the original content as well as the annotations produced
     * @throws S4ServiceClientException
     */
    public ClassifiedDocument classifyRequest(ServiceRequest rq, boolean requestCompression)
            throws S4ServiceClientException {

        try {
            if(requestCompression) {
                return client.request("", "POST", new TypeReference<ClassifiedDocument>() {}, rq,
                        "Accept", ResponseFormat.JSON.acceptHeader, "Accept-Encoding", "gzip");
            } else {
                return client.request("", "POST", new TypeReference<ClassifiedDocument>() {}, rq,
                        "Accept", ResponseFormat.JSON.acceptHeader);
            }
        } catch(HttpClientException e) {
            JsonNode response = e.getResponse();
            if(response == null) {
                throw new S4ServiceClientException(e.getMessage(), e);
            }
            JsonNode msg = response.get("message");
            throw new S4ServiceClientException(msg == null ? e.getMessage() : msg.asText(), e);
        }
    }

	public static void main(String... args) {
		if (args == null 
				|| args.length == 0) {
			printUsageAndTerminate(null);
		}
		Parameters params = new Parameters(args);
		String serviceID = params.getValue("service");
		if (serviceID == null) {
			printUsageAndTerminate("No service name provided");

		}
		ServiceDescriptor service = null;
		try {
			service = ServicesCatalog.getItem(serviceID);
		}
		catch(UnsupportedOperationException uoe) {
			printUsageAndTerminate("Unsupported service '" + serviceID + '\'');
		}
		SupportedMimeType mimetype = SupportedMimeType.PLAINTEXT;
		if (params.getValue("dtype") != null) {
			try {
				mimetype = SupportedMimeType.valueOf(params.getValue("dtype"));
			}
			catch(IllegalArgumentException iae) {
				printUsageAndTerminate("Unsupported document type (dtype) : " + params.getValue("dtype"));
			}
		}
		String inFile = params.getValue("file");
		String url = params.getValue("url");
		String outFile = params.getValue("out", "result.json");

		if (inFile != null) {
			if (!new File(inFile).exists()) {
				printUsageAndTerminate("Input file is not found : " + inFile);
			}
		}
		else {
			if (url == null) {
				printUsageAndTerminate("Neither input file, nor remote URL provided");
			}
		}

		Properties creds = readCredentials(params);
		if (!creds.containsKey("apikey")
				|| !creds.containsKey("secret")) {
			printUsageAndTerminate("No credentials details found");
		}

		S4ServiceClient client = new S4ServiceClient(service, creds.getProperty("apikey"), creds.getProperty("secret"));

		try {
			InputStream resultData = null;
			if (service.getName().equals("news-classifier")) {
				resultData = (inFile != null) ?
						client.classifyFileContentsAsStream(new File(inFile), Charset.forName("UTF-8"), mimetype)
						: client.classifyDocumentFromUrlAsStream(new URL(url), mimetype);
			}
			else {
				resultData = (inFile != null) ?
						client.annotateFileContentsAsStream(new File(inFile), Charset.forName("UTF-8"), mimetype, ResponseFormat.JSON)
						: client.annotateDocumentFromUrlAsStream(new URL(url), mimetype, ResponseFormat.JSON);
			}
			FileOutputStream outStream = new FileOutputStream(outFile);
			IOUtils.copy(resultData, outStream);
			
			outStream.close();
			resultData.close();
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			System.exit(1);
		}

	}

	private static void printUsageAndTerminate(String error) {
		if (error != null) {
			System.out.println(error);
		}
		System.out.println("Usage: S4ClientService parameter1=value1 parameter2=value2 ...");
		System.out.println("Parameters:");
		System.out.println("  service - the service id to be used (one of: 'TwitIE', 'SBT', 'news' and 'news-classifier')");
		System.out.println("  file    - input file path");
		System.out.println("  url     - input document URL");
		System.out.println("  dtype   - the type of the document (one of:'PLAINTEXT', 'HTML', 'XML_APPLICATION', 'XML_TEXT', 'PUBMED', 'COCHRANE', 'MEDIAWIKI', 'TWITTER_JSON')");
		System.out.println("  out     - result file name. Defaults to 'result.json'");
		System.out.println("  apikey  - the api key if credentials file is not used");
		System.out.println("  secret  - the api secret if credentials file is not used");
		System.out.println("  creds   - credentails file path (if apikey and secret parameters are not used)");
		System.exit(1);
	}

	private static Properties readCredentials(Parameters params) {
		Properties props = new Properties();

		if (params.getValue("apikey") != null) {
			if (params.getValue("secret") == null) {
				printUsageAndTerminate("API key secret not provided");
			}
			props.setProperty("apikey", params.getValue("apikey"));
			props.setProperty("secret", params.getValue("secret"));
			return props;
		}
		String credsFile = "s4credentials.properties";
		if (params.getValue("creds") != null) {
			credsFile = params.getValue("creds");
		}
		if (new File(credsFile).exists()) {
			try {
				props.load(new FileInputStream(credsFile));
			}
			catch(IOException ex) {
				printUsageAndTerminate("Error reading credentials file: " + ex.getMessage());
			}
		}
		else {
			InputStream inStr = Thread.currentThread().getContextClassLoader().getResourceAsStream(credsFile);
			if (inStr != null) {
				try {
					props.load(inStr);
				}
				catch(IOException ioe) {
					printUsageAndTerminate("Error reading credentials file: " + ioe.getMessage());
				}
			}
		}
		return props;
	}

}
