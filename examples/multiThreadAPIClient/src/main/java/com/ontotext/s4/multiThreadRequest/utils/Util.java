/*
 * Licensed to the Ontotext under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Ontotext licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
