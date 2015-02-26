package com.ontotext.s4.SBTDemo.processingData;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;


public class ThreadReadFiles implements Runnable {
	private static final Logger logger = Logger
			.getLogger(ThreadReadFiles.class);

	private Thread t;
	private Collection<File> listOfAllFiles;
	LinkedBlockingQueue<File> requestsQueue;

	public ThreadReadFiles(LinkedBlockingQueue<File> requestsQueue,
			File directory) {
		super();
		this.requestsQueue=requestsQueue;
		init(directory);
	}

	private void init(File directory) {
		listOfAllFiles=FileUtils.listFiles(directory,  new RegexFileFilter("^(.*?)"), 
				  DirectoryFileFilter.DIRECTORY);
		logger.info("We found "+ listOfAllFiles.size()+" files.");
	}

	public void run() {
			for(File currentFile:listOfAllFiles){				
				try {
					
					//add current file to the requestQueue
					requestsQueue.add(currentFile);
				} catch (Exception e) {
					logger.info("There was problem reading this " + currentFile.getAbsolutePath()+" document.");
					logger.error(e);
				}
				
			}
			ProcessingDocuments.readHasFinished=true;
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
}
