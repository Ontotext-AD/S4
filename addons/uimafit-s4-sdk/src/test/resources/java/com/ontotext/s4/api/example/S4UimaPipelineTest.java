package com.ontotext.s4.api.example;

import com.ontotext.s4.api.annotator.uimafit.MockS4DocumentUimaFitAnnotator;
import com.ontotext.s4.api.annotator.uimafit.S4DocumentUimaFitAnnotator;
import com.ontotext.s4.api.example.components.FileSystemCollectionReader;
import com.ontotext.s4.api.example.components.XmiWriterCasConsumer;
import com.ontotext.s4.api.util.ComponentConfigurationParameters;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 16-Apr-2016
 */
@Ignore("Not finished")
public class S4UimaPipelineTest {

    public static final String RES_DIR = "src/test/resources";

    @Test
    public void testMockedPipeline() throws Exception{
        String inputDir = new File(RES_DIR + "/input").getAbsolutePath();
        String outputDir = new File(RES_DIR + "/output").getAbsolutePath();
        String s4EndpointResult = new File(RES_DIR + "/s4_news_result.json").getAbsolutePath();

        ComponentConfigurationParameters readerParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(FileSystemCollectionReader.PARAM_SOURCE_TEXT_FILE_PATH, inputDir);

        ComponentConfigurationParameters annotatorParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(S4DocumentUimaFitAnnotator.PARAM_S4_SERVICE_ENDPOINT, s4EndpointResult);

        ComponentConfigurationParameters xmiWriterParameters = ComponentConfigurationParameters.newInstance()
                .withConfigParameter(XmiWriterCasConsumer.PARAM_OUTPUT_DIR, outputDir);

        CollectionReaderDescription readerDesc = CollectionReaderFactory
                .createReaderDescription(FileSystemCollectionReader.class, readerParameters.getParametersArray());

        AnalysisEngineDescription annotatorDesc = AnalysisEngineFactory
                .createEngineDescription(MockS4DocumentUimaFitAnnotator.class, annotatorParameters.getParametersArray());

        AnalysisEngineDescription casWriter = AnalysisEngineFactory.createEngineDescription(
                XmiWriterCasConsumer.class, xmiWriterParameters.getParametersArray());

        AggregateBuilder builder = new AggregateBuilder();
        builder.add(annotatorDesc);
        builder.add(casWriter);

        SimplePipeline.runPipeline(readerDesc, builder.createAggregateDescription());
    }
}
