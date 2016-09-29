/** Self-Service Semantic Suite (S4)
 * Copyright (c) 2016, Ontotext AD
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ontotext.s4.samples.chunkloader;

import org.openrdf.model.Resource;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.*;
import org.openrdf.rio.helpers.BasicParserSettings;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * Helper class for loading large datasets with chunking.
 * Chunk loading works by loading portions (chunks)
 * of the dataset and committing the transaction after each chunk.
 */
public class ChunkLoader {
    private boolean preserveBnodeIds;
    private long chunkSize;

    public ChunkLoader(long chunkSize, boolean preserveBnodeIds) {
        this.chunkSize = chunkSize;
        this.preserveBnodeIds = preserveBnodeIds;
    }

    public long loadFile(RepositoryConnection repositoryConnection, File file, Resource context)
            throws IOException, RDFParseException, RDFHandlerException, RepositoryException {
        RDFFormat format = 
        		Rio.getParserFormatForFileName(file.getName());

        if (format == null) {
            throw new IOException("Unknown format for file.");
        }

        InputStream reader = null;

        try {
            if (file.getName().endsWith("gz")) {
                reader = new GZIPInputStream(new BufferedInputStream(new FileInputStream(file), 256 * 1024));
            } else {
                reader = new BufferedInputStream(new FileInputStream(file), 256 * 1024);
            }

            long start = System.currentTimeMillis();

            ParserConfig config = new ParserConfig();
            config.set(BasicParserSettings.PRESERVE_BNODE_IDS, preserveBnodeIds);

            RDFParser parser = Rio.createParser(format);
            parser.setParserConfig(config);

            // add our own custom RDFHandler to the parser. This handler takes care of adding
            // triples to our repository and doing intermittent commits
            ChunkCommitter handler = new ChunkCommitter(repositoryConnection, context, chunkSize);
            parser.setRDFHandler(handler);

            repositoryConnection.begin();

            Resource importContext = context == null ? new URIImpl(file.toURI().toString()) : context;
            parser.parse(reader, importContext.toString());

            repositoryConnection.commit();

            long statementsLoaded = handler.getStatementCount();
            long time = System.currentTimeMillis() - start;

            System.out.println("Loaded " + statementsLoaded + " statements in " + time + " ms; avg speed = " + (statementsLoaded * 1000 / time) + " st/s");

            return statementsLoaded;
        } catch (RepositoryException | RDFParseException | RDFHandlerException e) {
            try {
                repositoryConnection.rollback();
            } catch (RepositoryException ex) {
                // ignored
            }
            throw e;
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
