/*
 * Copyright (c) 2014 Ontotext AD
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
package com.ontotext.s4.online;

/**
 * Representation of a single annotation selector expression. An
 * annotation selector is a combination of annotation set name and
 * annotation type. Either or both of the parts is optional - if the
 * annotation set name is omitted then this selects the default
 * annotation set (which has no name in GATE), and if the annotation
 * type is omitted then all annotations from the relevant set are
 * selected. So in particular if <em>both</em> parts are omitted, the
 * result is a selector that selects all annotations from the default
 * set.
 * 
 */
public class AnnotationSelector {

  /**
   * The annotation set name.
   */
  private String annotationSet;

  /**
   * The annotation type.
   */
  private String annotationType;

  /**
   * Create an annotation selector from an annotation set name and type.
   */
  public AnnotationSelector(String annotationSet, String annotationType) {
    this.annotationSet = (annotationSet == null ? "" : annotationSet);
    this.annotationType = (annotationType == null ? "" : annotationType);
  }

  public String getAnnotationClass() {
    return annotationSet;
  }

  public String getAnnotationType() {
    return annotationType;
  }

  public static final AnnotationSelector ALL_FROM_DEFAULT_SET =
          new AnnotationSelector("", "");

  /**
   * Create a selector selecting all annotations from the given set.
   */
  public static AnnotationSelector allAnnotationsFromClass(String annotationSet) {
    return new AnnotationSelector(annotationSet, "");
  }

  /**
   * Create a selector selecting the given annotation type from the
   * default annotation set.
   */
  public static AnnotationSelector annotationTypeFromDefaultSet(
          String annotationType) {
    return new AnnotationSelector("", annotationType);
  }

  @Override
  public String toString() {
    return annotationSet + ":" + annotationType;
  }

}
