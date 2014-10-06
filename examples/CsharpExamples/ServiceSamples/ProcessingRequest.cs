/*
 * Copyright (c) 2014
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
using System.Web.Script.Serialization;

namespace ServiceSamples
{
    class ProcessingRequest
    {
        private String Document;           
        private String DocumentUrl;
        private String DocumentType;
        private String[] AnnotationSelectors;

        public String toJSON()
        {
            var serializer = new JavaScriptSerializer();
            var serializedResult = serializer.Serialize(this);
            return serializedResult;
        }

        public String document
        {
            get { return Document; }
            set { Document = value; }
        }
        public String documentUrl
        {
            get { return DocumentUrl; }
            set { DocumentUrl = value; }
        }
        public String documentType
        {
            get { return DocumentType; }
            set { DocumentType = value; }
        }
        public String[] annotationSelectors
        {
            get { return AnnotationSelectors; }
            set { AnnotationSelectors = value; }
        }

      

    }
}
