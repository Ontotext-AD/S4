/*
 * S4 C# client library
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
