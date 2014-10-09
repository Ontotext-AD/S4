/*
 * S4 Java client library
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.ontotext.s4.common;

import java.util.HashMap;
import java.util.Map;

public class Parameters {


	private Map<String, String> mParameters = new HashMap<String, String>();
	
	/**
	 * Construct the parameters from an array of name-value pairs using equals '=' as the separator.
	 * 
	 * @param nameValuePairs
	 *            The array of name-value pairs
	 */
	public Parameters(String[] nameValuePairs) {
		parseNameValuePairs(nameValuePairs, '=', true);
	}

	/**
	 * Get the value associated with a parameter.
	 * 
	 * @param name
	 *            The name of the parameter.
	 * @return The value associated with the parameter.
	 */
	public String getValue(String name) {
		return mParameters.get(name);
	}

	/**
	 * Get the value associated with a parameter or return the given default if it is not available.
	 * 
	 * @param name
	 *            The name of the parameter.
	 * @param defaultValue
	 *            The default value to return.
	 * @return The value associated with the parameter.
	 */
	public String getValue(String name, String defaultValue) {
		String value = getValue(name);

		if (value == null)
			value = defaultValue;

		return value;
	}

	/**
	 * Associate the given value with the given parameter name.
	 * 
	 * @param name
	 *            The name of the parameter.
	 * @param value
	 *            The value of the parameter.
	 */
	public void setValue(String name, String value) {
		mParameters.put(name.trim().toLowerCase(), value);
	}

	/**
	 * Set a default value, i.e. set this parameter to have the given value ONLY if it has not already
	 * been set.
	 * 
	 * @param name
	 *            The name of the parameter.
	 * @param value
	 *            The value of the parameter.
	 */
	public void setDefaultValue(String name, String value) {
		if (getValue(name) == null)
			setValue(name, value);
	}

	/**
	 * The parse method that accepts an array of name-value pairs.
	 * 
	 * @param nameValuePairs
	 *            An array of name-value pairs, where each string is of the form:
	 *            "<name>'separator'<value>"
	 * @param separator
	 *            The character that separates the name from the value
	 * @param overWrite
	 *            true if the parsed values should overwrite existing value
	 */
	public void parseNameValuePairs(String[] nameValuePairs, char separator, boolean overWrite) {
		for (String pair : nameValuePairs) {
			int pos = pair.indexOf(separator);
			if (pos < 0)
				throw new IllegalArgumentException("Invalid name-value pair '" + pair
						+ "', expected <name>" + separator + "<value>");
			String name = pair.substring(0, pos).toLowerCase();
			String value = pair.substring(pos + 1);
			if (overWrite)
				setValue(name, value);
			else
				setDefaultValue(name, value);
		}
	}


}
