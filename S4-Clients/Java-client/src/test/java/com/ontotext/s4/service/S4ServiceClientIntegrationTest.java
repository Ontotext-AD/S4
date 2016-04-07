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
import java.io.InputStream;
import java.io.StringWriter;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.catalog.ServicesCatalog;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class S4ServiceClientIntegrationTest {

	final static String testApiKeyId = "";
	final static String testApiKeyPass = "";

	final static ServiceDescriptor descriptor = ServicesCatalog.getItem("twitie");

    String documentText;
    SupportedMimeType documentMimeType;
    ResponseFormat serializationFormat;

	static S4ServiceClient apiDesc;
    static S4ServiceClient apiUrl;

    @BeforeClass
	public static void setUpBeforeClass() throws MalformedURLException {
        final URL url = new URL("https://text.s4.ontotext.com/v1/twitie");

		apiDesc = new S4ServiceClient(descriptor, testApiKeyId, testApiKeyPass);
        apiUrl = new S4ServiceClient(url, testApiKeyId, testApiKeyPass);
	}

    @Before
    public void setUp() {
        documentText = "Barack Obama is the president of the USA.";
        documentMimeType = SupportedMimeType.PLAINTEXT;
    }

	@Test
	public void testAnnotateDocumentDescClient() throws Exception {
		AnnotatedDocument result = apiDesc.annotateDocument(documentText, documentMimeType);
		assertEquals(result.getText(), documentText);
		assertNotNull(result.getEntities());
		assertTrue(result.getEntities().size() > 0);
	}

    @Test
    public void testClassifyDocumentDescClient() throws Exception {
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);

        ClassifiedDocument result = client.classifyDocument(documentText, documentMimeType);
        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

	@Test
	public void testAnnotateDocumentFromUrlDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
		AnnotatedDocument result = apiDesc.annotateDocumentFromUrl(example, SupportedMimeType.HTML);
		assertTrue(result.getText().contains("Christian Slater"));
	}

    @Test
    public void testClassifyDocumentFromUrlDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);

        ClassifiedDocument result = client.classifyDocumentFromUrl(example, SupportedMimeType.HTML);

        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

	@Test
	public void testAnnotateFileContentsDescClient() throws Exception {
		File f = new File("test-file");
		try {
			Path p = f.toPath();
			ArrayList<String> lines = new ArrayList<>(1);
			lines.add(documentText);
			Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			AnnotatedDocument result = apiDesc.annotateFileContents(f, Charset.forName("UTF-8"), documentMimeType);
			assertTrue(result.getText().contains("Barack"));
		} finally {
			f.delete();
		}
	}

    @Test
    public void testClassifyFileContentsDescClient() throws Exception {
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);

        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            ClassifiedDocument result = client.classifyFileContents(f, Charset.forName("UTF-8"), documentMimeType);
            assertNotNull(result.getCategory());
            assertEquals(3, result.getAllScores().size());
        }
        finally {
            f.delete();
        }
    }

	@Test
	public void testAnnotateDocumentAsStreamDescClient() throws Exception {
		serializationFormat = ResponseFormat.JSON;
		InputStream result = apiDesc.annotateDocumentAsStream(documentText, documentMimeType, serializationFormat);
		StringWriter w = new StringWriter();
		IOUtils.copy(result, w, Charset.forName("UTF-8"));

		assertTrue(w.toString().contains("Barack"));
	}

    @Test
    public void testClassifyDocumentAsStreamDescClient() throws Exception {
         InputStream result = apiDesc.classifyDocumentAsStream(documentText, documentMimeType);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Barack"));
    }

	@Test
	public void testProcessRequestForStreamDescClient() throws Exception {
		serializationFormat = ResponseFormat.GATE_XML;
		ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
		InputStream result = apiDesc.processRequestForStream(rq, serializationFormat, false);
		StringWriter w = new StringWriter();
		IOUtils.copy(result, w, Charset.forName("UTF-8"));

		assertTrue(w.toString().contains("Barack"));
	}

    @Test
    public void testClassifyDocumentFromUrlAsStreamDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        InputStream result = apiDesc.classifyDocumentFromUrlAsStream(example, SupportedMimeType.HTML);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Slater"));
    }

    @Test
    public void testClassifyFileContentsAsStreamDescClient() throws Exception {
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);
        serializationFormat = ResponseFormat.JSON;
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);

            InputStream result = client.annotateFileContentsAsStream(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT, serializationFormat);
            StringWriter writer = new StringWriter();
            IOUtils.copy(result, writer, Charset.forName("UTF-8"));

            assertTrue(writer.toString().contains("category"));
            assertTrue(writer.toString().contains("allScores"));
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testAnnotateDocumentUrlClient() throws Exception {
        AnnotatedDocument result = apiUrl.annotateDocument(documentText, documentMimeType);
        assertEquals(result.getText(), documentText);
        assertNotNull(result.getEntities());
        assertTrue(result.getEntities().size() > 0);
    }

    @Test
    public void testClassifyDocumentUrlClient() throws Exception {
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);

        ClassifiedDocument result = client.classifyDocument(documentText, documentMimeType);
        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

    @Test
    public void testAnnotateDocumentFromUrlUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        AnnotatedDocument result = apiUrl.annotateDocumentFromUrl(example, SupportedMimeType.HTML);
        assertTrue(result.getText().contains("Christian Slater"));
    }

    @Test
    public void testClassifyDocumentFromUrlUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);

        ClassifiedDocument result = client.classifyDocumentFromUrl(example, SupportedMimeType.HTML);

        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

    @Test
    public void testAnnotateFileContentsUrlClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            AnnotatedDocument result = apiUrl.annotateFileContents(f, Charset.forName("UTF-8"), documentMimeType);
            assertTrue(result.getText().contains("Barack"));
        } finally {
            f.delete();
        }
    }

    @Test
    public void testClassifyFileContentsUrlClient() throws Exception {
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);

        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            ClassifiedDocument result = client.classifyFileContents(f, Charset.forName("UTF-8"), documentMimeType);
            assertNotNull(result.getCategory());
            assertEquals(3, result.getAllScores().size());
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testAnnotateDocumentAsStreamUrlClient() throws Exception {
        serializationFormat = ResponseFormat.JSON;
        InputStream result = apiUrl.annotateDocumentAsStream(documentText, documentMimeType, serializationFormat);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Barack"));
    }

    @Test
    public void testClassifyDocumentAsStreamUrlClient() throws Exception {
        InputStream result = apiUrl.classifyDocumentAsStream(documentText, documentMimeType);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Barack"));
    }

    @Test
    public void testProcessRequestForStreamUrlClient() throws Exception {
        serializationFormat = ResponseFormat.GATE_XML;
        ServiceRequest rq = new ServiceRequest(documentText, documentMimeType);
        InputStream result = apiUrl.processRequestForStream(rq, serializationFormat, false);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Barack"));
    }

    @Test
    public void testClassifyDocumentFromUrlAsStreamUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        InputStream result = apiUrl.classifyDocumentFromUrlAsStream(example, SupportedMimeType.HTML);
        StringWriter w = new StringWriter();
        IOUtils.copy(result, w, Charset.forName("UTF-8"));

        assertTrue(w.toString().contains("Slater"));
    }

    @Test
    public void testClassifyFileContentsAsStreamUrlClient() throws Exception {
        ServiceDescriptor temp = ServicesCatalog.getItem("news-classifier");
        S4ServiceClient client = new S4ServiceClient(temp, testApiKeyId, testApiKeyPass);
        serializationFormat = ResponseFormat.JSON;
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);

            InputStream result = client.annotateFileContentsAsStream(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT, serializationFormat);
            StringWriter writer = new StringWriter();
            IOUtils.copy(result, writer, Charset.forName("UTF-8"));

            assertTrue(writer.toString().contains("category"));
            assertTrue(writer.toString().contains("allScores"));
        }
        finally {
            f.delete();
        }
    }
}
