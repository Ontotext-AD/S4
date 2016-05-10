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
package com.ontotext.s4.service.impl;


import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.catalog.ServicesCatalog;
import com.ontotext.s4.model.annotation.AnnotatedDocument;
import com.ontotext.s4.service.S4AnnotationClient;
import com.ontotext.s4.service.util.ResponseFormat;
import com.ontotext.s4.service.util.ServiceRequest;
import com.ontotext.s4.service.util.SupportedMimeType;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class S4AnnotationClientTest {
    final static String testApiKeyId = "";
	final static String testApiKeyPass = "";

    final static ServiceDescriptor descriptor = ServicesCatalog.getItem("twitie");

    String documentText;

	static S4AnnotationClient apiDesc;
    static S4AnnotationClient apiUrl;

    static URL serviceUrl;
    static URL imageURL;

    @BeforeClass
	public static void setUpBeforeClass() throws MalformedURLException {
        serviceUrl = new URL("https://text.s4.ontotext.com/v1/twitie");

        imageURL = new URL("http://www.bbc.com/news/world-us-canada-36020717");

		apiDesc = new S4AnnotationClientImpl(descriptor, testApiKeyId, testApiKeyPass);
        apiUrl = new S4AnnotationClientImpl(serviceUrl, testApiKeyId, testApiKeyPass);
	}

    @Before
    public void setUp() {
        documentText = "Barack Obama is the president of the USA.";
    }

    @Test
    public void testAnnotateDocumentFromStringDescClient() {
        AnnotatedDocument result = apiDesc.annotateDocument(documentText, SupportedMimeType.PLAINTEXT);
		assertEquals(result.getText(), documentText);
		assertNotNull(result.getEntities());
		assertTrue(result.getEntities().size() > 0);
    }

    @Test
    public void testAnnotateDocumentFromStringUrlClient() {
        AnnotatedDocument result = apiUrl.annotateDocument(documentText, SupportedMimeType.PLAINTEXT);
        assertEquals(result.getText(), documentText);
        assertNotNull(result.getEntities());
        assertTrue(result.getEntities().size() > 0);
    }

    @Test
    public void testAnnotateDocumentFromUrlDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
		AnnotatedDocument result = apiDesc.annotateDocument(example, SupportedMimeType.HTML);
		assertTrue(result.getText().contains("Christian Slater"));
    }

    @Test
    public void testAnnotateDocumentFromUrlUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        AnnotatedDocument result = apiUrl.annotateDocument(example, SupportedMimeType.HTML);
        assertTrue(result.getText().contains("Christian Slater"));
    }

    @Test
    public void testAnnotateDocumentFromFileDescClient() throws Exception {
        File f = new File("test-file");
		try {
			Path p = f.toPath();
			ArrayList<String> lines = new ArrayList<>(1);
			lines.add(documentText);
			Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			AnnotatedDocument result = apiDesc.annotateDocument(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
			assertTrue(result.getText().contains("Barack"));
		} finally {
			f.delete();
		}
    }

    @Test
    public void testAnnotateDocumentFromFileUrlClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            AnnotatedDocument result = apiUrl.annotateDocument(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
            assertTrue(result.getText().contains("Barack"));
        } finally {
            f.delete();
        }
    }

    @Test
    public void testProcessImagesDescClient() {
        AnnotatedDocument doc = apiDesc.annotateDocument(imageURL, SupportedMimeType.HTML, true, true);
        assertNotNull(doc.getImages());
    }

    @Test
    public void testProcessImagesUrlClient() {
        AnnotatedDocument doc = apiUrl.annotateDocument(imageURL, SupportedMimeType.HTML, true, true);
        assertNotNull(doc.getImages());
    }

    @Test
    public void testGetImagesWhenImageClassificationIsNotOnDescClient() throws Exception {
        AnnotatedDocument doc = apiDesc.annotateDocument(imageURL, SupportedMimeType.HTML);
        assertNull(doc.getImages());
    }

    @Test
    public void testGetImagesWhenImageClassificationIsNotOnUrlClient() throws Exception {
        AnnotatedDocument doc = apiUrl.annotateDocument(imageURL, SupportedMimeType.HTML);
        assertNull(doc.getImages());
    }

    @Test
    public void testImageTaggingDescClient() {
        AnnotatedDocument doc = apiDesc.annotateDocument(imageURL, SupportedMimeType.HTML, true, false);
        assertNull(doc.getImages().get(0).getCategories());
        assertNotNull(doc.getImages().get(0).getTags());
    }

    @Test
    public void testImageTaggingUrlClient() {
        AnnotatedDocument doc = apiUrl.annotateDocument(imageURL, SupportedMimeType.HTML, true, false);
        assertNull(doc.getImages().get(0).getCategories());
        assertNotNull(doc.getImages().get(0).getTags());
    }

    @Test
    public void testImageCategorizationDescClient() {
        AnnotatedDocument doc = apiDesc.annotateDocument(imageURL, SupportedMimeType.HTML, false, true);
        assertNotNull(doc.getImages().get(0).getCategories());
        assertNull(doc.getImages().get(0).getTags());
    }

    @Test
    public void testImageCategorizationUrlClient() {
        AnnotatedDocument doc = apiUrl.annotateDocument(imageURL, SupportedMimeType.HTML, false, true);
        assertNotNull(doc.getImages().get(0).getCategories());
        assertNull(doc.getImages().get(0).getTags());
    }

    @Test
    public void testAnnotateDocumentAsStreamFromStringDescClient() throws Exception {
		InputStream result = apiDesc.annotateDocumentAsStream(documentText, SupportedMimeType.PLAINTEXT, ResponseFormat.JSON);
		StringWriter w = new StringWriter();
		IOUtils.copy(result, w, Charset.forName("UTF-8"));

		assertTrue(w.toString().contains("Barack"));
    }

    @Test
    public void testAnnotateDocumentAsStreamFromStringUrlClient() throws Exception {
        InputStream result = apiUrl.annotateDocumentAsStream(documentText, SupportedMimeType.PLAINTEXT, ResponseFormat.JSON);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Barack"));
    }

    @Test
    public void testAnnotateDocumentAsStreamFromFileDescClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            InputStream result = apiDesc.annotateDocumentAsStream(
                    f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT, ResponseFormat.JSON);
            StringWriter w = new StringWriter();
            IOUtils.copy(result, w, Charset.forName("UTF-8"));

            assertTrue(w.toString().contains("Barack"));
        } finally {
            f.delete();
        }
    }

    @Test
    public void testAnnotateDocumentAsStreamFromFileUrlClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            InputStream result = apiUrl.annotateDocumentAsStream(
                    f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT, ResponseFormat.JSON);
            StringWriter w = new StringWriter();
            IOUtils.copy(result, w, Charset.forName("UTF-8"));

            assertTrue(w.toString().contains("Barack"));
        } finally {
            f.delete();
        }
    }

    @Test
    public void testAnnotateDocumentAsStreamFromUrlDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        InputStream result = apiDesc.annotateDocumentAsStream(example, SupportedMimeType.HTML, ResponseFormat.JSON);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Christian Slater"));
    }

    @Test
    public void testAnnotateDocumentAsStreamFromUrlUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        InputStream result = apiUrl.annotateDocumentAsStream(example, SupportedMimeType.HTML, ResponseFormat.JSON);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Christian Slater"));
    }

    @Test
    public void testProcessImagesAsStreamDescClient() throws Exception {
        InputStream result = apiDesc.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, true, true);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertTrue(writer.contains("\"images\""));
        assertTrue(writer.contains("\"tags\""));
        assertTrue(writer.contains("\"categories\""));
        assertTrue(writer.contains("\"tagging_id\""));
        assertTrue(writer.contains("\"image\""));
    }

    @Test
    public void testProcessImagesAsStreamUrlClient() throws Exception {
        InputStream result = apiUrl.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, true, true);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertTrue(writer.contains("\"images\""));
        assertTrue(writer.contains("\"tags\""));
        assertTrue(writer.contains("\"categories\""));
        assertTrue(writer.contains("\"tagging_id\""));
        assertTrue(writer.contains("\"image\""));
    }

    @Test
    public void testImageTaggingAsStreamDescClient() throws Exception {
        InputStream result = apiDesc.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, true, false);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertTrue(writer.contains("\"images\""));
        assertTrue(writer.contains("\"tags\""));
        assertFalse(writer.contains("\"categories\""));
        assertTrue(writer.contains("\"tagging_id\""));
        assertTrue(writer.contains("\"image\""));
    }

    @Test
    public void testImageTaggingAsStreamUrlClient() throws Exception {
        InputStream result = apiUrl.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, true, false);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertTrue(writer.contains("\"images\""));
        assertTrue(writer.contains("\"tags\""));
        assertFalse(writer.contains("\"categories\""));
        assertTrue(writer.contains("\"tagging_id\""));
        assertTrue(writer.contains("\"image\""));
    }

    @Test
    public void testImageCategorizationAsStreamDescClient() throws Exception {
        InputStream result = apiDesc.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, false, true);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertTrue(writer.contains("\"images\""));
        assertFalse(writer.contains("\"tags\""));
        assertTrue(writer.contains("\"categories\""));
        assertFalse(writer.contains("\"tagging_id\""));
        assertTrue(writer.contains("\"image\""));
    }

    @Test
    public void testImageCategorizationAsStreamUrlClient() throws Exception {
        InputStream result = apiUrl.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, false, true);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertTrue(writer.contains("\"images\""));
        assertFalse(writer.contains("\"tags\""));
        assertTrue(writer.contains("\"categories\""));
        assertFalse(writer.contains("\"tagging_id\""));
        assertTrue(writer.contains("\"image\""));
    }

    @Test
    public void testGetImagesAsStreamWhenImageClassificationIsNotOnDescClient() throws Exception {
        InputStream result = apiDesc.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, false, false);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertFalse(writer.contains("\"images\""));
        assertFalse(writer.contains("\"tags\""));
        assertFalse(writer.contains("\"categories\""));
        assertFalse(writer.contains("\"tagging_id\""));
    }

    @Test
    public void testGetImagesAsStreamWhenImageClassificationIsNotOnUrlClient() throws Exception {
        InputStream result = apiUrl.annotateDocumentAsStream(
                imageURL, SupportedMimeType.HTML, ResponseFormat.JSON, false, false);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        String writer = w.toString();

        assertFalse(writer.contains("\"images\""));
        assertFalse(writer.contains("\"tags\""));
        assertFalse(writer.contains("\"categories\""));
        assertFalse(writer.contains("\"tagging_id\""));
    }

    @Test
	public void testProcessRequestForStreamDescClient() throws Exception {
		ServiceRequest rq = new ServiceRequest(documentText, SupportedMimeType.PLAINTEXT);
		InputStream result = apiDesc.processRequestForStream(rq, ResponseFormat.GATE_XML);
		StringWriter w = new StringWriter();
		IOUtils.copy(result, w, Charset.forName("UTF-8"));

		assertTrue(w.toString().contains("Barack"));
	}

    @Test
    public void testProcessRequestForStreamUrlClient() throws Exception {
        ServiceRequest rq = new ServiceRequest(documentText, SupportedMimeType.PLAINTEXT);
        InputStream result = apiUrl.processRequestForStream(rq, ResponseFormat.GATE_XML);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Barack"));
    }
}
