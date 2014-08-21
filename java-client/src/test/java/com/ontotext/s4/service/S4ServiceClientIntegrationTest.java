package com.ontotext.s4.service;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import com.ontotext.s4.service.AnnotatedDocument;
import com.ontotext.s4.service.S4ServiceClient;
import com.ontotext.s4.service.ServiceRequest;
import com.ontotext.s4.service.ResponseFormat;
import com.ontotext.s4.service.SupportedMimeType;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class S4ServiceClientIntegrationTest {

	private static final String testApiKeyId = "";
	private static final String testApiKeyPass = "";
	private static final String pipelineEndpoint = "https://text.s4.ontotext.com/v1/twitie";


	private S4ServiceClient api;

	public S4ServiceClientIntegrationTest() throws MalformedURLException {
		this.api = new S4ServiceClient(new URL(pipelineEndpoint), testApiKeyId, testApiKeyPass);
	}


	@Test
	public void testAnnotateDocument() throws Exception {
		String documentText = "Barack Obama is the president of the USA";
		SupportedMimeType documentMimeType = SupportedMimeType.PLAINTEXT;
		AnnotatedDocument result = api.annotateDocument(documentText, documentMimeType);
		assertEquals(result.text, documentText);
		assertNotNull(result.entities);
		assertTrue(result.entities.size() > 0);
	}	

	@Test 
	public void testAnnotateDocumentFromUrl() throws Exception {
		AnnotatedDocument result = api.annotateDocumentFromUrl(new URL("http://en.wikipedia.org/wiki/Jimi_Hendrix"), SupportedMimeType.HTML);
		assertTrue(result.text.contains("Hendrix"));
	}

	@Test 
	public void testAnnotateFileContents() throws Exception {
		File f = new File("test-file");
		try {
			Path p = f.toPath();
			ArrayList<String> lines = new ArrayList<String>(1);
			lines.add("Barack Obama is the president of the USA.");
			Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			AnnotatedDocument result = api.annotateFileContents(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
			assertTrue(result.text.contains("Barack"));
		} finally {
			f.delete();
		}
	}
	
	@Test
	public void testAnnotateDocumentAsStream() throws Exception {
		String documentText = "Barack Obama is the president of the USA";
		SupportedMimeType documentMimeType = SupportedMimeType.PLAINTEXT;
		ResponseFormat serializationFormat = ResponseFormat.JSON;
		InputStream result = api.annotateDocumentAsStream(documentText, documentMimeType, serializationFormat);
		StringWriter w = new StringWriter();
		IOUtils.copy(result, w, Charset.forName("UTF-8"));
		assertTrue(w.toString().contains("Barack"));
	}
	
	@Test
	public void testProcessRequestForStream() throws Exception {
		String documentText = "Barack Obama is the president of the USA";
		ResponseFormat serializationFormat = ResponseFormat.GATE_XML;		
		ServiceRequest rq = new ServiceRequest(documentText, SupportedMimeType.PLAINTEXT, null);
		InputStream result = api.processRequestForStream(rq, serializationFormat, false);
		StringWriter w = new StringWriter();
		IOUtils.copy(result, w, Charset.forName("UTF-8"));
		
		assertTrue(w.toString().contains("Barack"));
	}
}
