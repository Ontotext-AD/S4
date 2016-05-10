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


import com.ontotext.s4.model.classification.ClassifiedDocument;
import com.ontotext.s4.service.S4ClassificationClient;
import com.ontotext.s4.service.ServiceClientsFactory;
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

@Ignore
public class S4ClassificationClientTest {

    final static String testApiKeyId = "";
    final static String testApiKeyPass = "";

    String documentText;

    static S4ClassificationClient apiDesc;
    static S4ClassificationClient apiUrl;

    static URL serviceUrl;

    @BeforeClass
    public static void setUpBeforeClass() throws MalformedURLException {
        serviceUrl = new URL("https://text.s4.ontotext.com/v1/news-classifier");

        apiDesc = ServiceClientsFactory.createClassificationClient(testApiKeyId, testApiKeyPass);
        apiUrl = ServiceClientsFactory.createClassificationClient(serviceUrl, testApiKeyId, testApiKeyPass);
    }

    @Before
    public void setUp() {
        documentText = "Barack Obama is the president of the USA.";
    }


    @Test
    public void testClassifyDocumentFromStringDescClient() {
        ClassifiedDocument result = apiDesc.classifyDocument(documentText, SupportedMimeType.PLAINTEXT);
        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

    @Test
    public void testClassifyDocumentFromStringUrlClient() {
        ClassifiedDocument result = apiUrl.classifyDocument(documentText, SupportedMimeType.PLAINTEXT);
        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

    @Test
    public void testClassifyDocumentFromUrlDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");

        ClassifiedDocument result = apiDesc.classifyDocument(example, SupportedMimeType.HTML);
        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

    @Test
    public void testClassifyDocumentFromUrlUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");

        ClassifiedDocument result = apiUrl.classifyDocument(example, SupportedMimeType.HTML);
        assertNotNull(result.getCategory());
        assertEquals(3, result.getAllScores().size());
    }

    @Test
    public void testClassifyDocumentFromFileDescClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            ClassifiedDocument result = apiDesc.classifyDocument(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
            assertNotNull(result.getCategory());
            assertEquals(3, result.getAllScores().size());
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testClassifyDocumentFromFileUrlClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            ClassifiedDocument result = apiUrl.classifyDocument(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
            assertNotNull(result.getCategory());
            assertEquals(3, result.getAllScores().size());
        }
        finally {
            f.delete();
        }
    }

    @Test
    public void testClassifyDocumentAsStreamFromStringDescClient() throws Exception {
        InputStream result = apiDesc.classifyDocumentAsStream(documentText, SupportedMimeType.PLAINTEXT);
        StringWriter writer = new StringWriter();
        IOUtils.copy(result, writer, Charset.forName("UTF-8"));

        assertTrue(writer.toString().contains("category"));
        assertTrue(writer.toString().contains("allScores"));
    }

    @Test
    public void testClassifyDocumentAsStreamFromStringUrlClient() throws Exception {
        InputStream result = apiUrl.classifyDocumentAsStream(documentText, SupportedMimeType.PLAINTEXT);
        StringWriter writer = new StringWriter();
        IOUtils.copy(result, writer, Charset.forName("UTF-8"));

        assertTrue(writer.toString().contains("category"));
        assertTrue(writer.toString().contains("allScores"));
    }

    @Test
    public void testClassifyDocumentAsStreamFromUrlDescClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        InputStream result = apiDesc.classifyDocumentAsStream(example, SupportedMimeType.HTML);
        StringWriter writer = new StringWriter();
        IOUtils.copy(result, writer, Charset.forName("UTF-8"));

        assertTrue(writer.toString().contains("category"));
        assertTrue(writer.toString().contains("allScores"));
    }

    @Test
    public void testClassifyDocumentAsStreamFromUrlUrlClient() throws Exception {
        URL example = new URL("https://en.wikipedia.org/wiki/Christian_Slater");
        InputStream result = apiUrl.classifyDocumentAsStream(example, SupportedMimeType.HTML);
        StringWriter writer = new StringWriter();
        IOUtils.copy(result, writer, Charset.forName("UTF-8"));

        assertTrue(writer.toString().contains("category"));
        assertTrue(writer.toString().contains("allScores"));
    }

    @Test
    public void testClassifyDocumentAsStreamFromFileDescClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);

            InputStream result = apiDesc.classifyDocumentAsStream(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
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
    public void testClassifyDocumentAsStreamFromFileUrlClient() throws Exception {
        File f = new File("test-file");
        try {
            Path p = f.toPath();
            ArrayList<String> lines = new ArrayList<>(1);
            lines.add(documentText);
            Files.write(p, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);

            InputStream result = apiUrl.classifyDocumentAsStream(f, Charset.forName("UTF-8"), SupportedMimeType.PLAINTEXT);
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
