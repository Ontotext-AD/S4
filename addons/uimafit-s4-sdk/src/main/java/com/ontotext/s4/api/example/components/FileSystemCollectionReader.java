package com.ontotext.s4.api.example.components;

import com.ontotext.s4.api.util.FileUtils;
import com.ontotext.s4.api.util.Preconditions;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.CollectionReaderFactory;
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
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 2015-03-05
 */
public class FileSystemCollectionReader extends JCasCollectionReader_ImplBase {

    private static final Logger LOG = LoggerFactory
            .getLogger(FileSystemCollectionReader.class);

    public static final String PARAM_SOURCE_TEXT_FILE_PATH = "SOURCE_TEXT_FILE_PATH";

    @ConfigurationParameter(name = PARAM_SOURCE_TEXT_FILE_PATH,
            mandatory = true,
            description = "The file path leading to the source text files to be processed")
    public String rawTextFilePath;

    private List<Path> rawTextFiles;

    private Iterator<Path> rawTextFilesIterator;

    private int filesRead;

    private int totalFiles;

    private Object[] parameters;

    public CollectionReaderDescription createDescription(Object... confData) throws ResourceInitializationException {
        this.parameters = confData;
        return CollectionReaderFactory.createReaderDescription(FileSystemCollectionReader.class, parameters);
    }

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
