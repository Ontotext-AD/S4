/*
* Copyright (c) 2015
*
* This file is part of the s4.ontotext.com REST client library, and is
* licensed under the Apache License, Version 2.0 (the "License");
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

package com.ontotext.s4.api.example.components;

import com.ontotext.s4.api.util.FileUtils;
import com.ontotext.s4.api.util.Preconditions;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * A UIMA CollectionReader component responsible for reading raw text files from the file system.
 * NOTE: This component is implemented only for the purpose of running the example pipeline. Though it
 * could still be used as a regular component in any uimaFIT pipeline.
 *
 * Component parameters:
 * <ul>
 *      <li><code>SOURCE_TEXT_FILE_PATH</code> - The file path leading to the source text files to be processed.</li>
 * </ul>
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-03-05
 */
public class FileSystemCollectionReader extends JCasCollectionReader_ImplBase {

    private static final Logger LOG = LoggerFactory
            .getLogger(FileSystemCollectionReader.class);

    public static final String PARAM_SOURCE_TEXT_FILE_PATH = "SOURCE_TEXT_FILE_PATH";

    @ConfigurationParameter(name = PARAM_SOURCE_TEXT_FILE_PATH,
            mandatory = true,
            description = "The file path leading to the source text files to be processed.")
    public String rawTextFilePath;

    /**
     * List with raw text files
     */
    private List<Path> rawTextFiles;

    /**
     * An iterator for the raw text files used in the overriden hasNext() and getNext(...) methods of the Collection Reader.
     */
    private Iterator<Path> rawTextFilesIterator;

    /**
     * Number of files read.
     */
    private int filesRead;

    /**
     * Number of total available raw text files being read.
     */
    private int totalFiles;


    /**
     * Set raw document text as a Sofa string to the CAS object. This text will be then used from the other components of
     * your pipeline in order to be processed.
     *
     * @param rawTextFile the file being read
     * @param cas the new CAS object where the raw text is going to be set
     */
    private void setDocumentText(Path rawTextFile, JCas cas) {
        String rawText = null;
        try {
            rawText = FileUtils.readFileToString(rawTextFile, Charset.defaultCharset());
        } catch (IOException e) {
            LOG.error("Cannot read raw text document -> " + rawTextFile, e);
        }

        cas.setSofaDataString(rawText, "text/plain");
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        if (Preconditions.isNullOrEmpty(rawTextFilePath)) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.CONFIG_SETTING_ABSENT,
                    new Object[] { PARAM_SOURCE_TEXT_FILE_PATH });
        }

        try {
            this.rawTextFiles = FileUtils.listFiles(Paths.get(rawTextFilePath));
        } catch (IOException e) {
            LOG.error("Cannot list raw text files!", e);
        }

        this.rawTextFilesIterator = rawTextFiles.listIterator();
        this.filesRead = 0;
        this.totalFiles = rawTextFiles.size();
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return rawTextFilesIterator.hasNext();
    }

    @Override
    public void getNext(JCas cas) throws IOException, CollectionException {
        setDocumentText(rawTextFilesIterator.next(), cas);
    }

    @Override
    public Progress[] getProgress() {
        return new Progress[] { new ProgressImpl(filesRead, totalFiles, Progress.ENTITIES) };
    }
}
