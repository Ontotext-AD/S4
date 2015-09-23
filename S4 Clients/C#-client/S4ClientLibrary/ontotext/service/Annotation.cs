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

namespace Ontotext.S4.service
{
    /// <summary>
    /// Annotation
    /// </summary>
    public class Annotation
    {
        /// <summary>
        /// Start offset of the annotation (a zero-based index into the
        /// containing document's {@link AnnotatedDocument#text plain text}).
        /// </summary>
        public long startOffset;

       /// <summary>
       /// End offset of the annotation (a zero-based index into the
       /// containing document's {@link AnnotatedDocument#text plain text}).
       /// </summary>
        public long endOffset;

        /// <summary>
        /// The annotation's features.
        /// </summary>
        public Dictionary<String, Object> features = new Dictionary<String, Object>();

       /// <summary>
       ///   Used by Jackson to decode the "indices" property of the JSON
       /// response into start and end offsets.
       /// </summary>
       /// <param name="indices">indices a two-element array containing the start and end
       ///          offsets, in that order.</param>
        public Annotation(long[] indices)
        {
            startOffset = indices[0];
            endOffset = indices[1];
        }

        /// <summary>
        /// Catch-all setter used by Jackson to gather other properties from
        /// the JSON response into the {@link #features} map.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="value"></param>
        public void addFeature(String name, Object value)
        {
            features.Add(name, value);
        }
    }
}
