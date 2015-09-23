/*
 * S4 Java client library
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
package com.ontotext.s4.service;

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
