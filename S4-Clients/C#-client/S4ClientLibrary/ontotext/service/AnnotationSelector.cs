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

namespace Ontotext.S4.service
{
    /// <summary>
    /// Annotation Selector
    /// </summary>
    public class AnnotationSelector
    {
        /// <summary>
        /// The annotation set name.
        /// </summary>
        private String annotationSet;

        /// <summary>
        /// The annotation type.
        /// </summary>
        private String annotationType;

        /// <summary>
        /// Create an annotation selector from an annotation set name and type.
        /// </summary>
        /// <param name="annotationSet"></param>
        /// <param name="annotationType"></param>
        public AnnotationSelector(String annotationSet, String annotationType)
        {
            this.annotationSet = (annotationSet == null ? "" : annotationSet);
            this.annotationType = (annotationType == null ? "" : annotationType);
        }

        public String getAnnotationClass()
        {
            return annotationSet;
        }

        public String getAnnotationType()
        {
            return annotationType;
        }

        public const AnnotationSelector ALL_FROM_DEFAULT_SET = null;

        /// <summary>
        /// Create a selector selecting all annotations from the given set.
        /// </summary>
        /// <param name="annotationSet"></param>
        /// <returns></returns>
        public static AnnotationSelector allAnnotationsFromClass(String annotationSet)
        {
            return new AnnotationSelector(annotationSet, "");
        }

       /// <summary>
       /// Create a selector selecting the given annotation type from the default annotation set.
       /// </summary>
       /// <param name="annotationType"></param>
       /// <returns></returns>
        public static AnnotationSelector annotationTypeFromDefaultSet(
                String annotationType)
        {
            return new AnnotationSelector("", annotationType);
        }

        public String ToString()
        {
            return annotationSet + ":" + annotationType;
        }
    }
}
