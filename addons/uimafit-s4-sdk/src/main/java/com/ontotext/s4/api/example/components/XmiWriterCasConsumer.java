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


import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.TypeSystemUtil;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * 
 * <p>
 * Component parameters:
 * <ul>
 *      <li><code>OUTPUT_DIR</code> - path to directory into which output files will be written</li>
 * </ul>
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-19
 */
public class XmiWriterCasConsumer extends JCasConsumer_ImplBase {

    private static final String TYPE_SYSTEM_BASENAME = "typesystem.xml";
    private static final String DOCS_BASENAME = "processed";

    public static final String PARAM_OUTPUT_DIR = "OUTPUT_DIR";

    /**
     * Name of configuration parameter that must be set to the path of a
     * directory into which the output files will be written.
     */
    private boolean typeSystemWritten = false;

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, mandatory = true)
    private File outputDir;

    private int docNum;

    private File getTypeSystemXml() {
        return new File(outputDir, TYPE_SYSTEM_BASENAME);
    }

    private File getOutputDir() {
        return new File(outputDir, DOCS_BASENAME);
    }

    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        docNum = 0;
        if (!getOutputDir().exists())
            getOutputDir().mkdirs();
    }

    /**
     * Processes the CAS which was populated by the TextAnalysisEngines. <br>
     * In this case, the CAS is converted to XMI and written into the output
     * file .
     *
     * @param cas
     *           a CAS which has been populated by the TAEs
     *
     * @throws org.apache.uima.analysis_engine.AnalysisEngineProcessException
     *             if there is an error in processing the Resource
     *
     * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
     */
    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {

        File outFile = new File(getOutputDir(), String.format("doc_%05d.xml", docNum++));

        try {
            writeXmi(cas, outFile);
        } catch (IOException | SAXException | CASException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    /**
     * Serialize a CAS to a file in XMI format
     *
     * @param cas
     *            CAS to serialize
     * @param name
     *            output file
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     *
     * @throws org.apache.uima.cas.CASException
     */
    private void writeXmi(JCas cas, File name) throws IOException, SAXException, CASException {
        if (!typeSystemWritten) {
            writeTypeSystem(cas);
        }

        // write XMI
        try (FileOutputStream out = new FileOutputStream(name)) {
            XmiCasSerializer ser = new XmiCasSerializer(cas.getTypeSystem());
            XMLSerializer xmlSer = new XMLSerializer(out, false);
            ser.serialize(cas.getCas(), xmlSer.getContentHandler());
        }
    }

    /**
     * Parses and returns the descriptor for this collection reader. The
     * descriptor is stored in the uima.jar file and located using the
     * ClassLoader.
     *
     * @return an object containing all of the information parsed from the
     *         descriptor.
     *
     * @throws org.apache.uima.util.InvalidXMLException
     *             if the descriptor is invalid or missing
     */
    public static CasConsumerDescription getDescription() throws InvalidXMLException {
        InputStream descStream = XmiWriterCasConsumer.class
                .getResourceAsStream("XmiWriterCasConsumer.xml");
        return UIMAFramework.getXMLParser().parseCasConsumerDescription(
                new XMLInputSource(descStream, null));
    }

    private void writeTypeSystem(JCas cas) throws IOException, CASRuntimeException, SAXException, CASException {
        try (OutputStream typeOS = new BufferedOutputStream(new FileOutputStream(getTypeSystemXml()))) {
            TypeSystemUtil.typeSystem2TypeSystemDescription(cas.getTypeSystem()).toXML(typeOS);
        }
    }
}
