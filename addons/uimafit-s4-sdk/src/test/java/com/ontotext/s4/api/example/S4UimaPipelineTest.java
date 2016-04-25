package com.ontotext.s4.api.example;

import com.ontotext.s4.api.annotator.uimafit.MockS4DocumentUimaFitAnnotator;
import com.ontotext.s4.api.annotator.uimafit.S4DocumentUimaFitAnnotator;
import com.ontotext.s4.api.example.components.FileSystemCollectionReader;
import com.ontotext.s4.api.example.components.XmiWriterCasConsumer;
import com.ontotext.s4.api.util.ComponentConfigurationParameters;
import com.ontotext.s4.api.util.FileUtils;
import com.ontotext.s4.api.util.TypeSystemUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceManager;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 16-Apr-2016
 */
//@Ignore("Not finished")
public class S4UimaPipelineTest {

    public static final String RES_DIR = "src/test/resources";

    public void setUp() {

    }

    @After
    public void tearDown() {
        FileUtils.deleteFolder(new File ("src/test/resources/output"));
    }


    @Test
    public void test() throws Exception {
        ComponentConfigurationParameters readerParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(
                        FileSystemCollectionReader.PARAM_SOURCE_TEXT_FILE_PATH,
                        new File("src/test/resources/input").getAbsolutePath());

        ComponentConfigurationParameters annotatorParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_SERVICE_ENDPOINT, "http://text.s4.ontotext.com/v1/news")
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_API_KEY_ID, "")
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_API_PASSWORD, "");

        ComponentConfigurationParameters xmiWriterParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter
                        (XmiWriterCasConsumer.PARAM_OUTPUT_DIR,
                                new File("src/test/resources/output").getAbsolutePath());

        CollectionReaderDescription readerDesc = CollectionReaderFactory
                .createReaderDescription(FileSystemCollectionReader.class, readerParameters.getParametersArray());

        AnalysisEngineDescription annotatorDesc = AnalysisEngineFactory
                .createEngineDescription(S4DocumentUimaFitAnnotator.class, annotatorParameters.getParametersArray());

        AnalysisEngineDescription casWriter = AnalysisEngineFactory.createEngineDescription(
                XmiWriterCasConsumer.class, xmiWriterParameters.getParametersArray());

        AggregateBuilder builder = new AggregateBuilder();
        builder.add(annotatorDesc);
        builder.add(casWriter);

        SimplePipeline.runPipeline(readerDesc, builder.createAggregateDescription());


    }
}
