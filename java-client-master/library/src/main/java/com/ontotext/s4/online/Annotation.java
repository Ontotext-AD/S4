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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontotext.s4.common.ApiObject;

/**
 * Representation of a single annotation from an annotated document
 * returned by the S4 online API.
 * 
 */
public class Annotation extends ApiObject {

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
