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

namespace Ontotext.S4.service
{
    public class Annotation
    {
        private long StartOffset;
        private long EndOffset;
        private Dictionary<String, Object> Features = new Dictionary<String, Object>();

        public Annotation()
        {

        }

        public Annotation(long startOffset, long endOffset, Dictionary<String, Object> features)
        {
            this.startOffset = startOffset;
            this.endOffset = endOffset;
            this.features = features;
        }

        /// <summary>
        /// Start offset of the annotation (a zero-based index into the
        /// containing document's {@link AnnotatedDocument#text plain text}).
        /// </summary>
        public long startOffset
        {
            get { return StartOffset; }
            set { StartOffset = value; }
        }

        /// <summary>
        /// End offset of the annotation (a zero-based index into the
        /// containing document's {@link AnnotatedDocument#text plain text}).
        /// </summary>
        public long endOffset
        {
            get { return EndOffset; }
            set { EndOffset = value; }
        }

        /// <summary>
        /// The annotation's features.
        /// </summary>
        public Dictionary<String, Object> features
        {
            get { return Features; }
            set { Features = value; }
        }

        /// <summary>
        /// Used by Jackson to decode the "indices" property of the JSON response into start and end offsets.
        /// </summary>
        /// <param name="indices">indices a two-element array containing the start and end
        ///          offsets, in that order.</param>
        public Annotation(long[] indices)
        {
            StartOffset = indices[0];
            EndOffset = indices[1];
        }

        /// <summary>
        /// Catch-all setter used by Jackson to gather other properties from the JSON response into the {@link #features} map.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="value"></param>
        public void addFeature(String name, Object value)
        {
            Features.Add(name, value);
        }
    }
}
