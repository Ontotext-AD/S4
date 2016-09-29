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
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.util.RDFInserter;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;

/**
 * This class is inspired by Jeen Broekstra
 * http://rivuli-development.com/further-reading/sesame-cookbook/loading-large-file-in-sesame-native/
 */
public class ChunkCommitter implements RDFHandler {

    private final long chunkSize;
    private final RDFInserter inserter;
    private final RepositoryConnection conn;
    private final Resource context;
    private final ValueFactory factory;

    private long count = 0L;

    public ChunkCommitter(RepositoryConnection conn, Resource context, long chunkSize) {
        this.chunkSize = chunkSize;
        this.context = context;
        this.conn = conn;
        factory = conn.getValueFactory();
        inserter = new RDFInserter(conn);
    }

    public long getStatementCount() {
        return count;
    }

    @Override
    public void startRDF() throws RDFHandlerException {
        inserter.startRDF();
    }

    @Override
    public void endRDF() throws RDFHandlerException {
        inserter.endRDF();
    }

    @Override
    public void handleNamespace(String prefix, String uri)
            throws RDFHandlerException {
        inserter.handleNamespace(prefix, uri);
    }

    @Override
    public void handleStatement(Statement st) throws RDFHandlerException {
        if (context != null) {
            st = factory.createStatement(st.getSubject(), st.getPredicate(), st.getObject(), context);
        }
        inserter.handleStatement(st);
        count++;
        // do an intermittent commit whenever the number of triples
        // has reached a multiple of the chunk size
        if (count % chunkSize == 0) {
            try {
                conn.commit();
                System.out.print(".");
                conn.begin();
            } catch (RepositoryException e) {
                throw new RDFHandlerException(e);
            }
        }
    }

    @Override
    public void handleComment(String comment) throws RDFHandlerException {
        inserter.handleComment(comment);
    }
}
