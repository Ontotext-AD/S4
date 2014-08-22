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
