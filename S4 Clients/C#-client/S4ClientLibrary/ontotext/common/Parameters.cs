/*
 * S4 C# client library
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
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ontotext.S4.common
{
    /// <summary>
    /// Contains parameters
    /// </summary>
    public class Parameters
    {

        private Dictionary<String, String> mParameters = new Dictionary<String, String>();

        /// <summary>
        /// Construct the parameters from an array of name-value pairs using equals '=' as the separator.  
        /// </summary>
        /// <param name="nameValuePairs">The array of name-value pairs</param>
        public Parameters(String[] nameValuePairs)
        {
            parseNameValuePairs(nameValuePairs, '=', true);
        }   

       /// <summary>
       ///  Get the value associated with a parameter. 
       /// </summary>
       /// <param name="name">The name of the parameter.</param>
       /// <returns>The value associated with the parameter.</returns>
        public String getValue(String name)
        {
            return mParameters[name];
        }

        /// <summary>
        /// Get the value associated with a parameter or return the given default if it is not available.
        /// </summary>
        /// <param name="name">The name of the parameter.</param>
        /// <param name="defaultValue">The default value to return.</param>
        /// <returns>The value associated with the parameter.</returns>
        public String getValue(String name, String defaultValue)    
        {
            String value = getValue(name);

            if (value == null)
                value = defaultValue;

            return value;
        }

        /// <summary>
        /// Associate the given value with the given parameter name.
        /// </summary>
        /// <param name="name">The name of the parameter.</param>
        /// <param name="value">The value of the parameter.</param>
        public void setValue(String name, String value)
        {
            mParameters.Add(name.Trim().ToLower(), value);
        }

       /// <summary>
       /// Set a default value, i.e. set this parameter to have the given value ONLY if it has not already
       /// been set.
       /// </summary>
       /// <param name="name">The name of the parameter.</param>
       /// <param name="value">The value of the parameter.</param>
        public void setDefaultValue(String name, String value)
        {
            if (getValue(name) == null)
                setValue(name, value);
        }

        /// <summary>
        /// The parse method that accepts an array of name-value pairs.
        /// </summary>
        /// <param name="nameValuePairs">An array of name-value pairs, where each string is of the form:"<name>'separator'<value>"</param>
        /// <param name="separator"> The character that separates the name from the value</param>
        /// <param name="overWrite"> true if the parsed values should overwrite existing value</param>
        public void parseNameValuePairs(String[] nameValuePairs, char separator, Boolean overWrite) {
		foreach (String pair in nameValuePairs) {
			int pos = pair.IndexOf(separator);
			if (pos < 0)
                throw new ArgumentException("Invalid name-value pair '" + pair
						+ "', expected <name>" + separator + "<value>");
			String name = pair.Substring(0, pos).ToLower();
			String value = pair.Substring(pos + 1);
			if (overWrite)
				setValue(name, value);
			else
				setDefaultValue(name, value);
		}
	}

    }
}
