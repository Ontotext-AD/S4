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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontotext.s4.common.ServiceResponseEntity;

/**
 * Representation of a single annotation from an annotated document
 * returned by the S4 online API.
 * 
 */
public class Annotation extends ServiceResponseEntity {

  /**
   * Start offset of the annotation (a zero-based index into the
   * containing document's {@link AnnotatedDocument#text plain text}).
   */
  public long startOffset;

  /**
   * End offset of the annotation (a zero-based index into the
   * containing document's {@link AnnotatedDocument#text plain text}).
   */
  public long endOffset;

  /**
   * The annotation's features.
   */
  public Map<String, Object> features = new HashMap<String, Object>();

  /**
   * Used by Jackson to decode the "indices" property of the JSON
   * response into start and end offsets.
   * 
   * @param indices a two-element array containing the start and end
   *          offsets, in that order.
   */
  @JsonCreator
  public Annotation(@JsonProperty("indices") long[] indices) {
    startOffset = indices[0];
    endOffset = indices[1];
  }

  /**
   * Catch-all setter used by Jackson to gather other properties from
   * the JSON response into the {@link #features} map.
   */
  @JsonAnySetter
  public void addFeature(String name, Object value) {
    features.put(name, value);
  }

}
