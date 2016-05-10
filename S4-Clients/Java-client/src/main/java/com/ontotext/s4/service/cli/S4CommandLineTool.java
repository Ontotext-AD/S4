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
package com.ontotext.s4.service.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.catalog.ServicesCatalog;
import com.ontotext.s4.service.S4AnnotationClient;
import com.ontotext.s4.service.S4ClassificationClient;
import com.ontotext.s4.service.impl.S4AnnotationClientImpl;
import com.ontotext.s4.service.impl.S4ClassificationClientImpl;
import com.ontotext.s4.service.util.OutputMessages;
import com.ontotext.s4.service.util.ResponseFormat;
import com.ontotext.s4.service.util.SupportedMimeType;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;


public class S4CommandLineTool {

    private static class CommandLineParams {

        @Parameter(names = {"-k", "--api-key"}, required = true,
                description = "The S4 API key")
        private String apiKey;

        @Parameter(names = {"-s", "--key-secret"}, required = true,
                description = "The S4 key secret")
        private String keySecret;

        @Parameter(names = {"-t", "--service-type"}, required = true,
                description = "The S4 service ID to be used")
        private String serviceType;

        @Parameter(names = {"-f", "--file-path"}, required = false,
                description = "Input file path (somewhere in your local system)")
        private String filePath;

        @Parameter(names = {"-u", "--url-location"}, required = false,
                description = "Input URL document")
        private String url;

        @Parameter(names = {"-d", "--document-type"}, required = true,
                description = "Type of the document to be processed")
        private String documentType;

        @Parameter(names = {"-o", "--output-file"}, required = true,
                description = "Path to file where the service output will be stored")
        private String outputFileName;

        @Parameter(names = {"--img-tagging"}, required = false,
                description = "Get tags for the images found in the document ('-u', '--url-location' only)")
        private boolean imgTagging;

        @Parameter(names = {"--img-categorization"}, required = false,
                description = "Get categories for the images found in the document ('-u', '--url-location' only)")
        private boolean imgCategorization;

        @Parameter(names = {"-h", "--help"}, help = true,
                description = "Display this help text")
        private boolean help;
    }

    public static boolean validParams(CommandLineParams params) {
        if (params.url != null && params.filePath != null) {
            System.out.println(OutputMessages.BOTH_INPUTS_PASSED.message);
            return false;
        }

        if (params.url == null && params.filePath == null) {
            System.out.println(OutputMessages.NO_INPUTS_PASSED.message);
            return false;
        }

        if (params.imgTagging || params.imgCategorization) {
            if (params.url == null) {
                System.out.println(OutputMessages.URL_NOT_FOUND.message);
                return false;
            }
            if (params.serviceType.contains("classifier")) {
                System.out.println(OutputMessages.CLASSIFIER_USAGE.message);
                return false;
            }
        }

        try {
            ServicesCatalog.getItem(params.serviceType);
        }
        catch(UnsupportedOperationException uoe) {
            System.out.println(OutputMessages.UNSUPPORTED_SERVICE.message);
            return false;
        }

        try {
            SupportedMimeType.valueOf(params.documentType);
        }
        catch (IllegalArgumentException iae) {
            System.out.println(OutputMessages.UNSUPPORTED_MIME.message);
            return false;
        }
        return true;
    }

    public static InputStream executeAnnotationRequest(
            CommandLineParams params, S4AnnotationClient client, SupportedMimeType mimetype)
            throws IOException {

        if (params.filePath != null) {
            return client.annotateDocumentAsStream(new File(params.filePath), Charset.forName("UTF-8"), mimetype, ResponseFormat.JSON);
        } else if (params.imgTagging || params.imgCategorization) {
            return client.annotateDocumentAsStream(
                    new URL(params.url), mimetype, ResponseFormat.JSON, params.imgTagging, params.imgCategorization);
        } else {
            return client.annotateDocumentAsStream(new URL(params.url), mimetype, ResponseFormat.JSON);
        }
    }

    public static InputStream executeClassificationRequest(
            CommandLineParams params, S4ClassificationClient client, SupportedMimeType mimetype)
            throws IOException {

        if (params.filePath != null) {
            return client.classifyDocumentAsStream(new File(params.filePath), Charset.forName("UTF-8"), mimetype);
        } else {
            return client.classifyDocumentAsStream(new URL(params.url), mimetype);
        }
    }

    public static void saveOutputToFile(String outputFileName, InputStream resultData) {
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(outputFileName);
            IOUtils.copy(resultData, outStream);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        CommandLineParams params = new CommandLineParams();
        JCommander commander;
        try {
            commander = new JCommander(params, args);
        } catch (ParameterException pe) {
            System.out.println(pe.getMessage());
            System.out.println(OutputMessages.INFO.message);
            return;
        }
        commander.setProgramName(S4CommandLineTool.class.getSimpleName());

        if (params.help) {
            System.out.println(OutputMessages.HELP.message);
            commander.usage();
            return;
        }

        if (validParams(params)) {
            SupportedMimeType mimetype = SupportedMimeType.valueOf(params.documentType);
            ServiceDescriptor service = ServicesCatalog.getItem(params.serviceType);

            InputStream resultText;
            if (params.serviceType.equals("news-classifier")){
                S4ClassificationClient client = new S4ClassificationClientImpl(service, params.apiKey, params.keySecret);
                try {
                    resultText = executeClassificationRequest(params, client, mimetype);
                    saveOutputToFile(params.outputFileName, resultText);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            } else {
                S4AnnotationClient client = new S4AnnotationClientImpl(service, params.apiKey, params.keySecret);
                try {
                    resultText = executeAnnotationRequest(params, client, mimetype);
                    saveOutputToFile(params.outputFileName, resultText);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }
    }
}
