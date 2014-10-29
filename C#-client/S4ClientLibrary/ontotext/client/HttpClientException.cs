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
using System.Json;

namespace Ontotext.S4.client
{
    /// <summary>
    /// Some problem with execution of the query.
    /// </summary>
    public class HttpClientException : Exception
    {
        /// <summary>
        /// The JSON error response message, if applicable.
        /// </summary>
        private JsonObject response;

        public HttpClientException()
        {
        }

        public HttpClientException(String message)
            : base(message)
        {

        }

        public HttpClientException(Exception ex)
            : base(ex.Message)
        {

        }

        /// <summary>
        /// If this exception resulted from a 4xx or 5xx error response fromthe server, this method provides access to the response body. 
        /// </summary>
        /// <returns>the error response body as a Jackson {@link JsonNode}, or
        /// <code>null</code> if no response is available (e.g. if this
        ///  exception wraps an IOException rather than representing an
        ///  error response from the server).</returns>
        public JsonObject getResponse()
        {
            return response;
        }
    }
}
