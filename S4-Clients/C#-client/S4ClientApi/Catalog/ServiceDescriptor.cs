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

namespace Ontotext.S4.catalog
{
    /// <summary>
    /// A model class representing an S4 service
    /// </summary>
    public class ServiceDescriptor
    {
        private String name;

        private String description;

        private String serviceUrl;

        /// <summary>
        /// The service name.
        /// </summary>
        public String Name
        {
            get { return name; }
            set { name = value; }
        }

        /// <summary>
        /// A description of the service.
        /// </summary>
        public String Description
        {
            get { return description; }
            set { description = value; }
        }

        /// <summary>
        ///  URL to process documents using the service endpoint.
        /// </summary>
        public String ServiceUrl
        {
            get { return serviceUrl; }
            set { serviceUrl = value; }
        }
    }
}
