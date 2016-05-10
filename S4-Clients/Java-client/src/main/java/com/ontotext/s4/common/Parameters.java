/*
 * S4 Java client library
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.ontotext.s4.common;

import java.util.HashMap;

public class Parameters extends HashMap<String, String> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct the parameters from an array of name-value pairs using equals '=' as the separator.
	 * 
	 * @param nameValuePairs The array of name-value pairs
	 */
	public Parameters(String[] nameValuePairs) {
		parseNameValuePairs(nameValuePairs, '=', true);
	}
	
	private Parameters() {}
	
	/**
	 * Creates a new empty Parameters instance
	 * 
	 * @return new empty parameters
	 */
	public static Parameters newInstance() {
		return new Parameters();
	}

	/**
	 * Get the value associated with a parameter.
	 * 
	 * @param name The name of the parameter.
	 * @return The value associated with the parameter.
	 */
	public String getValue(String name) {
		return get(name);
	}

	/**
	 * Get the value associated with a parameter or return the given default if it is not available.
	 * 
	 * @param name The name of the parameter.
	 * @param defaultValue The default value to return.
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
	 * @param name The name of the parameter.
	 * @param value The value of the parameter.
	 */
	public void setValue(String name, String value) {
		put(name.trim(), value);
	}
	
	/**
	 * Associate the given value with the given parameter name. Convenience method allowing invocation chaining.
	 * 
	 * @param name The name of the parameter.
	 * @param value The value of the parameter.
	 * @return created object with newly added value
	 */
	public Parameters withValue(String name, String value) {
		setValue(name, value);
		return this;
	}

	/**
	 * Set a default value, i.e. set this parameter to have the given value ONLY if it has not already
	 * been set.
	 * 
	 * @param name The name of the parameter.
	 * @param value The value of the parameter.
	 */
	public void setDefaultValue(String name, String value) {
		if (getValue(name) == null)
			setValue(name, value);
	}

	/**
	 * The parse method that accepts an array of name-value pairs.
	 * 
	 * @param nameValuePairs An array of name-value pairs, where each string is of the form: "name-separator-value"
	 * @param separator The character that separates the name from the value
	 * @param overWrite true if the parsed values should overwrite existing value
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
