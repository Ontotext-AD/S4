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

package com.ontotext.s4.samples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openrdf.model.Resource;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;

import com.ontotext.s4.samples.chunkloader.ChunkLoader;

public class Loader {
	
	public static final String RECURSIVE_INPUT_PARAM = "r";
	public static final String INPUT_PARAM = "i";
	public static final String GRAPH_PARAM = "graph";
	public static final String CHUNKSIZE_PARAM = "chunksize";
	public static final String USER_CREDENTIALS_PARAM = "u";
	public static final String URL_PARAM = "url";
	
	public static final String DEFAULT_CHUNK_SIZE = "50000";
	
	private Options options;
	private CommandLine cmd;
	
	public Loader(String[] args) throws ParseException {
		initOptions();
		CommandLineParser parser = new DefaultParser();
		try {
			cmd = parser.parse(options, args);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Loader", options );
			System.exit(1); // FIXME
		}
	}
	
	private List<File> buildInputFiles(String inputSpec, boolean useRecursion) throws Exception {
		List<File> res = new ArrayList<File>();
		buildInputFilesAux(new File(inputSpec), useRecursion, res);
		return res;
	}
	
	private void buildInputFilesAux(File f, boolean useRecursion, List<File> res) throws Exception {
		if (false == f.exists()) {
			throw new IOException("Input file not found: " + f.getAbsolutePath());
		}
		if (f.isFile()) {
			res.add(f);
		}
		else if (f.isDirectory()) {
			for(File child : f.listFiles()) {
				if (child.isDirectory() && false == useRecursion) {
					continue;
				}
				buildInputFilesAux(child, useRecursion, res);
			}
		}
	}
	
	public void loadData() throws Exception {
		List<File> inputFiles = buildInputFiles(
				cmd.getOptionValue(INPUT_PARAM), 
				Boolean.valueOf(cmd.getOptionValue(RECURSIVE_INPUT_PARAM, "false")));
		if (inputFiles.size() == 0) {
			System.out.println("No input file(s) found for loading");
			return;
		}
		System.out.println(inputFiles.size() + " file(s) scheduled for loading");
		RepositoryConnection conn = initConnection();
		ChunkLoader loader = new ChunkLoader(
				Integer.valueOf(cmd.getOptionValue(CHUNKSIZE_PARAM, DEFAULT_CHUNK_SIZE)), false);
		
		long totalLoaded = 0l;
		Resource targetGraph = cmd.hasOption(GRAPH_PARAM) 
				? conn.getValueFactory().createURI(cmd.getOptionValue(GRAPH_PARAM)) 
						: null;
		
		for(File file : inputFiles) {
			System.out.print("Loading " + file.getName() + " ");
			totalLoaded += loader.loadFile(conn, file , targetGraph);
		}
		
		conn.close();
		
		System.out.println("Total statements loaded: " + totalLoaded);
	}
	
	private RepositoryConnection initConnection() throws Exception {
		HTTPRepository httpRepo = 
				new HTTPRepository(cmd.getOptionValue(URL_PARAM));
		String creds = cmd.getOptionValue(USER_CREDENTIALS_PARAM);
		if (creds != null) {
			if (false == creds.contains(":")) {
				throw new Exception("Invalid credentials value - expected column separated pair");
			}
			httpRepo.setUsernameAndPassword(creds.substring(0, creds.indexOf(':')), 
					creds.substring(creds.indexOf(':') + 1));
		}
		return httpRepo.getConnection();
	}

	public static void main(String[] args) throws Exception {
		
		new Loader(args).loadData();
	}
	
	private void initOptions() {
		options = new Options();
		
		Option opt = new Option(URL_PARAM, true, "The URL of the Sesame/RDF4J repository");
		opt.setRequired(true);
		options.addOption(opt);
		
		opt = new Option(USER_CREDENTIALS_PARAM, true, "Credentials for accessing the repository service (optional)");
		opt.setOptionalArg(true);
		options.addOption(opt);

		opt = new Option(CHUNKSIZE_PARAM, true, "The number of statements, sent on each transaction (optional). Defaults to 50000");
		opt.setOptionalArg(true);
		options.addOption(opt);

		opt = new Option(GRAPH_PARAM, true, "The named graph to load the data in (optional)");
		opt.setOptionalArg(true);
		options.addOption(opt);
		
		opt = new Option(INPUT_PARAM, true, "Input RDF dump file or folder");
		opt.setRequired(true);
		options.addOption(opt);
		
		opt = new Option(RECURSIVE_INPUT_PARAM, false, "Traverse recursively the input folder (optional)");
		opt.setOptionalArg(true);
		options.addOption(opt);

	}

}
