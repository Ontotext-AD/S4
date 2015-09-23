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
using Ontotext.S4.common;
using System.Json;

namespace Ontotext.S4.service
{
    /// <summary>
    /// Annotated Document
    /// </summary>
    public class AnnotatedDocument
    {
        /// <summary>
        ///  The <em>plain text</em> of the document, extracted from the
        /// original source. Any markup that was part of the original document
        /// (e.g. XML or HTML) will have been removed, leaving just the plain
        /// text.
        /// </summary>
        public String text;

        /// <summary>
        /// Annotations grouped by type. The keys in this map are annotation
        /// types and the corresponding values are the (possibly empty) list of
        /// annotations of that type that were found by the pipeline.
        /// </summary>
        public Dictionary<String, List<Annotation>> entities;

        /// <summary>
        /// Holder for any additional properties found in the JSON response
        /// apart from the text and entities. Typically this will only contain
        /// values if the original document that was processed was Twitter
        /// JSON.
        /// </summary>
        public Dictionary<String, JsonObject> otherFeatures;

        /// <summary>
        /// Catch-all setter used by Jackson to deserialize unknown properties
        /// into the {@link #otherFeatures} map.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="value"></param>
        public void addFeature(String name, JsonObject value)
        {
            if (otherFeatures == null) otherFeatures = new Dictionary<String, JsonObject>();
            otherFeatures.Add(name, value);
        }

    }
}
