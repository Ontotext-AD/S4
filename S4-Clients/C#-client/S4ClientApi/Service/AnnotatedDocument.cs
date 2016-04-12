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
using Ontotext.S4.common;
using System.Json;

namespace Ontotext.S4.service
{
    /// <summary>
    /// Object-oriented representation of an annotated document as returned by the S4 online API
    /// </summary>
    public class AnnotatedDocument
    {
        private String Text;
        private Dictionary<String, List<Annotation>> Entities;
        private Dictionary<String, JsonObject> OtherFeatures;

        /// <summary>
        ///  The <em>plain text</em> of the document, extracted from the original source. Any markup
        ///  that was part of the original document will have been removed, leaving just the plain text.
        /// </summary>
        public String text
        {
            get { return Text; }
            set { Text = value; }
        }

        /// <summary>
        /// Annotations grouped by type. The keys in this map are annotation
        /// types and the corresponding values are the (possibly empty) list of
        /// annotations of that type that were found by the pipeline.
        /// </summary>
        public Dictionary<String, List<Annotation>> entities
        {
            get { return Entities; }
            set { Entities = value; }
        }

        /// <summary>
        /// Holder for any additional properties found in the JSON response
        /// apart from the text and entities. Typically this will only contain
        /// values if the original document that was processed was Twitter
        /// JSON.
        /// </summary>
        public Dictionary<String, JsonObject> otherFeatures
        {
            get { return OtherFeatures; }
            set { OtherFeatures = value; }
        }

        /// <summary>
        /// Catch-all setter used by Jackson to deserialize unknown properties
        /// into the {@link #otherFeatures} map.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="value"></param>
        public void addFeature(String name, JsonObject value)
        {
            if (OtherFeatures == null) OtherFeatures = new Dictionary<String, JsonObject>();
            OtherFeatures.Add(name, value);
        }
    }
}
