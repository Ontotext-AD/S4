package com.ontotext.s4.multiThreadRequest.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class Util {
	
	public static final String UTF_8_ENCODING = "UTF-8";
	
	public static String convertSreamToString(InputStream inputStream)
			throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, UTF_8_ENCODING);
		String theString = writer.toString();
		return theString;
	}

}
